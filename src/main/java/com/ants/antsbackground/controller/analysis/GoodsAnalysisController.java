package com.ants.antsbackground.controller.analysis;

import com.ants.antsbackground.service.commodity.GiveService;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.LeaseService;
import com.ants.antsbackground.service.commodity.SeekService;
import com.ants.antsbackground.service.sell.SellService;
import com.ants.antsbackground.util.CountDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/analysis")
public class GoodsAnalysisController {
    public static void main(String[] args) {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = simpleDateFormat.format(nowDate);

        StringBuilder stringBuilder = new StringBuilder();
        String[] timeArray = endTime.split(" ");
        String start = timeArray[0];
        stringBuilder.append(start);
        stringBuilder.append(" 00:00:00");
        System.out.println(stringBuilder.toString());

//        //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
//        int before = -1;
//        String startTime = CountDateUtil.getBeforeDay(before);
//        System.out.println(startTime);
    }

    @Autowired
    private SellService sellService;
    @Autowired
    private IdleService idleService;
    @Autowired
    private SeekService seekService;
    @Autowired
    private LeaseService leaseService;
    @Autowired
    private GiveService giveService;

    /**
     * time -> 0:今天 1:昨天 2:最近7天 3:最近30天 4:搜索时间
     * @param time
     * @return
     */
    @GetMapping(value = "/sellAnalysis")
    public Map sellAnalysis(@RequestParam(value = "time") String time){
        Map resultMap = new HashMap(16);

        /**
         * 下面是完成交易的统计
         */
        String endTime = "";
        String startTime = "";
        int before = 0;
        Date nowDate = null;
        SimpleDateFormat simpleDateFormat = null;
        switch (time){
            //获取今天的交易统计量
            case "0":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                StringBuilder stringBuilder = new StringBuilder();
                //分割年-月-日 时:分:秒
                String[] timeArray = endTime.split(" ");
                //截取年-月-日
                String start = timeArray[0];
                //获取当天的从00:00:00到23:59:59的时间段
                stringBuilder.append(start);
                stringBuilder.append(" 00:00:00");
                //获取凌晨时间
                startTime = stringBuilder.toString();
            break;
            //获取昨天的交易统计量
            case "1":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -1;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
            //获取七天的交易统计量
            case "2":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -7;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
            //获取三十天的交易统计量
            case "3":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -30;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
            //获取三十天的交易统计量
            default:
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -30;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
        }


        //设置保存开始时间和结束时间的map
        Map parameterMap = new HashMap<>(16);
        parameterMap.put("endTime", endTime);
        parameterMap.put("startTime", startTime);


        //获取最近七天闲置的完成交易数量,0代表闲置
        parameterMap.put("goodsState", 0);
        int sellIdleNumber = sellService.countSellNumberByCommodity(parameterMap);
        //判断数据是否符合要求
        if (sellIdleNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("sellIdleNumber", sellIdleNumber);

        //获取最近七天赠送的完成交易数量,1代表赠送
        parameterMap.put("goodsState", 1);
        int sellGiveNumber = sellService.countSellNumberByCommodity(parameterMap);
        //判断数据是否符合要求
        if (sellGiveNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("sellGiveNumber", sellGiveNumber);

        //获取最近七天租赁的完成交易数量,2代表租赁
        parameterMap.put("goodsState", 2);
        int sellLeaseNumber = sellService.countSellNumberByCommodity(parameterMap);
        //判断数据是否符合要求
        if (sellLeaseNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("sellLeaseNumber", sellLeaseNumber);

        //获取最近七天寻求的完成交易数量,3代表寻求
        parameterMap.put("goodsState", 3);
        int sellSeekNumber = sellService.countSellNumberByCommodity(parameterMap);
        //判断数据是否符合要求
        if (sellSeekNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("sellSeekNumber", sellSeekNumber);


        return resultMap;
    }

    /**
     * time -> 0:今天 1:昨天 2:最近7天 3:最近30天 4:搜索时间
     * @param time
     * @return
     */
    @GetMapping(value = "/releaseAnalysis")
    public Map releaseAnalysis(@RequestParam(value = "time") String time){
        Map resultMap = new HashMap(16);

        /**
         * 下面是完成交易的统计
         */
        String endTime = "";
        String startTime = "";
        int before = 0;
        Date nowDate = null;
        SimpleDateFormat simpleDateFormat = null;
        switch (time){
            //获取今天的交易统计量
            case "0":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                StringBuilder stringBuilder = new StringBuilder();
                //分割年-月-日 时:分:秒
                String[] timeArray = endTime.split(" ");
                //截取年-月-日
                String start = timeArray[0];
                //获取当天的从00:00:00到23:59:59的时间段
                stringBuilder.append(start);
                stringBuilder.append(" 00:00:00");
                //获取凌晨时间
                startTime = stringBuilder.toString();
                break;
            //获取昨天的交易统计量
            case "1":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -1;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
            //获取七天的交易统计量
            case "2":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -7;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
            //获取三十天的交易统计量
            case "3":
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -30;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
            //获取三十天的交易统计量
            default:
                //获取当前时间
                nowDate = new Date();
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                endTime = simpleDateFormat.format(nowDate);

                //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
                before = -30;
                startTime = CountDateUtil.getBeforeDay(before);
                break;
        }

        //设置保存开始时间和结束时间的map
        Map parameterMap = new HashMap<>(16);
        parameterMap.put("endTime", endTime);
        parameterMap.put("startTime", startTime);


        /**
         * 下面是关于发布商品的统计
         */

        //计算最近七天的发布闲置的统计数量
        int releaseIdleNumber = idleService.countReleaseIdleNumber(parameterMap);
        //判断数据是否符合要求
        if (releaseIdleNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("releaseIdleNumber", releaseIdleNumber);

        //计算最近七天的寻求数量
        int releaseSeekNumber = seekService.countReleaseSeekNumber(parameterMap);
        if (releaseSeekNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("releaseSeekNumber", releaseSeekNumber);

        //计算最近七天的租赁数量
        int releaseLeaseNumber = leaseService.countReleaseLeaseNumber(parameterMap);
        if (releaseLeaseNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("releaseLeaseNumber", releaseLeaseNumber);

        int releaseGiveNumber = giveService.countReleaseGiveNumber(parameterMap);
        //计算最近七天的赠送数量
        if (releaseGiveNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("releaseGiveNumber", releaseGiveNumber);


        return resultMap;
    }
}
