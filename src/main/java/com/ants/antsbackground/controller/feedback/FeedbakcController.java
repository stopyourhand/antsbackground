package com.ants.antsbackground.controller.feedback;


import com.ants.antsbackground.entity.feedback.Feedback;
import com.ants.antsbackground.service.feedback.FeedbackService;
import com.ants.antsbackground.util.ShopIdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 对用户反馈进行业务操作
 *
 * @Author czd
 * @Date: create in 2019/9/10
 * @Version: V1.0
 */
@Controller
@RequestMapping(value = "/ants/feedback")
public class FeedbakcController {

    @Autowired
    private FeedbackService feedbackService;


    /**
     * 将用户反馈的信息添加到数据库中
     *
     * @param request
     * @param fbContent
     * @param fbSatisfaction
     * @param userMobile
     * @param userName
     * @return
     */
    @RequestMapping(value = "/insertFeedback", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> insertFeedback(HttpServletRequest request,
                                              @RequestParam(value = "fbContent") String fbContent,
                                              @RequestParam(value = "fbSatisfaction") String fbSatisfaction,
                                              @RequestParam(value = "userMobile") String userMobile,
                                              @RequestParam(value = "userName") String userName) {
        //用来保存返回给前端数据的map
        Map<String, String> resultMap = new HashMap(16);

        //保存前端传输的信息
        Feedback feedback = new Feedback();

        //获取反馈编号
        String fbId = ShopIdUtil.getFeedbackUUID();
        if (fbId != null && fbId.equals("")) {
            int feedbackId = Integer.parseInt(fbId);
            //设置反馈编号
            feedback.setFbId(feedbackId);
        }

        //获取反馈用户的id
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            resultMap.put("msg", "用户未登录！");
            return resultMap;
        }
        //设置反馈用户id
        feedback.setUserId(userId);

        //获取反馈内容
        if (fbContent == null && fbContent.equals("")) {
            resultMap.put("msg", "用户反馈内容为空，请填写!");
            return resultMap;
        }
        //设置用户反馈内容
        feedback.setFbContent(fbContent);

        //获取用户满意度:0:非常满意,1:满意,2:一般,3:不满意,4:非常不满意
        if (fbSatisfaction == null && fbSatisfaction.equals("")) {
            resultMap.put("msg", "用户满意度信息错误!");
            return resultMap;
        }
        //设置默认满意度为"非常满意"
        int fbSatisfy = 0;
        switch (fbSatisfaction) {
            case "非常满意":
                fbSatisfy = 0;
                break;
            case "满意":
                fbSatisfy = 1;
                break;
            case "一般":
                fbSatisfy = 2;
                break;
            case "不满意":
                fbSatisfy = 3;
                break;
            case "非常不满意":
                fbSatisfy = 4;
                break;
            default: {
            }
        }

        //设置用户反馈满意度
        feedback.setFbSatisfaction(fbSatisfy);

        if (userMobile == null && userMobile.equals("")) {
            resultMap.put("msg", "用户手机号为空!");
            return resultMap;
        }
        //设置用户手机号码
        feedback.setUserMobile(userMobile);

        if (userName == null && userName.equals("")) {
            resultMap.put("msg", "用户名称为空!");
            return resultMap;
        }
        //设置用户名称
        feedback.setUserName(userName);

        //获取反馈时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(new Date());
        //设置用户反馈时间
        feedback.setFbTime(time);

        //将用户提交的反馈信息存进数据库
        int result = feedbackService.insertFeedback(feedback);
        //判断添加进数据库是否成功
        if (result <= 0) {
            resultMap.put("msg", "提交失败!");
            return resultMap;
        }

        resultMap.put("msg", "提交成功!");
        return resultMap;
    }
}
