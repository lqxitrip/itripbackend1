package cn.itrip.auth.service;

public interface SmsService {
    /**
     * 用于发送短信。
     * @param phoneNum 将短信发送给谁
     * @param templateId 短信模板的ID
     * @param str 需要发送的内容
     */
    public void sendMsg(String phoneNum,String templateId, String [] str) throws Exception;

}
