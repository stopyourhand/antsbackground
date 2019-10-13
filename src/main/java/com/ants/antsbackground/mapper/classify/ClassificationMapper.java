package com.ants.antsbackground.mapper.classify;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理商品所属的大分类的交易信息统计数量的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface ClassificationMapper {
    /**
     * 计算交易完成的商品中,商品所属的大分类的交易信息统计数量
     * @param parameterMap
     * @return
     */
    Integer countClassificationNumber(Map<String,Integer> parameterMap);

}
