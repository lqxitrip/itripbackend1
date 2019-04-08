package cn.itrip.auth.service.impl;

import cn.itrip.auth.service.MailService;
import cn.itrip.auth.service.SmsService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.common.MD5;
import cn.itrip.common.RedisAPI;
import cn.itrip.dao.auth.ItripUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {


    @Resource
    private ItripUserMapper itripUserMapper;

    @Resource
    private MailService mailService;

    @Resource
    private RedisAPI redisAPI;

    @Resource
    private SmsService smsService;


    //根据userCode去查询用户对象信息
    public ItripUser getUser(String userCode){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userCode",userCode);
        List<ItripUser> userList = itripUserMapper.queryUserByMap(map);
        ItripUser user = null;
        if(userList != null && userList.size()>0){
            user = userList.get(0);
        }
        return user;
    }

    @Override
    public ItripUser login(String userCode,String password) throws Exception {
        ItripUser user = this.findByUsername(userCode);
        if (user != null && user.getUserPassword().equals(password)){
            if(user.getActivated() !=1){
                throw new Exception("账户尚未激活");
            }
            return user;
        }else {
            return null;
        }
    }

    /**
     * 完成用户的新增操作
     * @param itripUser 用户对象
     * @throws Exception
     */
    @Override
    public void insertUser(ItripUser itripUser) throws Exception {
        //1、添加用户---调用Dao层实现新增
        itripUserMapper.insertItripUser(itripUser);
        //2、生成激活码， new Date().toLocaleString()生成日期和时间，
        //格式如：2019-1-25     22:56:32，然后进行32位的MD5加密
        String activationCode = MD5.getMd5(new Date().toLocaleString(), 32);
        //3、发送邮件，参数一，收件邮箱地址。  参数二，激活码
        mailService.sendActivationMail(itripUser.getUserCode(),activationCode);
        //4、激活码存入Redis中、
        redisAPI.set("activation:" + itripUser.getUserCode(),30 * 60, activationCode);
    }

    /**
     * 邮箱激活
     * @param mail 邮箱地址
     * @param code 激活码(使用MD5进行32位的加密)
     * @return true/false，true表示验证成功
     */
    @Override
    public boolean activate(String mail, String code) throws Exception {
        //1、验证激活码
        String value = redisAPI.get("activation:" + mail);
        if (value.equals(code)) {
            ItripUser user = findByUsername(mail);
            //2、更新用户
            user.setActivated(1);  //激活账户
            user.setUserType(0); //自注册用户
            user.setFlatID(user.getId());
            itripUserMapper.updateItripUser(user);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 根据用户名查找用户
     * @param userCode
     */
    public ItripUser findByUsername(String userCode){
        Map<String, Object> param=new HashMap();
        param.put("userCode", userCode);
       List<ItripUser> list= itripUserMapper.getItripUserListByMap(param);
        if(list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public void insertUserByPhone(ItripUser user) throws Exception {
        //1、创建用户
        itripUserMapper.insertItripUser(user);
        //2、生成验证码(1111-9999)
        int code = MD5.getRandomCode();
        //3、发送验证码，  “1”为默认模板id的值。
        smsService.sendMsg(user.getUserCode(), "1", new String[] {String.valueOf(code), "1"});
        //4、缓存验证码到Redis数据库中， 有效期为60秒，测试时可以设置大一点。
        redisAPI.set("activation:" + user.getUserCode(), 240, String.valueOf(code));

    }

    @Override
    public boolean validatePhone(String phoneNum, String code) throws Exception {
        //1、对比验证码
        String key = "activation:" + phoneNum;
        String value = redisAPI.get(key);
        if (value != null && value.equals(code)) {
            ItripUser user = findByUsername(phoneNum);
            if (user != null) {
                //2、更新用户激活状态
                user.setActivated(1);  //表示激活
                user.setFlatID(user.getId()); //设置平台ID
                user.setUserType(0);
                itripUserMapper.updateItripUser(user);
                return true;
            }
        }
        return false;
    }

}
