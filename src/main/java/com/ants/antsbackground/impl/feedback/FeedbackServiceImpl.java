package com.ants.antsbackground.impl.feedback;

import com.ants.antsbackground.mapper.feedback.FeedbackMapper;
import com.ants.antsbackground.entity.feedback.Feedback;
import com.ants.antsbackground.service.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理用户反馈的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("FeedbackService")
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackDao;

    /**
     * 将用户进行反馈的信息添加到数据库中
     * @param feedback
     * @return
     */
    public int insertFeedback(Feedback feedback){

        return feedbackDao.insertFeedback(feedback);
    }
}
