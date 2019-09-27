package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.mapper.commodity.GiveMapper;
import com.ants.antsbackground.service.commodity.GiveService;
import com.ants.antsbackground.service.commodity.IdleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理赠送的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("GiveService")
public class GiveServiceImpl implements GiveService {
    @Autowired
    private GiveMapper giveMapper;
    /**
     * 获取在指定时间内(七天)发布赠送的数量
     * @return
     */
    public Integer countReleaseGiveNumber(Map<String,String> parameterMap){
        return giveMapper.countReleaseGiveNumber(parameterMap);
    }

}
