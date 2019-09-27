package com.ants.antsbackground.dto;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class PersonalDataDTO {
    /** 当前网站版本 */
    private String currentVersion;
    /** 用户(管理员)名称 */
    private String adminName;
    /** 上次登录时间 */
    private String lastLoginTime;
    /** 上次登录IP */
    private String lastLoginIp;
    /** 本次登录时间 */
    private String loginTime;
    /** 本次登录IP */
    private String loginIp;
}
