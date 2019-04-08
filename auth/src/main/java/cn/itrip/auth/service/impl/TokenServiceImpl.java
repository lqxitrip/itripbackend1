package cn.itrip.auth.service.impl;

import cn.itrip.auth.service.TokenService;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import com.alibaba.fastjson.JSON;
import nl.bitwalker.useragentutils.UserAgent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisAPI redisAPI;

    //生成Token字符串,格式为：token：客户端标识-USERCODE-USERIL-CREATIONDATE-RANDEM[6位]
    @Override
    public String generateToken(String userAgent, ItripUser user) {
        StringBuilder sb = new StringBuilder("token:");
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        //判断当前用户是从哪个客户端进行登录的。isMobileDevice（）判断是否是移动设备
        if(!agent.getOperatingSystem().isMobileDevice()){
            sb.append("PC-");
        }else {
            sb.append("MOBILE-");//移动设备
        }
        sb.append(MD5.getMd5(user.getUserCode(),32) + "-");
        sb.append(user.getId()+"-");
        sb.append(Calendar.getInstance().getTimeInMillis() + "-");
        //sb.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "-");
        sb.append(MD5.getMd5(userAgent,6));//对userAgent进行6位MD4加密
        return sb.toString();
    }

    // 以token字符串 为key，ItripUser对象为值，保存到Redis缓存数据库中。
    @Override
    public String save(String token, ItripUser user) {
        //如果是PC端那么token存入到Redis他的有效时间为2H
        if(token.startsWith("token:PC-")){
            redisAPI.set(token,2*60*60, JSON.toJSONString(user));
        }else {
            redisAPI.set(token, JSON.toJSONString(user));
        }
        return null;
    }

    @Override
    public boolean validate(String userAgent, String token) {
        if (!redisAPI.exist(token)) {
            return false;  //验证失败
        }
        //按"-"进行分割后取第四位，即得到使用MD5对http请求中的user-agent加密，生成的六位随机数。
        // token:客户端标识-USERCODE-USERID-CREATIONDATE-RANDEM[6位]
        String agentMD5 = token.split("-")[4];
        if (!MD5.getMd5(userAgent, 6).equals(agentMD5)) {
            return false;
        }
        //不用去验证token是否在有效期中，首先如果是PC端的用户，token有效期为2h，在2h后在redis中会被自动删除。
        //然后在移动端的用户信息是永久有效到redis缓存数据库中。
        return true;
    }

    @Override
    public void delete(String token) {
        redisAPI.delete(token);
    }



    //编写Token置换的方法
    private long expireTime = 2 * 60 * 60 * 1000;   /*2小时有效期*/
    private int delay = 2 * 60;  /*2分钟，以秒为单位*/
    private int friTime = 5 * 60 * 1000;  /*5分钟*/
    @Override
    public String reloadToken(String userAgent, String token) throws Exception {
        //1、验证token是否有效
        if (!redisAPI.exist(token)) {
            throw new Exception("token无效");
        }
        //2、看能不能置换,规则：用当前时间减token生成时间得到值，看这个值是否大于30分钟，大于则可以。
        //获取token的生成时间
        Date genTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(token.split("-")[3]);
        //使用当前时间去减token的生成时间
        //long passed = Calendar.getInstance().getTimeInMillis() - genTime.getTime();
        long activeTime = genTime.getTime() + expireTime;  /*token的有效期，规则生成时间+2小时*/
        long passed = activeTime - Calendar.getInstance().getTimeInMillis(); /*过期时间-当前时间*/
        if (passed > friTime) {  /*过期时间-当前时间>5分钟，如果大于则不进行置换。*/
            throw new Exception("处在token置换保护期内，不能置换！剩余时间为：" + passed / 1000 +   "s");
        }
        //3、进行置换, 之前是将ItripUser用户对象转换成JSON字符串并保存到Redis中(value)
        //【key为redis字符串】。下面代码是将取出来的JSON字符串转换成ItripUser用户对象。
        String userString = redisAPI.get(token); //拿到用户字符串
        ItripUser user = JSON.parseObject(userString, ItripUser.class);
        String newToken = generateToken(userAgent, user); //生成新的Token
        //4、老的token进行延时过期(估计是怕来不急，所以延时一会不让它被redis删除)
        redisAPI.set(token,delay, JSON.toJSONString(userString));
        //5、新的token保存至Redis缓存数据库
        this.save(newToken, user);
        return newToken;
    }

}
