package com.ants.antsbackground.service.feedback;

import com.ants.antsbackground.entity.feedback.Feedback;
import org.springframework.stereotype.Service;

/**
 * 处理用户反馈的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service
public interface FeedbackService {

    /**
     * 将用户进行反馈的信息添加到数据库中
     * @param feedback
     * @return
     */
    int insertFeedback(Feedback feedback);
}
