package com.ants.antsbackground.dto;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in 2019/10/8
 * @Version: V1.0
 */
@Data
public class VisitorDTO {
    /** 主键 */
    private Integer id;
    /** 游客真实ip地址 */
    private String ip;
}
