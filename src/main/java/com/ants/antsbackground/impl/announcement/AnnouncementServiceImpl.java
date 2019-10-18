package com.ants.antsbackground.impl.announcement;

import com.ants.antsbackground.dto.feedback.DecorationDTO;
import com.ants.antsbackground.entity.announcement.Announcement;
import com.ants.antsbackground.mapper.announcement.AnnouncementMapper;
import com.ants.antsbackground.service.announcement.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 处理用户反馈的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("AnnouncementService")
public class AnnouncementServiceImpl implements AnnouncementService {
    @Autowired
    private AnnouncementMapper announcementMapper;
    /**
     * 获取公告列表信息
     * @param parameterMap
     * @return
     */
    public List<DecorationDTO> listAnnouncement(Map<String,Integer> parameterMap){
        //返回从数据库中获取的公告的信息列表
        List<Announcement> announcementList = announcementMapper.listAnnouncement(parameterMap);
        //声明一个保存新的返回数据的格式的DTO列表
        List<DecorationDTO> decorationDTOList = new LinkedList<>();

        //获取公告列表的长度
        int length = announcementList.size();

        //声明反馈对象
        Announcement announcement = null;
        DecorationDTO decorationDTO = null;

        //遍历用户反馈信息列表，将列表中的数据对象进行DTO数据对象封装
        for (int index = 0; index < length; index++) {
            //获取单个反馈信息
            announcement = announcementList.get(index);

            //获取要返回给前端的数据格式的参数和数据
            int annId = announcement.getAnnId();
            String annTitle = announcement.getAnnTitle();
            String annContent = announcement.getAnnContent();
            String annReleaseTime = announcement.getAnnTime();

            decorationDTO = new DecorationDTO();
            //DTO赋值
            decorationDTO.setId(annId);
            decorationDTO.setTitle(annTitle);
            decorationDTO.setContent(annContent);
            decorationDTO.setTime(annReleaseTime);

            //将新的传输对象DTO添加到返回值列表里
            decorationDTOList.add(decorationDTO);
        }

        return decorationDTOList;
    }

    /**
     * 增加新公告
     * @param parameterMap
     * @return
     */
    public List<DecorationDTO> listAnnouncementRecycle(Map<String,Integer> parameterMap){
        //返回从数据库中获取的用户反馈的信息列表
        List<Announcement> announcementList = announcementMapper.listAnnouncementRecycle(parameterMap);
        //声明一个保存新的返回数据的格式的DTO列表
        List<DecorationDTO> decorationDTOList = new LinkedList<>();

        //获取用户反馈信息列表的长度
        int length = announcementList.size();

        //声明反馈对象
        Announcement announcement = null;
        DecorationDTO decorationDTO = null;

        //遍历用户反馈信息列表，将列表中的数据对象进行DTO数据对象封装
        for (int index = 0; index < length; index++) {
            //获取单个反馈信息
            announcement = announcementList.get(index);

            //获取要返回给前端的数据格式的参数和数据
            int annId = announcement.getAnnId();
            String annTitle = announcement.getAnnTitle();
            String annTime = announcement.getAnnTime();

            decorationDTO = new DecorationDTO();
            //DTO赋值
            decorationDTO.setId(annId);
            decorationDTO.setTitle(annTitle);
            decorationDTO.setTime(annTime);

            //将新的传输对象DTO添加到返回值列表里
            decorationDTOList.add(decorationDTO);

        }
        return decorationDTOList;
    }

    /**
     * 获取公告的数量
     * @param state
     * @return
     */
    public Integer countAnnouncementNumber(Integer state){
        return announcementMapper.countAnnouncementNumber(state);
    }

    /**
     * 增加一条新公告
     *
     * @param announcement
     * @return
     */
    public Integer insertAnnouncement(Announcement announcement){
        return announcementMapper.insertAnnouncement(announcement);
    }

    /**
     * 修改公告信息
     * @param parameterMap
     * @return
     */
    public Integer updateAnnouncement(Map parameterMap){
        return announcementMapper.updateAnnouncement(parameterMap);
    }

    /**
     * 将反馈信息状态进行更改，即进入回收站
     * @param map
     * @return
     */
    public Integer updateAnnouncementState(Map<String,Integer> map){
        return announcementMapper.updateAnnouncementState(map);
    }

    /**
     * 彻底公告反馈信息
     * @param annId
     * @return
     */
    public Integer deleteAnnouncement(Integer annId){
        return announcementMapper.deleteAnnouncement(annId);
    }


}
