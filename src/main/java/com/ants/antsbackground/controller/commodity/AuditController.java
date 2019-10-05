package com.ants.antsbackground.controller.commodity;

import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.service.commodity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个控制器是关于一些和审核相关的业务处理，例如统计访问模块那部分业务
 *
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "background/audit")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private GiveService giveService;

    @Autowired
    private IdleService idleService;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private SeekService seekService;

    /**
     * 发布管理列出包括审核和回收站的所有商品的信息
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/releaseManagementByAll")
    public Map releaseManagementByAll(@RequestParam(value = "currentPage") int currentPage,
                                      @RequestParam(value = "goodsType") int goodsType) {
        Map resultMap = new LinkedHashMap(16);
        if (currentPage <= 0) {
            resultMap.put("msg", "页面传输格式错误,请重新传入!");
            return resultMap;
        }

        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型格式错误,请重新传入!");
            return resultMap;
        }

        //用来保存数据库方法参数的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        parameterMap.put("goodsType", goodsType);

        //获取数据库Limit中从第几个数据开始获取数据
        int head = (currentPage - 1) * PageConsts.AUDIT_PAGE_NUMBER;
        if (head < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.AUDIT_PAGE_NUMBER);

        //获取审核商品中指定类型的(闲置,租赁,赠送,寻求)的商品的信息
        List<AuditDTO> auditDTOList = auditService.listAuditCommodity(parameterMap);
        resultMap.put("auditList", auditDTOList);

        //获取正在审核中的指定商品(闲置,租赁,赠送,寻求)的所有数量
        int allAuditGoodsNumber = auditService.countAuditByGoodsType(goodsType);
        if (allAuditGoodsNumber < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }

        //获取指定审核中的商品(闲置,租赁,赠送,寻求)的列表的总页数
        int allPage = (allAuditGoodsNumber / PageConsts.AUDIT_PAGE_NUMBER) + 1;
        if (allPage < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }
        resultMap.put("allPage", allPage);

        return resultMap;
    }

    /**
     * 发布管理列出包括审核或者回收站的所有商品的信息
     * goodsState -> 0:待审核,1:回收站
     * goodsType -> 商品审核类型 0:闲置 1: 寻求 2:租赁 3:赠送
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/releaseManagementByAuditOrRecycle")
    public Map releaseManagementByAll(@RequestParam(value = "currentPage") int currentPage,
                                      @RequestParam(value = "goodsType") int goodsType,
                                      @RequestParam(value = "goodsState") int goodsState) {
        //原来保存返回给前端数据的hashMap
        Map resultMap = new LinkedHashMap(16);
        //用来保存数据库方法参数的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //判断数据格式是否有误
        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型格式错误,请重新传入!");
            return resultMap;
        }
        if (currentPage <= 0) {
            resultMap.put("msg", "页面传输格式错误,请重新传入!");
            return resultMap;
        }
        if (goodsState < 0 || goodsState > 1) {
            resultMap.put("msg", "商品类型格式错误,请重新传入!");
            return resultMap;
        }


        parameterMap.put("goodsType", goodsType);
        parameterMap.put("goodsState", goodsState);

        //获取数据库Limit中从第几个数据开始获取数据
        int head = (currentPage - 1) * PageConsts.AUDIT_PAGE_NUMBER;
        if (head < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.AUDIT_PAGE_NUMBER);

        //获取审核商品中指定类型的(闲置,租赁,赠送,寻求)的商品的信息
        List<AuditDTO> auditDTOList = auditService.listCommodityByAudit(parameterMap);
        resultMap.put("auditList", auditDTOList);

        //获取正在审核中的指定商品(闲置,租赁,赠送,寻求)的所有数量
        int allAuditOrRecycleNumber = auditService.countAuditByRecycle(parameterMap);
        if (allAuditOrRecycleNumber < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }

        //获取指定审核中的商品(闲置,租赁,赠送,寻求)的列表的总页数
        int allPage = (allAuditOrRecycleNumber / PageConsts.AUDIT_PAGE_NUMBER) + 1;
        if (allPage < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }
        resultMap.put("allPage", allPage);

        return resultMap;
    }

    /**
     * 列出通过审核的闲置,租赁,赠送,寻求的商品的列表
     * goodsType -> 0: 闲置商品 1: 寻求商品 2: 租赁商品 3: 赠送商品
     *
     * @param currentPage
     * @param type
     * @return
     */
    @GetMapping(value = "/throughTheReview")
    public Map throughTheReview(@RequestParam(value = "currentPage") int currentPage,
                                @RequestParam(value = "goodsType") int type) {
        //保存返回给前端的数据的hashMap
        Map resultMap = new LinkedHashMap(16);
        //保存传给数据库的参数的值的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //盘带你数据格式是否符合
        if (currentPage <= 0) {
            resultMap.put("msg", "页面数据传输错误,请重新传送!");
            return resultMap;
        }
        if (type < 0 || type > 3) {
            resultMap.put("msg", "商品类型传输错误,请重新传送!");
            return resultMap;
        }

        //获取从数据库中第几条数据开始拿取,Limit语句
        int head = (currentPage - 1) * PageConsts.AUDIT_PAGE_NUMBER;
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.AUDIT_PAGE_NUMBER);

        //总页数
        int allPage = 0;

        //判断需要获取哪种商品类型(闲置,租赁,寻求,赠送)的数据列表
        switch (type) {
            //闲置商品的类型
            case 0:
                //获取闲置的商品的数据信息列表
                List<AuditDTO> auditIdleList = seekService.listAuditedSeekGoods(parameterMap);
                resultMap.put("auditIdleList", auditIdleList);

                //获取通过审核的闲置的商品的数量
                int auditIdleNumber = idleService.countAuditedIdleGoods();
                if (auditIdleNumber < 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                allPage = (auditIdleNumber / PageConsts.AUDIT_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //寻求商品的类型
            case 1:
                //获取寻求的商品的数据信息列表
                List<AuditDTO> auditSeekList = idleService.listAuditedIdleGoods(parameterMap);
                resultMap.put("auditSeekList", auditSeekList);

                //获取通过审核的寻求的商品的数量
                int auditSeekNumber = seekService.countAuditedSeekGoods();
                if (auditSeekNumber < 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                allPage = (auditSeekNumber / PageConsts.AUDIT_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //租赁商品的类型
            case 2:
                //获取租赁的商品的数据信息列表
                List<AuditDTO> leaseSeekList = leaseService.listAuditedLeaseGoods(parameterMap);
                resultMap.put("leaseSeekList", leaseSeekList);

                //获取通过审核的租赁的商品的数量
                int auditLeaseNumber = leaseService.countAuditedLeaseGoods();
                if (auditLeaseNumber < 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                allPage = (auditLeaseNumber / PageConsts.AUDIT_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //赠送商品的类型
            case 3:
                //获取赠送的商品的数据信息列表
                List<AuditDTO> giveSeekList = giveService.listAuditedGiveGoods(parameterMap);
                resultMap.put("giveSeekList", giveSeekList);

                //获取通过审核的赠送的商品的数量
                int auditGiveNumber = giveService.countAuditedGiveGoods();
                if (auditGiveNumber < 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                allPage = (auditGiveNumber / PageConsts.AUDIT_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;

        }

        return resultMap;
    }
}
