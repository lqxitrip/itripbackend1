package cn.itirp.auth.test;

import cn.itrip.auth.service.UserService;
import cn.itrip.beans.pojo.ItripUser;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserServiceTest {
    @Test
    public void testInsertUser() {
        //启动Spring
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
        UserService userService = (UserService)ctx.getBean("userService");
        ItripUser user = new ItripUser();
        user.setUserCode("ftfshadiao@aliyun.com");
        user.setUserName("FTF");
        try {
            //测试用户的新增操作
            userService.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
