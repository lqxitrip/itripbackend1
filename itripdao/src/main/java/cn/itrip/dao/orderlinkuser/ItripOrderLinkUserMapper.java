package cn.itrip.dao.orderlinkuser;

import cn.itrip.beans.pojo.ItripOrderLinkUser;
import cn.itrip.beans.vo.order.ItripOrderLinkUserVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripOrderLinkUserMapper {
    //添加订单
    public Integer deleteItripOrderLinkUserByOrderId(@Param(value = "orderId") Long orderId)throws Exception;
    public Integer insertItripOrderLinkUser(ItripOrderLinkUser itripOrderLinkUser)throws Exception;
    //删除联系人
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;
    public Integer deleteItripUserLinkUserByIds(@Param(value = "ids") Long[] ids)throws Exception;

    //根据订单ID获取订单信息
    public List<ItripOrderLinkUserVo> getItripOrderLinkUserListByMap(Map<String, Object> param)throws Exception;
}
