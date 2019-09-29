package com.ants.antsbackground.service.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理审核的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface AuditService {
    /**
     * 获取在审核表的所有商品(待审核,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditCommodity(Map<String,Integer> parameterMap);

    /**
     * 获取在审核表的待审核或者回收站商品的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listCommodityByAudit(Map<String,Integer> parameterMap);

    /**
     * 统计不同类型(闲置,赠送,寻求,租赁)正在审核以及在回收站的商品的数量
     * @param goodsType
     * @return
     */
    Integer countAuditByGoodsType(int goodsType);

    /**
     * 统计不同类型(闲置,赠送,寻求,租赁)正在审核或者在回收站的商品的数量
     * @param parameterMap
     * @return
     */
    Integer countAuditByRecycle(Map<String,Integer> parameterMap);

}
