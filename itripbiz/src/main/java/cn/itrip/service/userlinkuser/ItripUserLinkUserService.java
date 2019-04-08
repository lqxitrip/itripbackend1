package cn.itrip.service.userlinkuser;

import cn.itrip.beans.pojo.ItripUserLinkUser;

import java.util.List;
import java.util.Map;

public interface ItripUserLinkUserService {
    //2修改联系人
    public Integer modifyItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;
    //删除联系人
    public Integer deleteItripUserLinkUserByIds(Long[] ids)throws Exception;
    //添加常用联系人
    public Integer addItripUserLinkUser(ItripUserLinkUser itripUserLinkUser)throws Exception;
    // 查询用户联系人列表
    public List<ItripUserLinkUser> getItripUserLinkUserListByMap(Map<String, Object> param)throws Exception;
}
