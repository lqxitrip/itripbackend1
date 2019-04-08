package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface UserService {
    //用于登录
    public ItripUser login(String userCode,String password) throws Exception;

    /**
     *完成对用户的新增操作
     * @param itripUser 用户对象
     * @return
     * @throws Exception
     */
    public void insertUser(ItripUser itripUser)throws Exception;

    /**
     * 邮箱激活 activate方法用于激活验证码、更新用户信息
     * @param mail 邮箱地址
     * @param code 为存入到Redis的激活码所对应的key。格式是：activation: + 验证码
     * @return true/false，true表示验证成功
     */
    public boolean activate(String mail,String code) throws Exception ;

    /*验证用户唯一*/
    public ItripUser findByUsername(String userCode);

    /**
     * 通过手机注册完成用户的新增操作
     * @param user 用户对象
     */
    public void insertUserByPhone(ItripUser user) throws Exception;

    /**
     * 短信验证
     * @param phoneNum 手机号码
     * @param code 验证码
     * @return true表示验证成功，false表示验证失败。
     */
    public boolean validatePhone(String phoneNum,String code) throws Exception;

}
