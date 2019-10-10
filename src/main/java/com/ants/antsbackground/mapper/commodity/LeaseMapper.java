package com.ants.antsbackground.mapper.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.entity.commodity.IdleGoods;
import com.ants.antsbackground.entity.commodity.LeaseGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理租赁的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface LeaseMapper {
    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    Integer countReleaseLeaseNumber(Map<String,String> parameterMap);

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedLeaseGoods(Map<String,Integer> parameterMap);

    /**
     * 获取交易分析的列出不同时间段的发布成功的租赁的商品的信息
     * @param parameterMap
     * @return
     */
    List<LeaseGoods> listLeaseAnalysis(Map<String,String> parameterMap);

    /**
     * 统计审核通过的闲置的商品的数量
     * @return
     */
    Integer countAuditedLeaseGoods();

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<CommodityDTO> listLeaseCommodity(Map<String, Integer> parameterMap);

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    Integer countLeaseCommodity(int goodsType);

    /**
     * 获取在指定时间内不同分类的租赁商品的数量
     * @param parameterMap
     * @return
     */
    Integer countReleaseClassifyLeaseNumber(Map<String,Integer> parameterMap);

}
