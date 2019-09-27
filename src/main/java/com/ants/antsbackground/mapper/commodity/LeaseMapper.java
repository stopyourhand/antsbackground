package com.ants.antsbackground.mapper.commodity;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理租赁的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface LeaseMapper {
    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    Integer countReleaseLeaseNumber(Map<String,String> parameterMap);



}
