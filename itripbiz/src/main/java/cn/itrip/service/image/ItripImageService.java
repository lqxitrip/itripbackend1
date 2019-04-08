package cn.itrip.service.image;

import cn.itrip.beans.vo.ItripImageVO;

import java.util.List;
import java.util.Map;

public interface ItripImageService {
    //查询评论附带的图片//查询酒店房型的图片
    public List<ItripImageVO> getItripImageListByMap(Map<String, Object> param)throws Exception;
}
