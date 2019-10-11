package com.ants.antsbackground.mapper.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.entity.commodity.GiveGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理赠送的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface GiveMapper {

    /**
     * 获取在指定时间内(七天)发布赠送的数量
     * @return
     */
    Integer countReleaseGiveNumber(Map<String,String> parameterMap);

    /**
     * 获取在已经通过审核的赠送的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedGiveGoods(Map<String,Integer> parameterMap);

    /**
     * 获取交易分析的列出不同时间段的发布成功的赠送的商品的信息
     * @param parameterMap
     * @return
     */
    List<GiveGoods> listGiveAnalysis(Map<String,String> parameterMap);

    /**
     * 统计审核通过的赠送的商品的数量
     * @return
     */
    Integer countAuditedGiveGoods();

    /**
     * 获取在赠送表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<CommodityDTO> listGiveCommodity(Map<String,Integer> parameterMap);

    /**
     * 统计赠送的商品中通过审核或者正在回收站的商品的数量
     * @param goodsType
     * @return
     */
    Integer countGiveCommodity(int goodsType);

    /**
     * 获取在指定时间内不同分类的赠送商品的数量
     * @param parameterMap
     * @return
     */
    Integer countReleaseClassifyGiveNumber(Map<String,Integer> parameterMap);

    /**
     * 彻底删除赠送商品的信息
     * @param goodsId
     * @return
     */
    Integer deleteGive(Integer goodsId);

    /**
     * 将赠送商品状态进行更改，即进入回收站 -> 商品状态: goodsType 0:审核通过 1:回收站
     * @param parameterMap
     * @return
     */
    Integer updateGive(Map<String,Integer> parameterMap);
}
