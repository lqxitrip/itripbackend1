package cn.itrip.service.orderlinkuser;

import cn.itrip.beans.vo.order.ItripOrderLinkUserVo;

import java.util.List;
import java.util.Map;

public interface ItripOrderLinkUserService {
    //删除联系人
    /**
     * 查询所有未支付的订单所关联的所有常用联系人
     * @return
     */
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception;

    //根据订单ID获取订单信息
    public List<ItripOrderLinkUserVo>  getItripOrderLinkUserListByMap(Map<String, Object> param)throws Exception;
}
