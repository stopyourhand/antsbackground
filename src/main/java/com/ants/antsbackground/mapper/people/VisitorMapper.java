package com.ants.antsbackground.mapper.people;

import com.ants.antsbackground.entity.people.Visitor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理游客的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface VisitorMapper {

    /**
     * 获取网站现在的游客总数量，总人数
     *
     * @return
     */
    int countVisitors(Map<String,Integer> parameterMap);

    /**
     * 获取所有游客信息或者回收站的用户信息的列表（所有按钮）
     * @param parameterMap
     * @return
     */
    List<Visitor> listVisitors(Map<String,Integer> parameterMap);

}
