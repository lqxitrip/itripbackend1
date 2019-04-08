package cn.itrip.service.orderlinkuser;

import cn.itrip.beans.vo.order.ItripOrderLinkUserVo;
import cn.itrip.dao.orderlinkuser.ItripOrderLinkUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class ItripOrderLinkUserServiceImpl implements ItripOrderLinkUserService {
    @Resource
    private ItripOrderLinkUserMapper itripOrderLinkUserMapper;

    //删除联系人
    @Override
    public List<Long> getItripOrderLinkUserIdsByOrder() throws Exception {
        return itripOrderLinkUserMapper.getItripOrderLinkUserIdsByOrder();
    }


    //根据订单ID获取订单信息
    @Override
    public List<ItripOrderLinkUserVo> getItripOrderLinkUserListByMap(Map<String, Object> param) throws Exception {
        return itripOrderLinkUserMapper.getItripOrderLinkUserListByMap(param);
    }
}
