package com.ants.antsbackground.dto;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class BrowseDTO {
    /** 学生访问次数 */
    private Integer studentNumber;
    /** 教师访问次数 */
    private Integer teacherNumber;
    /** 游客访问次数 */
    private Integer visitorNumber;
}
