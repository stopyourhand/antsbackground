package com.ants.antsbackground.service.announcement;

import com.ants.antsbackground.dto.AnnouncementDTO;
import com.ants.antsbackground.dto.DecorationDTO;
import com.ants.antsbackground.entity.announcement.Announcement;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理公告的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface AnnouncementService {
    /**
     * 获取公告列表信息
     *
     * @param parameterMap
     * @return
     */
    List<DecorationDTO> listAnnouncement(Map<String, Integer> parameterMap);


    /**
     * 获取公告的数量
     * @param state
     * @return
     */
    Integer countAnnouncementNumber(Integer state);

    /**
     * 增加一条新公告
     *
     * @param announcement
     * @return
     */
    Integer insertAnnouncement(Announcement announcement);

    /**
     * 修改公告信息
     *
     * @param parameterMap
     * @return
     */
    Integer updateAnnouncement(Map parameterMap);

    /**
     * 将反馈信息状态进行更改，即进入回收站
     *
     * @param map
     * @return
     */
    Integer updateAnnouncementState(Map<String, Integer> map);

    /**
     * 彻底公告反馈信息
     *
     * @param annId
     * @return
     */
    Integer deleteAnnouncement(Integer annId);

    /**
     * 增加新公告
     *
     * @param parameter
     * @return
     */
    List<DecorationDTO> listAnnouncementRecycle(Map<String, Integer> parameter);

}
