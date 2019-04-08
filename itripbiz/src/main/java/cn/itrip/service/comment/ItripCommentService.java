package cn.itrip.service.comment;

import cn.itrip.beans.pojo.ItripComment;
import cn.itrip.beans.pojo.ItripImage;
import cn.itrip.beans.vo.comment.ItripListCommentVO;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import cn.itrip.common.Page;

import java.util.List;
import java.util.Map;

public interface ItripCommentService {
    //查询酒店评分
    /**
     * 根据酒店的id查询并计算所有点评的位置、设施、服务、卫生和综合评分-add by donghai
     * @param hotelId 酒店的id
     */
    public ItripScoreCommentVO getAvgAndTotalScore(Long hotelId) throws Exception;

    //查询评论数量
    public Integer getItripCommentCountByMap(Map<String, Object> param)throws Exception;

    //查询评论内容列表
    public Page<ItripListCommentVO> queryItripCommentPageByMap(Map<String, Object> param, Integer pageNo, Integer pageSize)throws Exception;

    //新增评论
    /**
     * 新增评论
     * @throws Exception
     */
    public boolean itriptxAddItripComment(ItripComment obj, List<ItripImage> itripImages)throws Exception;

}
