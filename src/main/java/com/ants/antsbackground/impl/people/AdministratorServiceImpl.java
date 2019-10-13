package com.ants.antsbackground.impl.people;

import com.ants.antsbackground.mapper.people.AdministratorMapper;
import com.ants.antsbackground.service.people.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理管理员的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("AdministratorService")
public class AdministratorServiceImpl implements AdministratorService {

    @Autowired
    private AdministratorMapper administratorMapper;

    /**
     * 获取管理员账号对应的密码，进行登录判断
     *
     * @param adminId
     * @return
     */
    public String getAdministratorPassword(Integer adminId) {
        return administratorMapper.getAdministratorPassword(adminId);
    }

    /**
     * 获取管理员原来的密码
     * @param parameterMap
     * @return
     */
    public String getOldPassword(Integer adminId){
        return administratorMapper.getOldPassword(adminId);
    }

    /**
     * 修改管理员密码
     * @param parameterMap
     * @return
     */
    public int updateAdminPassword(Map parameterMap){
        return administratorMapper.updateAdminPassword(parameterMap);
    }


}
