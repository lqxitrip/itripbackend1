package cn.itrip.auth.service;

import cn.itrip.beans.pojo.ItripUser;

public interface TokenService {
    /**
     * 根据用户登录生成Token
     * @Param userAgent 用户的唯一标识
     * request.getHeader("User-Agent"),用于判断当前用户是通过xx操作系统及xx客户端，移动端还是PC端
     * @Param user  用户对象
     * @return 生成好后的token字符串
     */
    public String generateToken(String userAgent, ItripUser user);


    /**
     * 以token字符串 为key，ItripUser对象为值，保存到Redis缓存数据库中。
     * @Param token Token对象，
     */
    public String save(String token, ItripUser user);


    /**
     * 验证Token
     * @param userAgent 为了保证每一个用户都使用同一个设备进行登录(访问服务器的客户端是唯一的)。
     * @param token token字符串
     * @return 返回验证后的结果，true表示验证通过。
     * userAget、token都是登录成功后保存客户端的请求头文件中——header
     */
    public boolean validate(String userAgent,String token);

    void delete(String token);

    String reloadToken(String header, String token) throws Exception;
}
