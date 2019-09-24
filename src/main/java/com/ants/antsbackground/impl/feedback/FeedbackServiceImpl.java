package com.ants.antsbackground.impl.feedback;

import com.ants.antsbackground.dto.FeedbackDTO;
import com.ants.antsbackground.entity.feedback.Feedback;
import com.ants.antsbackground.mapper.feedback.FeedbackMapper;
import com.ants.antsbackground.service.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private FeedbackMapper feedbackMapper;


    /**
     * 获取用户的反馈列表信息
     *
     * @param parameterMap
     * @return
     */
    public List<FeedbackDTO> listFeedback(Map<String, Integer> parameterMap) {
        //返回从数据库中获取的用户反馈的信息列表
        List<Feedback> feedbackList = feedbackMapper.listFeedback(parameterMap);
        //声明一个保存新的返回数据的格式的DTO列表
        List<FeedbackDTO> feedbackDTOList = new LinkedList<>();

        //获取用户反馈信息列表的长度
        int length = feedbackList.size();

        //声明反馈对象
        Feedback feedback = null;
        FeedbackDTO feedbackDTO = null;

        //遍历用户反馈信息列表，将列表中的数据对象进行DTO数据对象封装
        for (int index = 0; index < length; index++) {
            //获取单个反馈信息
            feedback = feedbackList.get(index);

            //获取要返回给前端的数据格式的参数和数据
            int fbId = feedback.getFbId();
            String fbSerial = feedback.getFbSerial();
            int fbSatisfaction = feedback.getFbSatisfaction();
            String fbTime = feedback.getFbTime();

            feedbackDTO = new FeedbackDTO();
            //DTO赋值
            feedbackDTO.setFbId(fbId);
            feedbackDTO.setFbSerial(fbSerial);
            feedbackDTO.setFbSatisfaction(fbSatisfaction);
            feedbackDTO.setFbTime(fbTime);

            //将新的传输对象DTO添加到返回值列表里
            feedbackDTOList.add(feedbackDTO);
        }

        return feedbackDTOList;
    }

    /**
     * 获取用户反馈信息的数量
     *
     * @return
     */
    public Integer countFeedbackNumber() {
        return feedbackMapper.countFeedbackNumber();
    }

    /**
     * 彻底删除用户反馈信息
     *
     * @param fbId
     * @return
     */
    public Integer deleteFeedback(Integer fbId) {
        return feedbackMapper.deleteFeedback(fbId);
    }

    /**
     * 将反馈信息状态进行更改，即进入回收站
     * @param parameterMap
     * @return
     */
    public Integer updateFeedback(Map<String,Integer> parameterMap){
        return feedbackMapper.updateFeedback(parameterMap);
    }
}
