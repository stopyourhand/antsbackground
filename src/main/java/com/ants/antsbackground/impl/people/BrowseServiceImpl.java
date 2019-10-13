package com.ants.antsbackground.impl.people;

import com.ants.antsbackground.dto.VisitorDTO;
import com.ants.antsbackground.entity.people.Visitor;
import com.ants.antsbackground.mapper.people.BrowseMapper;
import com.ants.antsbackground.mapper.people.VisitorMapper;
import com.ants.antsbackground.service.people.BrowseService;
import com.ants.antsbackground.service.people.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 处理流量访问的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("BrowseService")
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    private BrowseMapper browseMapper;

    /**
     * 获取指定时间内，不同用户类型(0:学生 1:教师 2:游客)的访问次数
     *
     * @param parameterMap
     * @return
     */
    public Integer countBrowseNumber(Map parameterMap){
        return browseMapper.countBrowseNumber(parameterMap);
    }
}
