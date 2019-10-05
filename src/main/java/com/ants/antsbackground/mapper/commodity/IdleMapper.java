package com.ants.antsbackground.mapper.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理闲置的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface IdleMapper {
    /**
     * 获取在指定时间内(七天)发布闲置的数量
     *
     * @return
     */
    Integer countReleaseIdleNumber(Map<String, String> parameterMap);

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedIdleGoods(Map<String, Integer> parameterMap);

    /**
     * 统计审核通过的闲置的商品的数量
     *
     * @return
     */
    Integer countAuditedIdleGoods();

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<CommodityDTO> listIdleCommodity(Map<String, Integer> parameterMap);

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    Integer countIdleCommodity(int goodsType);

}
