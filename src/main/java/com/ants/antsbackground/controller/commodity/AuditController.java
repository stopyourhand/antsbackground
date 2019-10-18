package com.ants.antsbackground.controller.commodity;

import com.ants.antsbackground.constant.PageConsts;
import com.ants.antsbackground.dto.commodity.AuditDTO;
import com.ants.antsbackground.service.commodity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping(value = "/background/release")
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
     * 发布管理列出包括审核和回收站的所有商品的信息(所有按钮)
     * goodsType - >商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/list")
    public Map releaseManagementAll(@RequestParam(value = "currentPage") int currentPage,
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

        //设置商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
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
     * 发布管理列出待审核的所有商品的信息(等待审核按钮)
     * goodsType - >商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/listWait")
    public Map releaseManagementWait(@RequestParam(value = "currentPage") int currentPage,
                                     @RequestParam(value = "goodsType") int goodsType) {
        Map resultMap = new LinkedHashMap(16);
        //用来保存数据库方法参数的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);
        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型格式错误,请重新传入!");
            return resultMap;
        }
        if (currentPage <= 0) {
            resultMap.put("msg", "页面传输格式错误,请重新传入!");
            return resultMap;
        }

        //商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
        parameterMap.put("goodsType", goodsType);
        //0代表待审核，1代表回收站
        parameterMap.put("goodsState", 0);

        //获取数据库Limit中从第几个数据开始获取数据
        int head = (currentPage - 1) * PageConsts.AUDIT_PAGE_NUMBER;
        if (head < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }
        parameterMap.put("length", PageConsts.AUDIT_PAGE_NUMBER);
        parameterMap.put("head", head);

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
     * 发布管理列出通过审核商品的信息(通过审核按钮)
     * goodsType - >商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/listThrough")
    public Map releaseManagementThrough(@RequestParam(value = "currentPage") int currentPage,
                                        @RequestParam(value = "goodsType") int goodsType) {
        Map resultMap = new LinkedHashMap(16);

        if (goodsType < 0 || goodsType > 3) {
            resultMap.put("msg", "商品类型格式错误,请重新传入!");
            return resultMap;
        }
        if (currentPage <= 0) {
            resultMap.put("msg", "页面传输格式错误,请重新传入!");
            return resultMap;
        }
        //用来保存数据库方法参数的hashMap
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //获取数据库Limit中从第几个数据开始获取数据
        int head = (currentPage - 1) * PageConsts.AUDIT_PAGE_NUMBER;
        if (head < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }

        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.AUDIT_PAGE_NUMBER);

        parameterMap.put("goodsType", goodsType);
        int allPage = 0;
        //判断需要获取哪种商品类型(闲置,租赁,寻求,赠送)的数据列表
        switch (goodsType) {
            //闲置商品的类型
            case 0:
                //获取闲置的商品的数据信息列表
                List<AuditDTO> auditIdleList = seekService.listAuditedSeekGoods(parameterMap);
                resultMap.put("auditList", auditIdleList);

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
                resultMap.put("auditList", auditSeekList);

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
                resultMap.put("auditList", leaseSeekList);

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
                resultMap.put("auditList", giveSeekList);

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


    /**
     * 发布管理列出包括审核和回收站的所有商品的信息(回收站按钮)
     * goodsType - >商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
     *
     * @param currentPage
     * @param goodsType
     * @return
     */
    @GetMapping(value = "/listRecycle")
    public Map releaseManagementRecycle(@RequestParam(value = "currentPage") int currentPage,
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

        //商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
        parameterMap.put("goodsType", goodsType);

        //获取数据库Limit中从第几个数据开始获取数据
        int head = (currentPage - 1) * PageConsts.AUDIT_PAGE_NUMBER;
        if (head < 0) {
            resultMap.put("msg", "数据格式错误!");
            return resultMap;
        }
        parameterMap.put("head", head);
        parameterMap.put("length", PageConsts.AUDIT_PAGE_NUMBER);

        //0代表待审核，1代表回收站
        parameterMap.put("goodsState", 1);
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
     * 判断对用户反馈的信息进行哪种审核操作（通过审核，撤销审核，警告）
     * type -> 0 -> 通过审核
     * type -> 1 -> 撤销审核
     * type -> 2 -> 警告
     * goodsType -> 商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求
     *
     * @param type
     * @param idList
     * @return
     */
    @RequestMapping(value = "/check", method = RequestMethod.PATCH)
    public Map checkAudit(@RequestParam(value = "type") Integer type,
                          @RequestParam(value = "goodsType") Integer goodsType,
                          @RequestParam(value = "idList") List<Integer> idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);
        //判断数据是否传输正确
        if (goodsType < 0) {
            resultMap.put("msg", "商品类型传输错误！");
            return resultMap;
        }
        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }
        if (idList.size() <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
            return resultMap;
        }

        //设置压迫删除的商品类型 0:闲置 1:赠送 2:租赁 3:寻求
        parameterMap.put("goodsType", goodsType);
        //对审核的操作类型进下判断
        switch (type) {
            //通过审核，对审核表中的商品进行状态改变，并且将商品信息添加到指定类型（闲置，寻求，租赁，赠送）商品表中
            case 0:
                //获取要通过审核的商品的id列表，对其进行状态改变，即弄进回收站里面
                for (int goodsId : idList) {
                    parameterMap.put("goodsId", goodsId);
                    parameterMap.put("goodsState", 2);
                    int result = auditService.updateAudit(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "审核出现错误!");
                        return resultMap;
                    }
                }
                break;
            //撤销审核操作
            case 1:
                //获取要撤销删除的审核中的商品的id列表，对其进行状态改变
                for (int goodsId : idList) {
                    parameterMap.put("goodsId", goodsId);
                    parameterMap.put("goodsState", 0);
                    int result = auditService.updateAudit(parameterMap);
                    if (result <= 0) {
                        resultMap.put("judge", false);
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //警告
            case 2:
                //警告操作
        }
        resultMap.put("judge", true);
        resultMap.put("msg", "商品审核通过!");
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
    public Map deleteAudit(@RequestParam(value = "type") Integer type,
                           @RequestParam(value = "goodsType") Integer goodsType,
                           @RequestParam(value = "idList") List<Integer> idList) {
        //存放返回给前端数据的一个map
        Map resultMap = new HashMap(16);
        //存放对数据库的操作方法的参数的值
        Map<String, Integer> parameterMap = new HashMap<>(16);

        //判断参数格式是否正确
        if (type < 0 || type > 2) {
            resultMap.put("msg", "删除类型错误！");
            return resultMap;
        }
        if (goodsType < 0) {
            resultMap.put("msg", "商品类型传输错误！");
            return resultMap;
        }
        if (idList.size() <= 0) {
            resultMap.put("msg", "请选择要删除的反馈信息！");
            return resultMap;
        }

        //设置压迫删除的商品类型 0:闲置 1:赠送 2:租赁 3:寻求
        parameterMap.put("goodsType", goodsType);
        //对删除的操作类型进下判断
        switch (type) {
            //删除操作，即将用户反馈信息放进回收站
            case 0:
                //获取要删除的反馈信息的id列表，对其进行状态改变，即弄进回收站里面
                for (int goodsId : idList) {
                    parameterMap.put("goodsId", goodsId);
                    parameterMap.put("goodsState", 1);
                    int result = auditService.updateAudit(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //撤销删除操作
            case 1:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                for (int goodsId : idList) {
                    parameterMap.put("goodsId", goodsId);
                    parameterMap.put("goodsState", 0);
                    int result = auditService.updateAudit(parameterMap);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
                break;
            //彻底删除
            case 2:
                //获取要撤销删除的反馈信息的id列表，对其进行状态改变
                for (int goodsId : idList) {
                    int result = auditService.deleteAudit(goodsId);
                    if (result <= 0) {
                        resultMap.put("msg", "删除错误，反馈编号出现错误!");
                        return resultMap;
                    }
                }
        }
        resultMap.put("msg", "删除成功!");
        return resultMap;
    }

}
