package com.ants.antsbackground.service.feedback;

import com.ants.antsbackground.dto.feedback.DecorationDTO;
import com.ants.antsbackground.dto.feedback.FeedbackDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
     * 获取用户的反馈列表信息
     *
     * @param parameterMap
     * @return
     */
    List<DecorationDTO> listFeedback(Map<String, Integer> parameterMap);

    /**
     * 获取回收站里的公告信息
     * @param parameterMap
     * @return
     */
    List<FeedbackDTO> listFeedbackRecycle(Map<String,Integer> parameterMap);

    /**
     * 获取用户反馈信息的数量
     * @return
     */
    Integer countFeedbackNumber(Integer state);

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
