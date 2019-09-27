package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.mapper.commodity.LeaseMapper;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理租赁的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("LeaseService")
public class LeaseServiceImpl implements LeaseService {
    @Autowired
    private LeaseMapper leaseMapper;

    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    public Integer countReleaseLeaseNumber(Map<String,String> parameterMap){
        return leaseMapper.countReleaseLeaseNumber(parameterMap);
    }


}
