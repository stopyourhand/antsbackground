package com.ants.antsbackground.controller.statistic;

import com.ants.antsbackground.constant.ClassifyContst;
import com.ants.antsbackground.constant.PersonalContst;
import com.ants.antsbackground.dto.PersonalDataDTO;
import com.ants.antsbackground.service.classify.ClassificationService;
import com.ants.antsbackground.service.commodity.GiveService;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.LeaseService;
import com.ants.antsbackground.service.commodity.SeekService;
import com.ants.antsbackground.service.people.UserService;
import com.ants.antsbackground.service.sell.SellService;
import com.ants.antsbackground.util.CountDateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 这个控制器是关于一些和统计相关的业务处理，例如首页的发布物品的统计
 *
 * @Author czd
 * @Date:createed in 2019/9/27
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "background/homePage")
public class HomePageController {

    @Autowired
    private UserService userService;

    @Autowired
    private SellService sellService;

    @Autowired
    private IdleService idleService;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private GiveService giveService;

    @Autowired
    private SeekService seekService;

    @Autowired
    private ClassificationService classificationService;


    /**
     * 获取后台管理系统首页信息,包括 ->
     * 1.用户最近七天注册人数,网站总人数
     * 2.发布统计: 发布总量,发布闲置数量,发布租赁数量,发布赠送数量,发布寻求数量
     * 3.交易完成统计: 交易总量,完成闲置数量,完成租赁数量,完成赠送数量,完成寻求数量
     * 4.管理员信息显示: 当前版本,用户名称,上次登录时间,上次登录IP,本次登录时间,本次登录IP
     *
     * @return
     * @throws UnknownHostException
     */
    @GetMapping(value = "/getHomePageData")
    public Map getHomePageData() throws UnknownHostException {
        //设置保存返回给前端数据的resultMap
        Map resultMap = new HashMap(16);

        //获取当前时间
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endTime = simpleDateFormat.format(nowDate);

        //获取距离当前时间的最近七天的时间,即获取七天前的时间戳
        int before = -7;
        String startTime = CountDateUtil.getBeforeDay(before);

        //设置保存开始时间和结束时间的map
        Map parameterMap = new HashMap<>(16);
        parameterMap.put("endTime", endTime);
        parameterMap.put("startTime", startTime);

        /**
         * 下面是关于用户统计量的业务操作
         */
        //获取网站的总人数
        int allUsers = userService.countUserNumber();
        //判断数据是否符合要求
        if (allUsers < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("allUsers", allUsers);

        //获取最近七天注册人数
        int userRegisterNumber = userService.countUserRegister(parameterMap);
        //判断数据是否符合要求
        if (userRegisterNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("userRegisterNumber", userRegisterNumber);

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

        int releaseGiveNumber = seekService.countReleaseSeekNumber(parameterMap);
        //计算最近七天的赠送数量
        if (releaseGiveNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("releaseGiveNumber", releaseGiveNumber);

        //计算最近七天的发布商品的总数量
        int releaseAmount = releaseGiveNumber + releaseIdleNumber + releaseLeaseNumber + releaseSeekNumber;
        resultMap.put("releaseAmount", releaseAmount);

        /**
         * 下面是完成交易的统计
         */

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

        //统计最近七天发布总量
        int sellAmount = sellGiveNumber + sellIdleNumber + sellLeaseNumber + sellSeekNumber;
        //判断数据是否符合要求
        if (sellAmount < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("sellAmount", sellAmount);

        /**
         * 获取管理员资料的显示信息
         *
         * 需要修改
         */
        PersonalDataDTO personalDataDTO = new PersonalDataDTO();
        //获取网站当前版本
        personalDataDTO.setCurrentVersion(PersonalContst.CURRENT_VRSION);
        //获取管理员名称
        personalDataDTO.setAdminName("admin");
        //获取管理员上次登录时间
        personalDataDTO.setLastLoginTime("2019-09-04 00:55:34");
        //获取上一次登录IP地址
        personalDataDTO.setLastLoginIp("127.0.0.1");

        //获取管理员本次登录时间
        personalDataDTO.setLoginTime(startTime);

        //获取本地主机
        InetAddress inetAddress = InetAddress.getLocalHost();
        //获取IP地址
        String hostAddress = inetAddress.getHostAddress();
        //获取本次登录IP
        personalDataDTO.setLoginIp(hostAddress);

        resultMap.put("personalData", personalDataDTO);

        /**
         * 下面的是对大分类的交易统计数量的一个业务操作
         */

        //统计书籍大分类的交易数量
        parameterMap.put("start", ClassifyContst.BOOKS_HEAD);
        parameterMap.put("end", ClassifyContst.BOOKS_TAIL);
        int booksNumber = classificationService.countClassificationNumber(parameterMap);
        if (booksNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("booksNumber",booksNumber);

        //统计文具大分类的交易数量
        parameterMap.put("start", ClassifyContst.STATIONERY_HEAD);
        parameterMap.put("end", ClassifyContst.STATIONERY_TAIL);
        int stationeryNumber = classificationService.countClassificationNumber(parameterMap);
        if (stationeryNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("stationeryNumber",stationeryNumber);

        //统计日用大分类的交易数量
        parameterMap.put("start", ClassifyContst.DAILY_HEAD);
        parameterMap.put("end", ClassifyContst.DAILY_TAIL);
        int dailyNumber = classificationService.countClassificationNumber(parameterMap);
        if (dailyNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("dailyNumber",dailyNumber);

        //统计美妆大分类的交易数量
        parameterMap.put("start", ClassifyContst.COSMETICS_HEAD);
        parameterMap.put("end", ClassifyContst.COSMETICS_TAIL);
        int cosmeticsNumber = classificationService.countClassificationNumber(parameterMap);
        if (cosmeticsNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("cosmeticsNumber",cosmeticsNumber);

        //统计食品大分类的交易数量
        parameterMap.put("start", ClassifyContst.FOOD_HEAD);
        parameterMap.put("end", ClassifyContst.FOOD_TAIL);
        int foodNumber = classificationService.countClassificationNumber(parameterMap);
        if (foodNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("foodNumber",foodNumber);

        //统计电器大分类的交易数量
        parameterMap.put("start", ClassifyContst.ELECTRICAL_HEAD);
        parameterMap.put("end", ClassifyContst.ELECTRICAL_TAIL);
        int electricalNumber = classificationService.countClassificationNumber(parameterMap);
        if (electricalNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("electricalNumber",electricalNumber);

        //获取所有交易完成的商品的数量
        int allSellGoodsNumber = sellService.countAllSellGoodsNumber();
        if (allSellGoodsNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }

        //计算"其他"大分类的交易统计数量
        int othersNumber = allSellGoodsNumber - booksNumber - stationeryNumber - dailyNumber - cosmeticsNumber - foodNumber - electricalNumber;
        if (othersNumber < 0) {
            resultMap.put("msg", "网站获取数据错误，请重新请求!");
            return resultMap;
        }
        resultMap.put("othersNumber",othersNumber);

        return resultMap;
    }

    /**
     * 获取系统信息,包括 ->
     * 1.操作系统
     * 2.JAVA运行环境
     * 3.JAVA虚拟机
     * 4.系统用户
     * 5.用户主目录
     * 6.用户工作目录
     * 7.最大内存
     * 8.已用内存
     * 9.可用内存
     *
     * @return
     */
    @GetMapping(value = "/getSystemMessage")
    public Map<String, String> getSystemMessage() {
        //设置保存返回给前端信息数据的hashMap
        Map<String, String> resultMap = new HashMap<>(16);
        //获取系统参数文件信息
        Properties properties = System.getProperties();

        //获取操作系统名称
        String operatingSystemName = properties.getProperty("os.arch");
        if (operatingSystemName == null || "".equals(operatingSystemName)) {
            resultMap.put("msg", "获取操作系统名称失败!");
            return resultMap;
        }
        resultMap.put("operatingSystemName", operatingSystemName);

        //获取Java运行时环境规范名称
        String environmentalSpecificationName = properties.getProperty("java.specification.name");
        if (environmentalSpecificationName == null || "".equals(environmentalSpecificationName)) {
            resultMap.put("msg", "获取Java运行时环境规范名称失败!");
            return resultMap;
        }
        resultMap.put("environmentalSpecificationName", environmentalSpecificationName);

        //获取Java的虚拟机规范名称
        String virtualMachineSpecificationName = properties.getProperty("java.vm.specification.name");
        if (virtualMachineSpecificationName == null || "".equals(virtualMachineSpecificationName)) {
            resultMap.put("msg", "获取Java的虚拟机规范名称失败!");
            return resultMap;
        }
        resultMap.put("virtualMachineSpecificationName", virtualMachineSpecificationName);


        //获取系统用户名称
        String usersOfTheSystem = properties.getProperty("user.name");
        if (usersOfTheSystem == null || "".equals(usersOfTheSystem)) {
            resultMap.put("msg", "获取系统用户名称失败!");
            return resultMap;
        }
        resultMap.put("usersOfTheSystem", usersOfTheSystem);


        //获取用户主目录
        String userHomeDirectory = properties.getProperty("user.home");
        if (userHomeDirectory == null || "".equals(userHomeDirectory)) {
            resultMap.put("msg", "获取用户主目录失败!");
            return resultMap;
        }
        resultMap.put("userHomeDirectory", userHomeDirectory);


        //获取用户工作目录
        String userWorkingDirectory = properties.getProperty("user.dir");
        if (userWorkingDirectory == null || "".equals(userWorkingDirectory)) {
            resultMap.put("msg", "获取用户工作目录失败!");
            return resultMap;
        }
        resultMap.put("userWorkingDirectory", userWorkingDirectory);


        //获取系统内存信息
        Runtime runtime = Runtime.getRuntime();
        //获取总内存信息
        Long totalMemoryL = runtime.totalMemory() / (1024L * 1024L);
        String totalMemory = String.valueOf(totalMemoryL);
        resultMap.put("totalMemory", totalMemory + "M");

        //获取剩余的内存信息
        Long freeMemoryL = runtime.freeMemory() / (1024L * 1024L);
        String freeMemory = String.valueOf(freeMemoryL);
        resultMap.put("freeMemory", freeMemory + "M");

        //获取已经用的内存
        Long usedMemoryL = totalMemoryL - freeMemoryL;
        if (usedMemoryL < 0) {
            resultMap.put("msg", "内存获取错误!");
            return resultMap;
        }
        String usedMemory = String.valueOf(usedMemoryL);
        resultMap.put("usedMemory", usedMemory + "M");


        return resultMap;
    }
}
