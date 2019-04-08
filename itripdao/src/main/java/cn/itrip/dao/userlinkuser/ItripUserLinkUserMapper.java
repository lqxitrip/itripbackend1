package cn.itrip.dao.userlinkuser;

import cn.itrip.beans.pojo.ItripUserLinkUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripUserLinkUserMapper {
    //2修改联系人
    public Integer updateItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;
    //删除联系人
    public Integer deleteItripUserLinkUserByIds(@Param(value = "ids") Long[] ids)throws Exception;
    //添加常用联系人
    public Integer insertItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;
    // 查询用户联系人列表
    public List<ItripUserLinkUser> getItripUserLinkUserListByMap(Map<String, Object> param)throws Exception;
}
