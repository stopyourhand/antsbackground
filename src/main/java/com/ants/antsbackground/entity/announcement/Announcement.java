package com.ants.antsbackground.entity.announcement;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 对应公告的数据库表
 * @Author czd
 * @Date:createed in 2019/9/24
 * @Version: V1.0
 */
@Data
@Component
public class Announcement {
    /** 公告表编号 */
    private Integer annId;
    /** 公告的标题  */
    private String annTitle;
    /** 公告的内容 */
    private String annContent;
    /** 公告发布的时间 */
    private String annTime;
    /** 判断公告状态: 0 正常,1回收站( 1 删除,2 撤销删除, 3 彻底删除) */
    private Integer state;
}
