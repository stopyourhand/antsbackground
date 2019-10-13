package com.ants.antsbackground.service.classify;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 处理商品所属的大分类的交易信息统计数量的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface ClassificationService {
    /**
     * 计算交易完成的商品中,商品所属的大分类的交易信息统计数量
     * @param parameterMap
     * @return
     */
    Integer countClassificationNumber(Map<String,Integer> parameterMap);



}
