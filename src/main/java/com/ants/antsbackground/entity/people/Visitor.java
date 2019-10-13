package com.ants.antsbackground.entity.people;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author czd
 * @Date:createed in 2019/10/8
 * @Version: V1.0
 */
@Component
@Data
public class Visitor {
    /** 主键 */
    private Integer id;
    /** 游客真实ip地址 */
    private String ip;
    /** 游客类型 0 正常 1 回收站 */
    private Integer userType;
    /** 游客浏览时间 */
    private String browseTime;
}
