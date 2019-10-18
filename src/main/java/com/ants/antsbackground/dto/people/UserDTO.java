package com.ants.antsbackground.dto.people;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class UserDTO {
    /** 用户编号，主键 */
    private Integer id;
    /** 用户名称 */
    private String userName;
    /** 用户手机号码 */
    private String phone;
    /** 用户QQ号 */
    private String QQ;
    /** 用户微信号 */
    private String weChat;
    /** 用户交易地址 */
    private String address;
}
