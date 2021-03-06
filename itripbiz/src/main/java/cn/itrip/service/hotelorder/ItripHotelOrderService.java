package cn.itrip.service.hotelorder;

import cn.itrip.beans.pojo.ItripHotelOrder;
import cn.itrip.beans.pojo.ItripUserLinkUser;
import cn.itrip.beans.vo.order.ItripListHotelOrderVO;
import cn.itrip.beans.vo.order.ItripPersonalOrderRoomVO;
import cn.itrip.common.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ItripHotelOrderService {

    //添加订单
    /**
     * 根据订单的预定天数和房间的单价计算订单总金额 -add by donghai
     * @param count ,roomId count为天数和房间数量的乘积
     */
    public BigDecimal getOrderPayAmount(int count, Long roomId) throws Exception;

    public Map<String, String> itriptxAddItripHotelOrder(ItripHotelOrder itripHotelOrder, List<ItripUserLinkUser> itripOrderLinkUserList)throws Exception;

    //根据订单ID查看个人订单详情
    public ItripHotelOrder getItripHotelOrderById(Long id)throws Exception;
    //分页查询个人酒店订单列表
    public Page<ItripListHotelOrderVO> queryOrderPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;
    /**
     * 通过订单id查看订单详情-具体房型信息等- add by hanlu
     */
    public ItripPersonalOrderRoomVO getItripHotelOrderRoomInfoById(Long orderId)throws Exception;

}
