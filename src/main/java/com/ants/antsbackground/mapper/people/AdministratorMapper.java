package com.ants.antsbackground.mapper.people;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理管理员的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface AdministratorMapper {

    /**
     * 获取管理员账号对应的密码，进行登录判断
     * @param adminId
     * @return
     */
    String getAdministratorPassword(Integer adminId);

    /**
     * 获取管理员原来的密码
     * @param parameterMap
     * @return
     */
    String getOldPassword(Integer adminId);

    /**
     * 修改管理员密码
     * @param parameterMap
     * @return
     */
    int updateAdminPassword(Map parameterMap);



}
