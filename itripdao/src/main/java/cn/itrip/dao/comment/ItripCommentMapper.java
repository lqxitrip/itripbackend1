package cn.itrip.dao.comment;

import cn.itrip.beans.pojo.ItripComment;
import cn.itrip.beans.vo.comment.ItripListCommentVO;
import cn.itrip.beans.vo.comment.ItripScoreCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItripCommentMapper {
    //查询酒店评分
    public ItripScoreCommentVO getCommentAvgScore(@Param(value = "hotelId") Long hotelId)throws Exception;

    //查询评论数量
    public Integer getItripCommentCountByMap(Map<String, Object> param)throws Exception;

    //查询评论内容列表
    public List<ItripListCommentVO> getItripCommentListByMap(Map<String, Object> param)throws Exception;

    //新增评论
    public Long insertItripComment(ItripComment itripComment)throws Exception;
}
