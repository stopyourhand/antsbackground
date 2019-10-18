package com.ants.antsbackground.dto.feedback;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class DecorationDTO {
    /** 主键 */
    private int id;
    /** 标题 */
    private String title;
    /** 内容 */
    private String content;
    /** 时间 */
    private String time;
    /** 编号 */
    private String serial;
    /** 满意度 */
    private int satisfaction;
}
