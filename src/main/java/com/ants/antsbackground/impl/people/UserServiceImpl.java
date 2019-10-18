package com.ants.antsbackground.impl.people;

import com.ants.antsbackground.dto.people.UserDTO;
import com.ants.antsbackground.mapper.people.UserMapper;
import com.ants.antsbackground.service.people.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理用户的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 获取网站现在的用户总数量，总人数
     *
     * @return
     */
    public Integer countUserNumber() {
        return userMapper.countUserNumber();
    }


    /**
     * 获取在指定时间内注册的用户人数(七天内注册)
     *
     * @param parameterMap
     * @return
     */
    public Integer countUserRegister(Map<String, String> parameterMap) {
        return userMapper.countUserRegister(parameterMap);
    }

    /**
     * 获取所有用户信息或者回收站的用户信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<UserDTO> listUsers(Map<String, Integer> parameterMap) {
        return userMapper.listUsers(parameterMap);
    }

    /**
     * 获取网站现在的用户（包括回收站）总数量，总人数
     * @param parameterMap
     * @return
     */
    public Integer countUser(Map<String,Integer> parameterMap){
        return userMapper.countUser(parameterMap);
    }

    /**
     * 彻底删除用户信息
     *
     * @param studentId
     * @return
     */
    public Integer deleteUser(Integer studentId){
        return userMapper.deleteUser(studentId);
    }

    /**
     * 将用户信息状态进行更改，即进入回收站 userType 0 正常 1 回收站
     *
     * @param parameterMap
     * @return
     */
    public Integer updateUser(Map<String, Integer> parameterMap){
        return userMapper.updateUser(parameterMap);
    }

    /**
     * 获取指定时间内，不同用户类型(0:学生 1:教师)的访问次数(用户分析)
     * @param parameterMap
     * @return
     */
    public Integer countUserNumberByType(Map parameterMap){
        return userMapper.countUserNumberByType(parameterMap);
    }

}
