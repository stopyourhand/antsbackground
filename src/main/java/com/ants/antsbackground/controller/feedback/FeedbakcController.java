package com.ants.antsbackground.controller.feedback;


import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.FeedbackDTO;
import com.ants.antsbackground.entity.feedback.Feedback;
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
    @RequestMapping(value = "/listFeedback", method = RequestMethod.GET)
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
        List<FeedbackDTO> feedbackList = feedbackService.listFeedback(parameterMap);
        resultMap.put("feedbackList", feedbackList);

        //获取用户反馈信息的数量
        int countFeedbackNumber = feedbackService.countFeedbackNumber();

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
     * 判断对用户反馈的信息进行哪种删除操作
     * type -> 0 -> 删除
     * type -> 1 -> 撤销删除
     * type -> 2 -> 彻底删除
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/deleteFeedback", method = RequestMethod.DELETE)
    public Map deleteFeedback(@RequestParam(value = "type") Integer type,
                              @RequestParam(value = "idList[]") int[] idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);

        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }

        if (idList.length <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
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

//    /**
//     * 将用户反馈的信息添加到数据库中
//     *
//     * @param request
//     * @param fbContent
//     * @param fbSatisfaction
//     * @param userMobile
//     * @param userName
//     * @return
//     */
//    @RequestMapping(value = "/insertFeedback", method = RequestMethod.POST)
//    @ResponseBody
//    public Map<String, String> insertFeedback(HttpServletRequest request,
//                                              @RequestParam(value = "fbContent") String fbContent,
//                                              @RequestParam(value = "fbSatisfaction") String fbSatisfaction,
//                                              @RequestParam(value = "userMobile") String userMobile,
//                                              @RequestParam(value = "userName") String userName) {
//        //用来保存返回给前端数据的map
//        Map<String, String> resultMap = new HashMap(16);
//
//        //保存前端传输的信息
//        Feedback feedback = new Feedback();
//
//        //获取反馈编号
//        String fbId = SerialNumberUtil.getFeedbackUUID();
//        if (fbId != null && fbId.equals("")) {
//            int feedbackId = Integer.parseInt(fbId);
//            //设置反馈编号
//            feedback.setFbId(feedbackId);
//        }
//
//        //获取反馈用户的id
//        Integer userId = (Integer) request.getSession().getAttribute("userId");
//        if (userId == null) {
//            resultMap.put("msg", "用户未登录！");
//            return resultMap;
//        }
//        //设置反馈用户id
//        feedback.setUserId(userId);
//
//        //获取反馈内容
//        if (fbContent == null && fbContent.equals("")) {
//            resultMap.put("msg", "用户反馈内容为空，请填写!");
//            return resultMap;
//        }
//        //设置用户反馈内容
//        feedback.setFbContent(fbContent);
//
//        //获取用户满意度:0:非常满意,1:满意,2:一般,3:不满意,4:非常不满意
//        if (fbSatisfaction == null && fbSatisfaction.equals("")) {
//            resultMap.put("msg", "用户满意度信息错误!");
//            return resultMap;
//        }
//        //设置默认满意度为"非常满意"
//        int fbSatisfy = 0;
//        switch (fbSatisfaction) {
//            case "非常满意":
//                fbSatisfy = 0;
//                break;
//            case "满意":
//                fbSatisfy = 1;
//                break;
//            case "一般":
//                fbSatisfy = 2;
//                break;
//            case "不满意":
//                fbSatisfy = 3;
//                break;
//            case "非常不满意":
//                fbSatisfy = 4;
//                break;
//            default: {
//            }
//        }
//
//        //设置用户反馈满意度
//        feedback.setFbSatisfaction(fbSatisfy);
//
//        if (userMobile == null && userMobile.equals("")) {
//            resultMap.put("msg", "用户手机号为空!");
//            return resultMap;
//        }
//        //设置用户手机号码
//        feedback.setUserMobile(userMobile);
//
//        if (userName == null && userName.equals("")) {
//            resultMap.put("msg", "用户名称为空!");
//            return resultMap;
//        }
//        //设置用户名称
//        feedback.setUserName(userName);
//
//        //获取反馈时间
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
//        String time = format.format(new Date());
//        //设置用户反馈时间
//        feedback.setFbTime(time);
//
//        //将用户提交的反馈信息存进数据库
//        int result = feedbackService.insertFeedback(feedback);
//        //判断添加进数据库是否成功
//        if (result <= 0) {
//            resultMap.put("msg", "提交失败!");
//            return resultMap;
//        }
//
//        resultMap.put("msg", "提交成功!");
//        return resultMap;
//    }


}
