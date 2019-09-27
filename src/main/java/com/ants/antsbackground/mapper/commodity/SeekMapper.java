package com.ants.antsbackground.mapper.commodity;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 处理寻求的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface SeekMapper {
    /**
     * 获取在指定时间内(七天)发布寻求的数量
     * @return
     */
    Integer countReleaseGiveNumber(Map<String,String> parameterMap);



}
