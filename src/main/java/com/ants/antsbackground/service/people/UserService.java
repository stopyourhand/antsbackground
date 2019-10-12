package com.ants.antsbackground.service.people;

import com.ants.antsbackground.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Service
public interface UserService {
    /**
     * 获取网站现在的用户总数量，总人数
     *
     * @return
     */
    Integer countUserNumber();

    /**
     * 获取在指定时间内注册的用户人数(七天内注册)
     *
     * @param parameterMap
     * @return
     */
    Integer countUserRegister(Map<String,String> parameterMap);

    /**
     * 获取所有用户信息或者回收站的用户信息的列表
     * @param parameterMap
     * @return
     */
    List<UserDTO> listUsers(Map<String,Integer> parameterMap);

    /**
     * 获取网站现在的用户（包括回收站）总数量，总人数
     * @param parameterMap
     * @return
     */
    Integer countUser(Map<String,Integer> parameterMap);

    /**
     * 彻底删除用户信息
     *
     * @param studentId
     * @return
     */
    Integer deleteUser(Integer studentId);

    /**
     * 将用户信息状态进行更改，即进入回收站 userType 0 正常 1 回收站
     *
     * @param parameterMap
     * @return
     */
    Integer updateUser(Map<String, Integer> parameterMap);

    /**
     * 获取指定时间内，不同用户类型(0:学生 1:教师)的访问次数(用户分析)
     * @param parameterMap
     * @return
     */
    Integer countUserNumberByType(Map parameterMap);
}
