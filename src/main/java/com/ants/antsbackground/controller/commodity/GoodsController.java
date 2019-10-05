package com.ants.antsbackground.controller.commodity;

import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.service.commodity.GiveService;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.LeaseService;
import com.ants.antsbackground.service.commodity.SeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是关于商品管理的控制器，包括所有闲置，所有寻求，所有租赁和所有赠送
 *
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@RestController
@RequestMapping(value = "/background/goods")
public class GoodsController {
    @Autowired
    private GiveService giveService;

    @Autowired
    private IdleService idleService;

    @Autowired
    private LeaseService leaseService;

    @Autowired
    private SeekService seekService;


    /**
     * 获取商品管理的数据列表信息,审核通过以及回收站的商品
     * goodsType -> 商品审核类型 0:闲置 1: 寻求 2:租赁 3:赠送（前端参数）
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/list")
    public Map listAllCommodity(@RequestParam(value = "currentPage") int currentPage,
                                @RequestParam(value = "goodsType") int goodsType) {
        //用来返回给前端数据的保存的hashMap
        Map resultMap = new HashMap(16);
        //判断数据格式是否正确
        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型数据传输错误!");
            return resultMap;
        }
        if (currentPage <= 0) {
            resultMap.put("msg", "页面数据传输错误!");
            return resultMap;
        }

        //保存从数据库中输入的参数的值的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);
        int head = (currentPage - 1) * PageConsts.GOODS_Management_PAGE_NUMBER;
        parameterMap.put("length", PageConsts.GOODS_Management_PAGE_NUMBER);
        //将从数据库第几条下标获取数据的参数的值传入parameterMap
        parameterMap.put("head", head);

        //总页数
        int allPage = 0;

        switch (goodsType) {
            //闲置
            case 0:
                //获取所有闲置商品的数据信息列表
                List<AuditDTO> listAuditedIdleGoods = idleService.listAuditedIdleGoods(parameterMap);
                resultMap.put("listAuditedIdleGoods", listAuditedIdleGoods);

                //统计所有的的闲置的商品的数量
                int idleNumber = idleService.countAuditedIdleGoods();
                if (idleNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (idleNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //寻求
            case 1:
                //获取所有寻求商品的数据信息列表
                List<AuditDTO> listAuditedSeekGoods = seekService.listAuditedSeekGoods(parameterMap);
                resultMap.put("listAuditedSeekGoods", listAuditedSeekGoods);

                //统计所有的的寻求的商品的数量
                int seekNumber = seekService.countAuditedSeekGoods();
                if (seekNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (seekNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //租赁
            case 2:
                //获取所有租赁商品的数据信息列表
                List<AuditDTO> listAuditedLeaseGoods = leaseService.listAuditedLeaseGoods(parameterMap);
                resultMap.put("listAuditedLeaseGoods", listAuditedLeaseGoods);

                //统计所有的的租赁的商品的数量
                int leaseNumber = leaseService.countAuditedLeaseGoods();
                if (leaseNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (leaseNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //赠送
            case 3:
                //获取所有赠送商品的数据信息列表
                List<AuditDTO> listAuditedGiveGoods = giveService.listAuditedGiveGoods(parameterMap);
                resultMap.put("listAuditedGiveGoods", listAuditedGiveGoods);

                //统计所有的的赠送的商品的数量
                int giveNumber = giveService.countAuditedGiveGoods();
                if (giveNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (giveNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            default:
                break;
        }

        return resultMap;
    }

    /**
     * 获取商品管理的数据列表信息,审核通过或者回收站
     * goodsType -> 商品审核类型 0:闲置 1: 寻求 2:租赁 3:赠送（前端参数）
     * auditType -> 0代表审核通过的商品，1代表回收站（后端参数，相当于数据库的goodsType）
     *
     * @param currentPage
     * @param goodsType
     * @return
     * @Param auditType
     */
    @GetMapping(value = "/listRecycle")
    public Map commodityManagement(@RequestParam(value = "currentPage") int currentPage,
                                   @RequestParam(value = "goodsType") int goodsType,
                                   @RequestParam(value = "auditType") int auditType) {
        //用来返回给前端数据的保存的hashMap
        Map resultMap = new HashMap(16);

        //判断数据格式是否正确
        if (currentPage <= 0) {
            resultMap.put("msg", "页面数据传输错误!");
            return resultMap;
        }
        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型数据传输错误!");
            return resultMap;
        }
        if (auditType < 0 || auditType > 1) {
            resultMap.put("msg", "商品类型数据传输错误!");
            return resultMap;
        }

        //保存从数据库中输入的参数的值的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        int head = (currentPage - 1) * PageConsts.GOODS_Management_PAGE_NUMBER;
        //将从数据库第几条下标获取数据的参数的值传入parameterMap
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.GOODS_Management_PAGE_NUMBER);
        //0代表审核通过的商品，1代表回收站
        parameterMap.put("goodsType", auditType);

        //数据总页数
        int allPage = 0;

        switch (goodsType) {
            //所有闲置
            case 0:
                //从数据库中获取赠送商品的信息
                List<CommodityDTO> listIdleCommodity = idleService.listIdleCommodity(parameterMap);

                resultMap.put("listIdleCommodity", listIdleCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int idleGoodsNumber = idleService.countIdleCommodity(auditType);
                if (idleGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (idleGoodsNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //所有寻求
            case 1:
                //从数据库中获取赠送商品的信息
                List<CommodityDTO> listSeekCommodity = seekService.listSeekCommodity(parameterMap);

                resultMap.put("listSeekCommodity", listSeekCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int seekGoodsNumber = leaseService.countLeaseCommodity(auditType);
                if (seekGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (seekGoodsNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //租赁
            case 2:
                //从数据库中获取赠送商品的信息
                List<CommodityDTO> listLeaseCommodity = leaseService.listLeaseCommodity(parameterMap);

                resultMap.put("listLeaseCommodity", listLeaseCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int leaseGoodsNumber = leaseService.countLeaseCommodity(auditType);
                if (leaseGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (leaseGoodsNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            //赠送
            case 3:
                //从数据库中获取赠送商品的信息
                List<CommodityDTO> listGiveCommodity = giveService.listGiveCommodity(parameterMap);

                resultMap.put("listGiveCommodity", listGiveCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int giveGoodsNumber = giveService.countGiveCommodity(auditType);
                if (giveGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (giveGoodsNumber / PageConsts.GOODS_Management_PAGE_NUMBER) + 1;
                if (allPage <= 0) {
                    resultMap.put("msg", "数据获取出现错误!");
                    return resultMap;
                }
                resultMap.put("allPage", allPage);
                break;
            default:
                break;
        }
        return resultMap;
    }

}
