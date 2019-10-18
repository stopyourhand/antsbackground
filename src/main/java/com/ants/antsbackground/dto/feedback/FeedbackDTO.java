package com.ants.antsbackground.dto.feedback;

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
    private Integer id;
    /** 反馈编号 */
    private String serial;
    /** 用户满意度 */
    private Integer satisfaction;
    /** 用户反馈时间 */
    private String time;
}
