package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.dto.commodity.AuditDTO;
import com.ants.antsbackground.mapper.commodity.AuditMapper;
import com.ants.antsbackground.service.commodity.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理审核的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("AuditService")
public class AuditServiceImpl implements AuditService {
    @Autowired
    private AuditMapper auditMapper;

    /**
     * 获取在审核表的所有商品(待审核,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditCommodity(Map<String, Integer> parameterMap) {
        return auditMapper.listAuditCommodity(parameterMap);
    }

    /**
     * 获取在审核表的待审核或者回收站商品的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listCommodityByAudit(Map<String,Integer> parameterMap){
        return auditMapper.listCommodityByAudit(parameterMap);
    }

    /**
     * 统计不同类型(闲置,赠送,寻求,租赁)正在审核以及在回收站的商品的数量
     * @param goodsType
     * @return
     */
    public Integer countAuditByGoodsType(int goodsType){
        return auditMapper.countAuditByGoodsType(goodsType);
    }

    /**
     * 统计不同类型(闲置,赠送,寻求,租赁)正在审核或者在回收站的商品的数量
     * @param parameterMap
     * @return
     */
    public Integer countAuditByRecycle(Map<String,Integer> parameterMap){
        return auditMapper.countAuditByRecycle(parameterMap);
    }

    /**
     * 彻底删除审核商品的信息
     * @param goodsId
     * @return
     */
    public Integer deleteAudit(Integer goodsId){
        return auditMapper.deleteAudit(goodsId);
    }

    /**
     * 将审核商品状态进行更改，即进入回收站 -> 商品状态: 0 待审核 1回收站 2 审核通过
     * @param parameterMap
     * @return
     */
    public Integer updateAudit(Map<String,Integer> parameterMap){
        return auditMapper.updateAudit(parameterMap);
    }


}
