package com.ants.antsbackground.mapper.feedback;

import com.ants.antsbackground.entity.feedback.Feedback;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理用户反馈的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/10
 * @Version: V1.0
 */
@Mapper
public interface FeedbackMapper {


    /**
     * 获取用户的反馈列表信息
     *
     * @param parameterMap
     * @return
     */
    List<Feedback> listFeedback(Map<String, Integer> parameterMap);

    /**
     * 获取用户反馈信息的数量
     * @return
     */
    Integer countFeedbackNumber();

    /**
     * 彻底删除用户反馈信息
     * @param fbId
     * @return
     */
    Integer deleteFeedback(Integer fbId);

    /**
     * 将反馈信息状态进行更改，即进入回收站
     * @param parameterMap
     * @return
     */
    Integer updateFeedback(Map<String,Integer> parameterMap);
}
