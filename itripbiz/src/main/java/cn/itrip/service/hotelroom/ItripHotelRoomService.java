package cn.itrip.service.hotelroom;

import cn.itrip.beans.pojo.ItripHotelRoom;
import cn.itrip.beans.vo.hotelroom.ItripHotelRoomVO;

import java.util.List;
import java.util.Map;

public interface  ItripHotelRoomService {
    //查询酒店房型列表
    public List<ItripHotelRoomVO> getItripHotelRoomListByMap(Map<String, Object> param)throws Exception;
    //预生成订单信息
    public ItripHotelRoom getItripHotelRoomById(Long id)throws Exception;
}
