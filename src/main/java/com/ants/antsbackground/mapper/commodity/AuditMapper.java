package com.ants.antsbackground.mapper.commodity;

import com.ants.antsbackground.dto.commodity.AuditDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理审核的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface AuditMapper {
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

    /**
     * 彻底删除审核商品的信息
     * @param goodsId
     * @return
     */
    Integer deleteAudit(Integer goodsId);

    /**
     * 将审核商品状态进行更改，即进入回收站 -> 商品状态: 0 待审核 1回收站 2 审核通过
     * @param parameterMap
     * @return
     */
    Integer updateAudit(Map<String,Integer> parameterMap);

}
