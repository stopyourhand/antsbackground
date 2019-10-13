package com.ants.antsbackground.mapper.people;

import com.ants.antsbackground.entity.people.Visitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理流量访问的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface BrowseMapper {

    /**
     * 获取指定时间内，不同用户类型(0:学生 1:教师 2:游客)的访问次数
     *
     * @param parameterMap
     * @return
     */
    Integer countBrowseNumber(Map parameterMap);

}
