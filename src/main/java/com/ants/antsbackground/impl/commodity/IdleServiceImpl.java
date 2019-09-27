package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.mapper.commodity.IdleMapper;
import com.ants.antsbackground.mapper.sell.SellMapper;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.sell.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理闲置的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("IdleService")
public class IdleServiceImpl implements IdleService {
    @Autowired
    private IdleMapper idleMapper;
    /**
     * 获取在指定时间内(七天)发布闲置的数量
     * @return
     */
    public Integer countReleaseIdleNumber(Map<String,String> parameterMap){
        return idleMapper.countReleaseIdleNumber(parameterMap);
    }
}
