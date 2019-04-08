package cn.itrip.auth.service.impl;

import cn.itrip.auth.service.SmsService;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("smsService")
public class SmsServiceImpl implements SmsService {
    /**
     * 用于发送短信。
     * @param phoneNum 将短信发送给谁
     * @param templateId 短信模板的ID
     * @param str 需要发送的内容
     */
    @Override
    public void sendMsg(String phoneNum, String templateId, String[] str) throws Exception {
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        //1、初始【开发者账号】信息中的URL。
        sdk.init("app.cloopen.com","8883");
        //ACCOUNT SID和AUTH TOKEN
        sdk.setAccount("8a216da86904c060016927db3d360df2","a00c5bd7c58849a18b33321cdc63ca43");
        sdk.setAppId("8a216da86904c060016927db3d8b0df9"); //AppID
        HashMap result = sdk.sendTemplateSMS(phoneNum,templateId, str);
        //查看短信是否发送成功。就通过查看statusCoe的值是否为000000
        if ("000000".equals(result.get("statusCode"))) {
            System.out.println("短信发送成功");
        } else {
            throw new Exception(result.get("statusCode").toString() + ":" + result.get("statusMsg").toString());
        }

    }
}
