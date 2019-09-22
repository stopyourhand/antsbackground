package com.ants.antsbackground.entity.feedback;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 对应数据库中ants_management_feedback的表
 *
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Component
@Data
public class Feedback {
    /** 反馈编号 */
    private Integer fbId;
    /** 用户编号 */
    private Integer userId;
    /** 反馈内容 */
    private String fbContent;
    /** 反馈时间 */
    private String fbTime;
    /** 满意度:0:非常满意,1:满意,2:一般,3:不满意,4:非常不满意 */
    private Integer fbSatisfaction;
    /** 反馈人电话 */
    private String userMobile;
    /** 反馈人姓名 */
    private String userName;

}
