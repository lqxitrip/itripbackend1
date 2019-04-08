package cn.itrip.dao.auth;

import cn.itrip.beans.pojo.ItripUser;

import java.util.List;
import java.util.Map;

public interface ItripUserMapper {
    /**
     * 根据Map集合中的参数列表查询指定的用户对象信息
     * @param map
     * @return
     */
    List<ItripUser> queryUserByMap(Map<String, Object> map);

    /**
     *完成对用户的新增操作
     * @param itripUser 用户对象
     * @return
     * @throws Exception
     */
    public  Integer insertItripUser(ItripUser itripUser)throws Exception;

    //验证用户是否存在
    public List<ItripUser> getItripUserListByMap(Map<String, Object> param);

    //修改
    public Integer updateItripUser(ItripUser itripUser)throws Exception;


}
