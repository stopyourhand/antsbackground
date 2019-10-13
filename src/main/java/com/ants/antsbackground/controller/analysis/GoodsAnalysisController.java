package com.ants.antsbackground.controller.analysis;

import com.ants.antsbackground.constant.ClassifyContst;
import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.AnalysisDTO;
import com.ants.antsbackground.entity.commodity.GiveGoods;
import com.ants.antsbackground.entity.commodity.IdleGoods;
import com.ants.antsbackground.entity.commodity.LeaseGoods;
import com.ants.antsbackground.entity.commodity.SeekGoods;
import com.ants.antsbackground.entity.sell.Sell;
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
import java.util.*;

/**
 * 商品数据分析控制器
 *
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/analysis")
public class GoodsAnalysisController {
    //length代表所有子分类的个数
    private int Lengths = 55;
    //书籍大分类统计个数
    private int booksNumber = 0;
    //文具大分类统计个数
    private int stationeryNumber = 0;
    //日常大分类统计个数
    private int dailyNumber = 0;
    //美妆大分类统计个数
    private int cosmeticsNumber = 0;
    //食物大分类统计个数
    private int foodNumber = 0;
    //电器大分类统计个数
    private int electricalNumber = 0;
    //其他大分类统计个数
    private int othersNumber = 0;

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
     * 访问统计模块中发布分析功能
     * time -> 0:今天 1:昨天 2:最近7天 3:最近30天 4:搜索时间
     * @param currentPage
     * @param time
     * @return
     */
    @GetMapping(value = "/releaseAnalysis")
    public Map releaseAnalysis(@RequestParam(value = "currentPage") int currentPage,
                               @RequestParam(value = "time") String time) {
        //保存返回给前端数据的hashMap
        Map resultMap = new LinkedHashMap(16);

        /**
         * 下面是完成交易的统计
         */
        String endTime = "";
        String startTime = "";
        int before = 0;
        Date nowDate = null;
        SimpleDateFormat simpleDateFormat = null;
        switch (time) {
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

        //获取页面数开始的数据信息在数据库的坐标信息
        int head = (currentPage - 1) * PageConsts.FEEDBACK_PAGE_NUMBER;
        //传入初始页面的列表返回值
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.ANALYSIS_MANAGEMENT_PAGE_NUMBER);

        //设置用户查看的开始时间和结束时间
        parameterMap.put("endTime", endTime);
        parameterMap.put("startTime", startTime);


        /**
         * 获取闲置，寻求，租赁，赠送四种不同类型的商品的所属的大分类为书籍的数量
         */
        parameterMap.put("start", ClassifyContst.BOOKS_HEAD);
        parameterMap.put("end",ClassifyContst.BOOKS_TAIL);


        for (int i = 1; i < Lengths;){
            if (i != 1){
                parameterMap.put("start", i);
                parameterMap.put("end",i + 7);
            }else {
                parameterMap.put("start", i);
                parameterMap.put("end",i + 6);
            }

            //从数据库中获取对应商品类型的各分类的统计数量
            int idleClassifyNumber = idleService.countReleaseClassifyIdleNumber(parameterMap);
            int leaseClassifyNumber = leaseService.countReleaseClassifyLeaseNumber(parameterMap);
            int seekClassifyNumber = seekService.countReleaseClassifySeekNumber(parameterMap);
            int giveClassifyNumber = giveService.countReleaseClassifyGiveNumber(parameterMap);
            //判断从数据库中获取的数据是否符合要求
            if (idleClassifyNumber < 0 || leaseClassifyNumber < 0 || seekClassifyNumber < 0 || giveClassifyNumber < 0){
                resultMap.put("msg", "网站获取数据错误，请重新请求!");
                return resultMap;
            }

            //根据i(小分类的代号)的不同表示对应区间的大分类
            switch (i){
                case 1:
                    //获取书籍的发布的统计数量
                    booksNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("booksNumber",booksNumber);
                break;
                case 8:
                    //获取文具的发布的统计数量
                    stationeryNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("stationeryNumber",stationeryNumber);
                    break;
                case 16:
                    //获取日常的发布的统计数量
                    dailyNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("dailyNumber",dailyNumber);
                    break;
                case 24:
                    //获取美妆的发布的统计数量
                    cosmeticsNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("cosmeticsNumber",cosmeticsNumber);
                    break;
                case 32:
                    //获取食物的发布的统计数量
                    foodNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("foodNumber",foodNumber);
                    break;
                case 40:
                    //获取电器的发布的统计数量
                    electricalNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("electricalNumber",electricalNumber);
                    break;
                default:
                    //获取其他的发布的统计数量
                    othersNumber = idleClassifyNumber + leaseClassifyNumber + seekClassifyNumber + giveClassifyNumber;
                    resultMap.put("othersNumber",othersNumber);
            }
            //对小分类对应的大分类跳转
            if (i != 1){
                i = i + 8;
            }else {
                i = i + 7;
            }
        }

        //计算特定时间内所有交易完成的数据量
        int allNumber = booksNumber + stationeryNumber + dailyNumber + cosmeticsNumber + foodNumber + electricalNumber +othersNumber;
        if (allNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        int allPage = (allNumber / PageConsts.ANALYSIS_MANAGEMENT_PAGE_NUMBER) + 1;
        if (allPage < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("allPage",allPage);

        //将返回给前端数据的参数进行转换
        List<AnalysisDTO> analysisDTOList = new LinkedList<>();
        //从数据库中获取交易分析的列出不同时间段的“发布闲置”的商品的信息
        List<IdleGoods> idleGoodsList = idleService.listIdleAnalysis(parameterMap);
        //从数据库中获取交易分析的列出不同时间段的“发布租赁”的商品的信息
        List<LeaseGoods> leaseGoodsList = leaseService.listLeaseAnalysis(parameterMap);
        //从数据库中获取交易分析的列出不同时间段的“发布寻求”的商品的信息
        List<SeekGoods> seekGoodsList = seekService.listSeekAnalysis(parameterMap);
        //从数据库中获取交易分析的列出不同时间段的“发布赠送”的商品的信息
        List<GiveGoods> giveGoodsList = giveService.listGiveAnalysis(parameterMap);

        int[] lengthArray = {idleGoodsList.size(), leaseGoodsList.size(), seekGoodsList.size(), giveGoodsList.size()};

        //保存要传给前端的参数的数据
        AnalysisDTO analysisDTO = null;
        //暂时保存从数据库中获取的信息的每个对象的数据
        IdleGoods idleGoods = null;
        LeaseGoods leaseGoods = null;
        SeekGoods seekGoods = null;
        GiveGoods giveGoods = null;
        int Length = lengthArray.length;
        //type表示对应的商品类型 0:闲置  1:赠送  2:租赁 3:寻求
        for (int type = 0; type < Length; type++) {
            int length = lengthArray[type];
            for (int i = 0; i < length; i++) {
                int goodsClass = 0;
                String goodsName = null;
                int goodsState = 0;
                double price = 0.0;
                switch (type) {
                    //闲置
                    case 0:
                        idleGoods = idleGoodsList.get(i);
                        goodsClass = idleGoods.getGoodsClass();
                        goodsName = idleGoods.getGoodsName();
                        //0:闲置  1:赠送  2:租赁 3:寻求
                        goodsState = 0;
                        price = idleGoods.getGoodsPrice();
                        System.out.println("闲置");
                        break;
                    //租赁
                    case 1:
                        leaseGoods = leaseGoodsList.get(i);
                        goodsClass = leaseGoods.getGoodsClass();
                        goodsName = leaseGoods.getGoodsName();
                        //0:闲置  1:赠送  2:租赁 3:寻求
                        goodsState = 1;
                        price = leaseGoods.getGoodsPrice();
                        break;
                    //寻求
                    case 2:
                        seekGoods = seekGoodsList.get(i);
                        goodsClass = seekGoods.getGoodsClass();
                        goodsName = seekGoods.getGoodsName();
                        //0:闲置  1:赠送  2:租赁 3:寻求
                        goodsState = 2;
                        price = seekGoods.getGoodsPrice();
                        break;
                    //赠送
                    case 3:
                        giveGoods = giveGoodsList.get(i);
                        goodsClass = giveGoods.getGoodsClass();
                        goodsName = giveGoods.getGoodsName();
                        //0:闲置  1:赠送  2:租赁 3:寻求
                        goodsState = 3;
                        price = giveGoods.getGoodsPrice();
                        break;
                    default: {
                    }
                }

                analysisDTO = new AnalysisDTO();
                //根据对象的小分类获取所属大分类名称
                switch (goodsClass) {
                    case 1:case 2:case 3:case 4:case 5:case 6:
                    case 7:
                        analysisDTO.setClassify("书籍");
                        break;
                    case 8:case 9:case 10:case 11:case 12:case 13:case 14:
                    case 15:
                        analysisDTO.setClassify("文具");
                        break;
                    case 16:case 17:case 18:case 19:case 20:case 21:case 22:
                    case 23:
                        analysisDTO.setClassify("日用");
                        break;
                    case 24:case 25:case 26:case 27:case 28:case 29:case 30:
                    case 31:
                        analysisDTO.setClassify("美妆");
                        break;
                    case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                    case 39:
                        analysisDTO.setClassify("食品");
                        break;
                    case 40:case 41:case 42:case 43:case 44:case 45:case 46:
                    case 47:
                        analysisDTO.setClassify("电器");
                        break;
                    default:
                        analysisDTO.setClassify("其他");
                        break;
                }
                analysisDTO.setName(goodsName);
                analysisDTO.setGoodsState(goodsState);
                analysisDTO.setPrice(price);

                analysisDTOList.add(analysisDTO);
            }
        }

        resultMap.put("analysisList", analysisDTOList);

        return resultMap;
    }


    /**
     * 访问统计模块中交易分析功能
     *
     * time -> 0:今天 1:昨天 2:最近7天 3:最近30天 4:搜索时间
     * @param currentPage
     * @param time
     * @return
     */
    @GetMapping(value = "/sellAnalysis")
    public Map sellAnalysis(@RequestParam(value = "currentPage") int currentPage,
                            @RequestParam(value = "time") String time) {
        //用来保存返回给前端数据的hashMap
        Map resultMap = new LinkedHashMap(16);
        //判断前端参数的值是否符合要求
        if (currentPage <= 0) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }
        if (time == null || "".equals(time)) {
            resultMap.put("msg", "页面数传输有误!");
            return resultMap;
        }

        /**
         * 下面是完成交易的统计
         */
        String endTime = "";
        String startTime = "";
        int before = 0;
        Date nowDate = null;
        SimpleDateFormat simpleDateFormat = null;
        switch (time) {
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
        //设置用户查看数据的开始时间和结束时间
        parameterMap.put("endTime", endTime);
        parameterMap.put("startTime", startTime);

        //获取页面数开始的数据信息在数据库的坐标信息
        int head = (currentPage - 1) * PageConsts.FEEDBACK_PAGE_NUMBER;
        //传入初始页面的列表返回值
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.ANALYSIS_MANAGEMENT_PAGE_NUMBER);

        //根据i(小分类的代号)的不同表示对应区间的大分类
        for (int i = 1; i < Lengths;){
            if (i == 1){
                parameterMap.put("start", i);
                parameterMap.put("end",i + 6);
            }else {
                parameterMap.put("start", i);
                parameterMap.put("end",i + 7);
            }



            //根据i(小分类的代号)的不同表示对应区间的大分类
            switch (i){
                case 1:
                    //获取书籍的发布的统计数量
                    booksNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("booksNumber",booksNumber);
                    break;
                case 8:
                    //获取文具的发布的统计数量
                    stationeryNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("stationeryNumber",stationeryNumber);
                    break;
                case 16:
                    //获取日常的发布的统计数量
                    dailyNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("dailyNumber",dailyNumber);
                    break;
                case 24:
                    //获取美妆的发布的统计数量
                    cosmeticsNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("cosmeticsNumber",cosmeticsNumber);
                    break;
                case 32:
                    //获取食物的发布的统计数量
                    foodNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("foodNumber",foodNumber);
                    break;
                case 40:
                    //获取电器的发布的统计数量
                    electricalNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("electricalNumber",electricalNumber);
                    break;
                default:
                    //获取其他的发布的统计数量
                    othersNumber = sellService.countReleaseClassifySellNumber(parameterMap);
                    resultMap.put("othersNumber",othersNumber);

            }

            //对小分类对应的大分类跳转
            if (i != 1){
                i = i + 8;
            }else {
                i = i + 7;
            }
        }

        //计算特定时间内所有交易完成的数据量
        int allNumber = booksNumber + stationeryNumber + dailyNumber + cosmeticsNumber + foodNumber + electricalNumber +othersNumber;
        if (allNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        int allPage = (allNumber / PageConsts.ANALYSIS_MANAGEMENT_PAGE_NUMBER) + 1;
        if (allPage < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("allPage",allPage);

        //将返回给前端数据的参数进行转换
        List<AnalysisDTO> analysisDTOList = new LinkedList<>();
        //从数据库中获取交易分析的列出不同时间段的交易完成的商品的信息
        List<Sell> sellList = sellService.listSellAnalysis(parameterMap);
        int length = sellList.size();
        //保存要传给前端的参数的数据
        AnalysisDTO analysisDTO = null;
        //暂时保存从数据库中获取的信息的每个对象的数据
        Sell sell = null;
        for (int i = 0; i < length; i++) {
            sell = sellList.get(i);

            int goodsClass = sell.getGoodsClass();
            String goodsName = sell.getGoodsName();
            int goodsState = sell.getGoodsState();
            double price = sell.getGoodsPrice();

            analysisDTO = new AnalysisDTO();
            //根据对象的小分类获取所属大分类名称
            switch (goodsClass) {
                case 1:case 2:case 3:case 4:case 5:case 6:
                case 7:
                    analysisDTO.setClassify("书籍");
                    break;
                case 8:case 9:case 10:case 11:case 12:case 13:case 14:
                case 15:
                    analysisDTO.setClassify("文具");
                    break;
                case 16:case 17:case 18:case 19:case 20:case 21:case 22:
                case 23:
                    analysisDTO.setClassify("日用");
                    break;
                case 24:case 25:case 26:case 27:case 28:case 29:case 30:
                case 31:
                    analysisDTO.setClassify("美妆");
                    break;
                case 32:case 33:case 34:case 35:case 36:case 37:case 38:
                case 39:
                    analysisDTO.setClassify("食品");
                    break;
                case 40:case 41:case 42:case 43:case 44:case 45:case 46:
                case 47:
                    analysisDTO.setClassify("电器");
                    break;
                default:
                    analysisDTO.setClassify("其他");
                    break;
            }
            analysisDTO.setName(goodsName);
            analysisDTO.setGoodsState(goodsState);
            analysisDTO.setPrice(price);

            analysisDTOList.add(analysisDTO);
        }

        resultMap.put("analysisList", analysisDTOList);
        return resultMap;
    }



}
