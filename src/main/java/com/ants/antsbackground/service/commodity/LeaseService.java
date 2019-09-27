package com.ants.antsbackground.service.commodity;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理租赁的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface LeaseService {
    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    Integer countReleaseLeaseNumber(Map<String,String> parameterMap);



}
