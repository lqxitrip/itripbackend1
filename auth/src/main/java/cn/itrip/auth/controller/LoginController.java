package cn.itrip.auth.controller;

import cn.itrip.auth.service.TokenService;
import cn.itrip.auth.service.UserService;
import cn.itrip.beans.dto.Dto;
import cn.itrip.beans.pojo.ItripUser;
import cn.itrip.beans.vo.ItripTokenVO;
import cn.itrip.common.DtoUtil;
import cn.itrip.common.EmptyUtils;
import cn.itrip.common.ErrorCode;
import cn.itrip.common.MD5;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

@Controller
@RequestMapping("/api")
public class LoginController {

    @Resource
    private TokenService tokenService;

    @Resource
    private UserService userService;


    /**
     * 登录的请求
     * @param name 用户编号
     * @param password 密码
     * @param request 请求对象
     * @return Dto 如果登录成功，将token的信息对象进行返回，包括token字符串，生成时间，有效时间。
     */
    @RequestMapping(value = "/dologin",method = RequestMethod.POST)
    @ResponseBody
    public Dto doLogin(@RequestParam String name,
                       @RequestParam String password, HttpServletRequest request) throws Exception {
        try {
            ItripUser user = userService.login(name, MD5.getMd5(password,32));//调用业务层的登录方法
            if(EmptyUtils.isNotEmpty(user)){//判断user是否不为空
                String userAgent = request.getHeader("User-Agent");
                String token = tokenService.generateToken(userAgent,user);//生成token字符串
                //以token字符串 为key，ItripUser对象为值，保存到Redis缓存数据库中。
                tokenService.save(token, user);
                long expTime = Calendar.getInstance().getTimeInMillis() + 2*60*60*1000;//有效时间
                long genTime = Calendar.getInstance().getTimeInMillis() ;//生成时间
                ItripTokenVO vo = new ItripTokenVO(token,expTime,genTime);
                return DtoUtil.returnDataSuccess(vo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("用户名或密码不正确", ErrorCode.AUTH_AUTHENTICATION_FAILED);
        }
        return null;
    }



    /**
     * 退出
     */
    @RequestMapping(value="/logout", method = RequestMethod.GET,headers = "token")
    @ResponseBody
    public Dto logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            boolean result = tokenService.validate(request.getHeader("user-agent"), token);
            if (result) {
                tokenService.delete(token);
                return DtoUtil.returnSuccess("退出成功！");
            } else {
                return DtoUtil.returnFail("token无效", ErrorCode.AUTH_TOKEN_INVALID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return DtoUtil.returnFail("退出失败",ErrorCode.AUTH_TOKEN_INVALID);
        }
    }

}
