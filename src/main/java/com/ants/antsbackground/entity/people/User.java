package com.ants.antsbackground.entity.people;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
@Component
public class User {
    /** 主键，编号 */
    private Integer studentId;
    /** 用户名称 */
    private String userName;
    /** 用户性别 0:男  1：女 */
    private Integer userSex;
    /** 用户密码 */
    private String passWord;
    /** 用户加密后的密码 */
    private String unpw;
    /** 用户手机号码 */
    private String mobilePhone;
    /** 用户交易地址 */
    private String address;
    /** 用户头像图片路径 */
    private String portrait;
    /** 用户微信号 */
    private String userWechat;
    /** 用户QQ号 */
    private String userQQ;
    /** 用户注册时间 */
    private String registTime;
    /** QQ是否隐藏 0:不隐藏 1:隐藏 */
    private Integer qqHidden;
    /** 微信是否隐藏 0:不隐藏 1:隐藏 */
    private Integer wechatHidden;
    /** 用户状态 0:正常 1:回收站 */
    private Integer userType;
}
