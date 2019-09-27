package com.ants.antsbackground.service.sell;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理交易完成的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface SellService {
    /**
     * 获取在指定时间内(七天)不同类型商品的交易数量
     * @param parameterMap
     * @return
     */
    Integer countSellNumberByCommodity(Map parameterMap);


}
