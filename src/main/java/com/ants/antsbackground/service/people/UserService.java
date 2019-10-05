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
}
