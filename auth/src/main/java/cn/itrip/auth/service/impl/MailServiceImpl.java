package cn.itrip.auth.service.impl;

import cn.itrip.auth.service.MailService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("mailService")
public class MailServiceImpl implements MailService {

    @Resource
    private SimpleMailMessage activationMailMessage;//允许用户快速设置邮件内容的各种属性信息

    @Resource
    private MailSender mailSender;//API是用于发送邮件

    //https://mail.aliyun.com  进行个人邮箱注册。
    @Override
    public void sendActivationMail(String mailTo, String activationCode) {
        activationMailMessage.setTo(mailTo);//设置收件人的邮箱地址
        activationMailMessage.setText("您的激活码是："+activationCode);//设置发送邮箱的内容
        mailSender.send(activationMailMessage);//实现最后邮件发送的工作
    }
}
