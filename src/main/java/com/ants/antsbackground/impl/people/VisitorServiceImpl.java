package com.ants.antsbackground.impl.people;

import com.ants.antsbackground.dto.people.VisitorDTO;
import com.ants.antsbackground.entity.people.Visitor;
import com.ants.antsbackground.mapper.people.VisitorMapper;
import com.ants.antsbackground.service.people.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 处理游客的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("VisitorService")
public class VisitorServiceImpl implements VisitorService {
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 获取网站现在的游客总数量，总人数
     *
     * @return
     */
    public int countVisitors(Map<String, Integer> parameterMap) {
        return visitorMapper.countVisitors(parameterMap);
    }

    /**
     * 获取所有游客信息或者回收站的用户信息的列表（所有按钮）
     *
     * @param parameterMap
     * @return
     */
    public List<VisitorDTO> listVisitors(Map<String, Integer> parameterMap) {
        //返回从数据库中获取的游客的信息列表
        List<Visitor> visitorList = visitorMapper.listVisitors(parameterMap);

        //声明一个保存新的返回数据的格式的DTO列表
        List<VisitorDTO> visitorDTOLinkedList = new LinkedList<>();

        //获取公告列表的长度
        int length = visitorList.size();

        //声明反馈对象
        Visitor visitor = null;
        VisitorDTO visitorDTO = null;

        //遍历用户反馈信息列表，将列表中的数据对象进行DTO数据对象封装
        for (int index = 0; index < length; index++) {
            //获取单个反馈信息
            visitor = visitorList.get(index);

            //获取要返回给前端的数据格式的参数和数据
            int id = visitor.getId();
            String ip = visitor.getIp();

            visitorDTO = new VisitorDTO();
            //DTO赋值
            visitorDTO.setId(id);
            visitorDTO.setIp(ip);

            //将新的传输对象DTO添加到返回值列表里
            visitorDTOLinkedList.add(visitorDTO);
        }

        return visitorDTOLinkedList;
    }

    /**
     * 彻底删除游客信息
     *
     * @param visitorId
     * @return
     */
    public Integer deleteVisitor(Integer visitorId){
        return visitorMapper.deleteVisitor(visitorId);
    }

    /**
     * 将游客信息状态进行更改，即进入回收站
     *
     * @param parameterMap
     * @return
     */
    public Integer updateVisitor(Map<String, Integer> parameterMap){
        return visitorMapper.updateVisitor(parameterMap);
    }

}
