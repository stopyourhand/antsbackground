package com.ants.antsbackground.thread.announcement;

import com.ants.antsbackground.service.announcement.AnnouncementService;
import com.ants.antsbackground.util.SpringUtil;

import java.util.Map;

/**
 * 更新公告
 * @Author czd
 * @Date:createed in 2019/10/18
 * @Version: V1.0
 */
public class UpdateAnnouncementThread implements Runnable{
    //用来保存数据库方法的传入的参数的值
    private Map parameter;
    //获取公告接口
    private AnnouncementService announcementService = SpringUtil.getBean(AnnouncementService.class);

    public UpdateAnnouncementThread(Map parameter){
        this.parameter = parameter;
    }

    @Override
    public void run() {
        //更新公告操作
        announcementService.updateAnnouncement(parameter);
    }
}
