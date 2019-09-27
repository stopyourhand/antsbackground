package com.ants.antsbackground.impl.people;

import com.ants.antsbackground.mapper.people.AdministratorMapper;
import com.ants.antsbackground.mapper.people.UserMapper;
import com.ants.antsbackground.service.people.AdministratorService;
import com.ants.antsbackground.service.people.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @return
     */
    public Integer countUserNumber(){
        return userMapper.countUserNumber();
    }


    /**
     * 获取在指定时间内注册的用户人数(七天内注册)
     * @param parameterMap
     * @return
     */
    public Integer countUserRegister(Map<String,String> parameterMap){
        return userMapper.countUserRegister(parameterMap);
    }

}
