package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.mapper.commodity.SeekMapper;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.SeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理寻求的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("SeekService")
public class SeekServiceImpl implements SeekService {
    @Autowired
    private SeekMapper seekMapper;
    /**
     * 获取在指定时间内(七天)发布寻求的数量
     * @return
     */
    public Integer countReleaseGiveNumber(Map<String,String> parameterMap){
        return seekMapper.countReleaseGiveNumber(parameterMap);
    }
}
