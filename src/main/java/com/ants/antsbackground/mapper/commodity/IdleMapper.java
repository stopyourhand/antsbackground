package com.ants.antsbackground.mapper.commodity;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理闲置的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface IdleMapper {
    /**
     * 获取在指定时间内(七天)发布闲置的数量
     * @return
     */
    Integer countReleaseIdleNumber(Map<String,String> parameterMap);


}
