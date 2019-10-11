package com.ants.antsbackground.controller.analysis;

import com.ants.antsbackground.dto.BrowseDTO;
import com.ants.antsbackground.service.people.BrowseService;
import com.ants.antsbackground.util.CountDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 用户数据分析控制器
 *
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/analysis")
public class PeopleAnalysisController {


    @Autowired
    private BrowseService browseService;

    /**
     * 流量分析功能
     * time -> 0:今天 1:最近7天 2:最近30天 3:搜索时间
     *
     * @param time
     * @return
     */
    @GetMapping(value = "/trafficAnalysis")
    public Map trafficAnalysis(@RequestParam(value = "time") String time) {
        //用来保存返回给前端数据的hashMap
        Map resultMap = new LinkedHashMap(16);
        //判断前端参数的值是否符合要求
        if (time == null || "".equals(time)) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }
        //设置保存开始时间和结束时间的map
        Map parameterMap = new HashMap<>(16);

        /**
         * 下面是流量分析的统计
         */
        String endTime;
        String startTime;
        Date nowDate;
        SimpleDateFormat simpleDateFormat;
        List<String> timeList = new LinkedList<>();
        //声明参数参数的对象
        BrowseDTO browseDTO = null;
        switch (time) {
            //获取今天的交易统计量
            case "0":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                endTime = simpleDateFormat.format(nowDate);

                //拼接字符串，对时间进行处理
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(endTime);
                stringBuilder.append(" HH:00:00");
                for (int i = 0; i < 24; i++) {
                    String Time ;
                    if (i < 10 ) {
                        stringBuilder.replace(11, 13, "0" + i);
                        String start = stringBuilder.toString();
                        Time = start;
                        if (i + 1 == 10){
                            stringBuilder.replace(11, 13, "" + (i + 1));
                        }else {
                            stringBuilder.replace(11, 13, "0" + (i + 1));

                        }

                        String end = stringBuilder.toString();

                        //设置用户查看数据的开始时间和结束时间
                        parameterMap.put("endTime", end);
                        parameterMap.put("startTime", start);
                    } else {
                        stringBuilder.replace(11, 13, String.valueOf(i));
                        String start = stringBuilder.toString();
                        Time = start;
                        stringBuilder.replace(11, 13, String.valueOf(i + 1));
                        String end = stringBuilder.toString();

                        //设置用户查看数据的开始时间和结束时间
                        parameterMap.put("endTime", end);
                        parameterMap.put("startTime", start);
                    }

                    browseDTO = new BrowseDTO();

                    //获取学生最近指定天数的登录访问次数
                    parameterMap.put("peopleType", 0);
                    int studentNumber = browseService.countBrowseNumber(parameterMap);
                    browseDTO.setStudentNumber(studentNumber);

                    //获取游客最近指定天数的登录访问次数
                    parameterMap.put("peopleType", 2);
                    int visitorNumber = browseService.countBrowseNumber(parameterMap);
                    browseDTO.setVisitorNumber(visitorNumber);
                    resultMap.put(Time, browseDTO);

                    //获取教师最近指定天数的登录访问次数
                    parameterMap.put("peopleType", 1);
                    int teacherNumber = browseService.countBrowseNumber(parameterMap);
                    browseDTO.setTeacherNumber(teacherNumber);

                    resultMap.put(Time, browseDTO);

                }
                return resultMap;
            //获取七天的交易统计量
            case "1":
                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                for (int before = -7; before < 0; before++) {
                    String starttime = CountDateUtil.getBeforeDay(before);
                    String[] array = starttime.split(" ");
                    starttime = array[0];
                    timeList.add(starttime);
                }
                break;
            //获取三十天的交易统计量
            case "2":
                //获取距离当前时间的最近三十天的时间,即获取七天前的时间戳
                for (int before = -30; before < 0; before++) {
                    String starttime = CountDateUtil.getBeforeDay(before);
                    String[] array = starttime.split(" ");
                    starttime = array[0];
                    timeList.add(starttime);
                }
                break;
            //获取三十天的交易统计量
            default:
                //测试中
                break;
        }


        //过去多少天的程度
        int length = timeList.size();
        //拼接开始时间和结束时间字符串
        StringBuilder startBuilder = null;
        StringBuilder endBuilder = null;

        for (int i = 0; i < length; i++) {
            String Time = timeList.get(i);
            browseDTO = new BrowseDTO();
            //设置一天的开始时间，涉及到秒
            startBuilder = new StringBuilder();
            startBuilder.append(Time);
            //设置当天的凌晨时间
            startBuilder.append(" 00:00:00");
            //获取一天的开始时间
            startTime = startBuilder.toString();

            //设置一天的结束时间，涉及到秒
            endBuilder = new StringBuilder();
            endBuilder.append(Time);
            //设置当天的凌晨时间
            endBuilder.append(" 23:59:59");
            //获取一天的开始时间
            endTime = endBuilder.toString();

            //设置用户查看数据的开始时间和结束时间
            parameterMap.put("endTime", endTime);
            parameterMap.put("startTime", startTime);

            //获取学生最近指定天数的登录访问次数
            parameterMap.put("peopleType", 0);
            int studentNumber = browseService.countBrowseNumber(parameterMap);
            browseDTO.setStudentNumber(studentNumber);

            //获取教师最近指定天数的登录访问次数
            parameterMap.put("peopleType", 1);
            int teacherNumber = browseService.countBrowseNumber(parameterMap);
            browseDTO.setTeacherNumber(teacherNumber);

            //获取游客最近指定天数的登录访问次数
            parameterMap.put("peopleType", 2);
            int visitorNumber = browseService.countBrowseNumber(parameterMap);
            browseDTO.setVisitorNumber(visitorNumber);
            resultMap.put(Time, browseDTO);
        }

        return resultMap;
    }

}
