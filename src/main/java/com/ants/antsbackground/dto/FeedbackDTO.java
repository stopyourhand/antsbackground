package com.ants.antsbackground.dto;

import lombok.Data;

/**
 * 用来对反馈数据表的字段进行保护，转换数据传输对象的格式
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class FeedbackDTO {
    /** 主键 */
    private Integer fbId;
    /** 反馈编号 */
    private String fbSerial;
    /** 用户满意度 */
    private Integer fbSatisfaction;
    /** 用户反馈时间 */
    private String fbTime;
}
