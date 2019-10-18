package com.ants.antsbackground.controller.feedback;


import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.feedback.DecorationDTO;
import com.ants.antsbackground.dto.feedback.FeedbackDTO;
import com.ants.antsbackground.service.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对用户反馈进行业务操作
 *
 * @Author czd
 * @Date: create in 2019/9/10
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/feedback")
public class FeedbakcController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 列出用户反馈信息的列表
     *
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Map listFeedback(@RequestParam(value = "currentPage") int currentPage) {
        //返回给前端数据的map
        Map resultMap = new HashMap(16);
        //用来保存listFeedback方法中的head和length参数
        Map<String, Integer> parameterMap = new HashMap<>(16);

        if (currentPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }

        //获取页面数开始的数据信息在数据库的坐标信息
        int head = (currentPage - 1) * PageConsts.FEEDBACK_PAGE_NUMBER;

        //传入初始页面的列表返回值
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.FEEDBACK_PAGE_NUMBER);

        //获取符合条件的返回列表的数据
        List<DecorationDTO> feedbackList = feedbackService.listFeedback(parameterMap);
        resultMap.put("feedbackList", feedbackList);

        //获取用户反馈信息的数量,设置state为0代表还没有被回收进回收站的反馈信息
        int state = 0;
        int countFeedbackNumber = feedbackService.countFeedbackNumber(state);

        //计算反馈列表的总页数
        int allPage = (countFeedbackNumber / PageConsts.FEEDBACK_PAGE_NUMBER) + 1;
        if (allPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }

        resultMap.put("allPage", allPage);
        return resultMap;
    }

    /**
     * 列出反馈信息回收站信息的列表
     *
     * @param currentPage
     * @return
     */
    @RequestMapping(value = "/listRecycle", method = RequestMethod.GET)
    public Map listFeedbackRecycle(@RequestParam(value = "currentPage") int currentPage) {
        //返回给前端数据的map
        Map resultMap = new HashMap(16);

        if (currentPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }

        //用来保存listFeedbackRecycle方法中的head和length参数
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //获取用户反馈信息的数量,设置state为1，表示为回收站的反馈信息
        int state = 1;
        int countFeedbackNumber = feedbackService.countFeedbackNumber(state);

        //计算反馈列表的总页数
        int allPage = (countFeedbackNumber / PageConsts.FEEDBACK_PAGE_NUMBER) + 1;
        if (allPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }

        resultMap.put("allPage", allPage);

        //获取页面数开始的数据信息在数据库的坐标信息
        int head = (currentPage - 1) * PageConsts.FEEDBACK_PAGE_NUMBER;

        //传入初始页面的列表返回值
        parameterMap.put("state",1);
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.FEEDBACK_PAGE_NUMBER);

        //获取符合条件的返回列表的数据
        List<FeedbackDTO> feedbackList = feedbackService.listFeedbackRecycle(parameterMap);
        resultMap.put("feedbackList", feedbackList);
        return resultMap;
    }

    /**
     * 判断对用户反馈的信息进行哪种删除操作
     * type -> 0 -> 删除
     * type -> 1 -> 撤销删除
     * type -> 2 -> 彻底删除
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map deleteFeedback(@RequestParam(value = "type") Integer type,
                              @RequestParam(value = "idList") List<Integer> idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //判断格式是否正确
        if (idList.size() <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
            return resultMap;
        }
        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }


        //对删除的操作类型进下判断
        switch (type) {
            //删除操作，即将用户反馈信息放进回收站
            case 0:
                //获取要删除的反馈信息的id列表，对其进行状态改变，即弄进回收站里面
                for (int fbId : idList) {
                    parameterMap.put("fbId", fbId);
                    parameterMap.put("state", 1);
                    int result = feedbackService.updateFeedback(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //撤销删除操作
            case 1:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                for (int fbId : idList) {
                    parameterMap.put("fbId", fbId);
                    parameterMap.put("state", 0);
                    int result = feedbackService.updateFeedback(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //彻底删除
            case 2:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                for (int fbId : idList) {
                    int result = feedbackService.deleteFeedback(fbId);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
        }
        resultMap.put("msg", "删除成功!");
        return resultMap;
    }

}
