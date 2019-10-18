package com.ants.antsbackground.dto.announcement;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class AnnouncementDTO {
    /** 公告表编号 */
    private Integer id;
    /** 公告的标题  */
    private String title;
    /** 公告的内容 */
    private String content;
    /** 公告发布的时间 */
    private String time;
}
