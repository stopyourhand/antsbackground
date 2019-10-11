package com.ants.antsbackground.controller.commodity;

import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.service.commodity.GiveService;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.LeaseService;
import com.ants.antsbackground.service.commodity.SeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
     * 获取商品管理的数据列表信息,审核通过以及回收站的商品（所有按钮）
     * goodsType -> 商品审核类型 0:闲置 1: 寻求 2:租赁 3:赠送（前端参数）
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/list")
    public Map goodsManagementAll(@RequestParam(value = "currentPage") int currentPage,
                                @RequestParam(value = "goodsType") int goodsType) {
        //用来返回给前端数据的保存的hashMap
        Map resultMap = new HashMap(16);

        //保存从数据库中输入的参数的值的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);
        int head = (currentPage - 1) * PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER;
        parameterMap.put("length", PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER);
        //将从数据库第几条下标获取数据的参数的值传入parameterMap
        parameterMap.put("head", head);
        //判断数据格式是否正确
        if (currentPage <= 0) {
            resultMap.put("msg", "页面数据传输错误!");
            return resultMap;
        }
        //总页数
        int allPage = 0;

        //判断数据格式是否正确
        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型数据传输错误!");
            return resultMap;
        }
        switch (goodsType) {
            //闲置
            case 0:
                //获取所有闲置商品的数据信息列表
                List<AuditDTO> listAuditedIdleGoods = idleService.listAuditedIdleGoods(parameterMap);
                resultMap.put("goodsList", listAuditedIdleGoods);

                //统计所有的的闲置的商品的数量
                int idleNumber = idleService.countAuditedIdleGoods();
                if (idleNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (idleNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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
                resultMap.put("goodsList", listAuditedSeekGoods);

                //统计所有的的寻求的商品的数量
                int seekNumber = seekService.countAuditedSeekGoods();
                if (seekNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (seekNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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
                resultMap.put("goodsList", listAuditedLeaseGoods);

                //统计所有的的租赁的商品的数量
                int leaseNumber = leaseService.countAuditedLeaseGoods();
                if (leaseNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (leaseNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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
                resultMap.put("goodsList", listAuditedGiveGoods);

                //统计所有的的赠送的商品的数量
                int giveNumber = giveService.countAuditedGiveGoods();
                if (giveNumber < 0){
                    resultMap.put("msg","数据库数据出现错误!");
                    return resultMap;
                }
                //计算赠送商品的总页数
                allPage = (giveNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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
     * 获取商品管理的数据列表信息,审核通过（审核通过按钮）
     * goodsType -> 商品审核类型 0:闲置 1: 寻求 2:租赁 3:赠送（前端参数）
     *  0代表审核通过的商品，1代表回收站（后端参数，相当于数据库的goodsType）
     *
     * @param currentPage
     * @param goodsType
     * @return
     * @Param goodsType
     */
    @GetMapping(value = "/listThrough")
    public Map goodsManagementThrough(@RequestParam(value = "currentPage") int currentPage,
                                   @RequestParam(value = "goodsType") int goodsType) {
        //用来返回给前端数据的保存的hashMap
        Map resultMap = new HashMap(16);
        //保存从数据库中输入的参数的值的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //判断数据格式是否正确

        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型数据传输错误!");
            return resultMap;
        }
        if (currentPage <= 0) {
            resultMap.put("msg", "页面数据传输错误!");
            return resultMap;
        }


        int length = PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER;
        int head = (currentPage - 1) * length;
        //将从数据库第几条下标获取数据的参数的值传入parameterMap
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER);
        //0代表审核通过的商品，1代表回收站
        parameterMap.put("goodsType", 0);

        //数据总页数
        int allPage = 0;

        switch (goodsType) {
            //所有闲置
            case 0:
                //从数据库中获取赠送商品的信息
                List<CommodityDTO> listIdleCommodity = idleService.listIdleCommodity(parameterMap);

                resultMap.put("goodsList", listIdleCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int idleGoodsNumber = idleService.countIdleCommodity(0);
                if (idleGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (idleGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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

                resultMap.put("goodsList", listSeekCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int seekGoodsNumber = leaseService.countLeaseCommodity(0);
                if (seekGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (seekGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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

                resultMap.put("goodsList", listLeaseCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int leaseGoodsNumber = leaseService.countLeaseCommodity(0);
                if (leaseGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (leaseGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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

                resultMap.put("goodsList", listGiveCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int giveGoodsNumber = giveService.countGiveCommodity(0);
                if (giveGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (giveGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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
     * 获取商品管理的数据列表信息回收站（回收站按钮）
     * goodsType -> 商品审核类型 0:闲置 1: 寻求 2:租赁 3:赠送（前端参数）
     *  0代表审核通过的商品，1代表回收站（后端参数，相当于数据库的goodsType）
     *
     * @param currentPage
     * @param goodsType
     * @return
     * @Param auditType
     */
    @GetMapping(value = "/listRecycle")
    public Map goodsManagementRecycle(@RequestParam(value = "currentPage") int currentPage,
                                   @RequestParam(value = "goodsType") int goodsType) {
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


        //保存从数据库中输入的参数的值的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        int head = (currentPage - 1) * PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER;
        //将从数据库第几条下标获取数据的参数的值传入parameterMap
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER);
        //0代表审核通过的商品，1代表回收站
        parameterMap.put("goodsType", 1);

        //数据总页数
        int allPage = 0;

        switch (goodsType) {
            //所有闲置
            case 0:
                //从数据库中获取赠送商品的信息
                List<CommodityDTO> listIdleCommodity = idleService.listIdleCommodity(parameterMap);

                resultMap.put("goodsList", listIdleCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int idleGoodsNumber = idleService.countIdleCommodity(1);
                if (idleGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (idleGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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

                resultMap.put("goodsList", listSeekCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int seekGoodsNumber = leaseService.countLeaseCommodity(1);
                if (seekGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (seekGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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

                resultMap.put("goodsList", listLeaseCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int leaseGoodsNumber = leaseService.countLeaseCommodity(1);
                if (leaseGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (leaseGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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

                resultMap.put("goodsList", listGiveCommodity);

                //统计赠送的商品中通过审核或者正在回收站的商品的数量
                int giveGoodsNumber = giveService.countGiveCommodity(1);
                if (giveGoodsNumber < 0) {
                    resultMap.put("msg", "数据库获取数据错误!");
                    return resultMap;
                }

                allPage = (giveGoodsNumber / PageConsts.GOODS_MANAGEMENT_PAGE_NUMBER) + 1;
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
     * 判断对用户反馈的信息进行哪种审核操作（通过审核，撤销审核，警告）
     * type -> 0 -> 通过审核
     * type -> 1 -> 撤销审核
     * type -> 2 -> 警告
     * goodsType -> 商品审核类型 0:闲置 1:寻求 2:租赁 3:赠送
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.PATCH)
    public Map checkGoods(@RequestParam(value = "type") Integer type,
                          @RequestParam(value = "goodsType") Integer goodsType,
                          @RequestParam(value = "idList[]") int[] idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);

        //判断数据是否传输正确
        if (goodsType < 0) {
            resultMap.put("msg", "商品类型传输错误！");
            return resultMap;
        }
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);

        if (idList.length <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
            return resultMap;
        }
        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }

        //设置压迫删除的商品类型 0:闲置 1:寻求 2:租赁 3:赠送
        //对审核的操作类型进下判断
        switch (type) {
            //撤销审核，通过审核，对审核表中的商品进行状态改变，并且将商品信息添加到指定类型（闲置，寻求，租赁，赠送）商品表中
            case 0:
                //获取要通过审核的商品的id列表，对其进行状态改变，即弄进回收站里面
                for (int goodsId : idList) {
                    parameterMap.put("goodsId", goodsId);
                    //goodsType 0:审核通过 1:回收站
                    parameterMap.put("goodsType", 0);
                    int result = 0;
                    switch (goodsType){
                        //闲置
                        case 0:
                            //将闲置商品的状态改为回收站
                            result = idleService.updateIdle(parameterMap);
                            break;
                        //寻求
                        case 1:
                            //将寻求商品的状态改为回收站
                            result = seekService.updateSeek(parameterMap);
                            break;
                        //租赁
                        case 2:
                            //将租赁商品的状态改为回收站
                            result = leaseService.updateLease(parameterMap);
                            break;
                        //赠送
                        case 3:
                            //将赠送商品的状态改为回收站
                            result = giveService.updateGive(parameterMap);
                            break;
                        default:{}
                    }
                    if (result <= 0) {
                        resultMap.put("msg", "审核出现错误!");
                        return resultMap;
                    }
                }
                break;
            //警告操作
            case 1:
                //警告操作
                break;
            default:{}
        }
        resultMap.put("msg", "删除成功!");
        return resultMap;
    }

    /**
     * 判断对用户反馈的信息进行哪种删除操作
     * type -> 0 -> 删除
     * type -> 1 -> 撤销删除
     * type -> 2 -> 彻底删除
     * goodsType -> 商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public Map deleteGoods(@RequestParam(value = "type") Integer type,
                           @RequestParam(value = "goodsType") Integer goodsType,
                           @RequestParam(value = "idList[]") int[] idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);

        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }
        if (goodsType < 0) {
            resultMap.put("msg", "商品类型传输错误！");
            return resultMap;
        }

        if (idList.length <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
            return resultMap;
        }

        //设置压迫删除的商品类型 0:闲置 1:赠送 2:租赁 3:寻求
        parameterMap.put("goodsType", goodsType);
        //对删除的操作类型进下判断
        switch (type) {
            //删除操作，即将用户反馈信息放进回收站
            case 0:
                //获取要通过审核的商品的id列表，对其进行状态改变，即弄进回收站里面
                for (int goodsId : idList) {
                    int result = 0;
                    parameterMap.put("goodsId", goodsId);
                    //goodsType 0:审核通过 1:回收站
                    parameterMap.put("goodsType", 1);
                    switch (goodsType){
                        //闲置
                        case 0:
                            //将闲置商品的状态改为回收站
                            result = idleService.updateIdle(parameterMap);
                            break;
                        //寻求
                        case 1:
                            //将寻求商品的状态改为回收站
                            result = seekService.updateSeek(parameterMap);
                            break;
                        //租赁
                        case 2:
                            //将租赁商品的状态改为回收站
                            result = leaseService.updateLease(parameterMap);
                            break;
                        //赠送
                        case 3:
                            //将赠送商品的状态改为回收站
                            result = giveService.updateGive(parameterMap);
                            break;
                        default:{}
                    }
                    if (result <= 0) {
                        resultMap.put("msg", "审核出现错误!");
                        return resultMap;
                    }
                }
                break;
            //撤销删除操作
            case 1:
                //获取要通过审核的商品的id列表，对其进行状态改变，即弄进回收站里面
                for (int goodsId : idList) {
                    int result = 0;
                    parameterMap.put("goodsId", goodsId);
                    //goodsType 0:审核通过 1:回收站
                    parameterMap.put("goodsType", 0);
                    switch (goodsType){
                        //闲置
                        case 0:
                            //将闲置商品的状态改为回收站
                            result = idleService.updateIdle(parameterMap);
                            break;
                        //寻求
                        case 1:
                            //将寻求商品的状态改为回收站
                            result = seekService.updateSeek(parameterMap);
                            break;
                        //租赁
                        case 2:
                            //将租赁商品的状态改为回收站
                            result = leaseService.updateLease(parameterMap);
                            break;
                        //赠送
                        case 3:
                            //将赠送商品的状态改为回收站
                            result = giveService.updateGive(parameterMap);
                            break;
                        default:{}
                    }
                    if (result <= 0) {
                        resultMap.put("msg", "审核出现错误!");
                        return resultMap;
                    }
                }
                break;
            //彻底删除
            case 2:
                //获取要通过审核的商品的id列表，对其进行状态改变，即弄进回收站里面
                for (int goodsId : idList) {
                    int result = 0;
                    parameterMap.put("goodsId", goodsId);
                    switch (goodsType){
                        //闲置
                        case 0:
                            //将闲置商品的状态改为回收站
                            result = idleService.deleteIdle(goodsId);
                            break;
                        //寻求
                        case 1:
                            //将寻求商品的状态改为回收站
                            result = seekService.deleteSeek(goodsId);
                            break;
                        //租赁
                        case 2:
                            //将租赁商品的状态改为回收站
                            result = leaseService.deleteLease(goodsId);
                            break;
                        //赠送
                        case 3:
                            //将赠送商品的状态改为回收站
                            result = giveService.deleteGive(goodsId);
                            break;
                        default:{}
                    }
                    if (result <= 0) {
                        resultMap.put("msg", "审核出现错误!");
                        return resultMap;
                    }
                }
                break;
        }
        resultMap.put("msg", "删除成功!");
        return resultMap;
    }


}
