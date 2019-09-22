package com.ants.antsbackground.mapper.feedback;

import com.ants.antsbackground.entity.feedback.Feedback;
import org.apache.ibatis.annotations.Mapper;

/**
 *处理用户反馈的数据库层
 * @Author czd
 * @Date:created in 2019/9/10
 * @Version: V1.0
 */
@Mapper
public interface FeedbackMapper {

    /**
     * 将用户进行反馈的信息添加到数据库中
     * @param feedback
     * @return
     */
    int insertFeedback(Feedback feedback);
}
