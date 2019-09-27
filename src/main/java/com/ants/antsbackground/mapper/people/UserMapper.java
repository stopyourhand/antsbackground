package com.ants.antsbackground.mapper.people;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理用户的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface UserMapper {

    /**
     * 获取网站现在的用户总数量，总人数
     * @return
     */
    Integer countUserNumber();

    /**
     * 获取在指定时间内注册的用户人数(七天内注册)
     * @param parameterMap
     * @return
     */
    Integer countUserRegister(Map<String,String> parameterMap);

}
