package com.ants.antsbackground.service.people;

import com.ants.antsbackground.dto.VisitorDTO;
import com.ants.antsbackground.entity.people.Visitor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author czd
 * @Date:createed in 2019/10/8
 * @Version: V1.0
 */
@Service
public interface VisitorService {
    /**
     * 获取网站现在的游客总数量，总人数
     *
     * @return
     */
    int countVisitors(Map<String,Integer> parameterMap);

    /**
     *获取所有游客信息或者回收站的用户信息的列表（所有按钮）
     * @param parameterMap
     * @return
     */
    List<VisitorDTO> listVisitors(Map<String,Integer> parameterMap);

    /**
     * 彻底删除游客信息
     *
     * @param visitorId
     * @return
     */
    Integer deleteVisitor(Integer visitorId);

    /**
     * 将游客信息状态进行更改，即进入回收站
     *
     * @param parameterMap
     * @return
     */
    Integer updateVisitor(Map<String, Integer> parameterMap);
}
