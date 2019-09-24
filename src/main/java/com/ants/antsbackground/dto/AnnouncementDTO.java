package com.ants.antsbackground.dto;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class AnnouncementDTO {
    /** 公告表编号 */
    private Integer annId;
    /** 公告的标题  */
    private String annTitle;
    /** 公告的内容 */
    private String annContent;
    /** 公告发布的时间 */
    private String annReleaseTime;
}
