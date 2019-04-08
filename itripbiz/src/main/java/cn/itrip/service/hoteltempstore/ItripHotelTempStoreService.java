package cn.itrip.service.hoteltempstore;

import cn.itrip.beans.vo.store.StoreVO;

import java.util.List;
import java.util.Map;

public interface ItripHotelTempStoreService {
    //预生成订单信息
    public List<StoreVO> queryRoomStore(Map<String, Object> param)throws Exception;
    //添加订单
    public boolean validateRoomStore(Map<String, Object> param)throws Exception;
}
