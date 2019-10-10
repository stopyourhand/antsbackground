package com.ants.antsbackground.service.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.entity.commodity.SeekGoods;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理寻求的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface SeekService {


    /**
     * 获取在已经通过审核的寻求的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedSeekGoods(Map<String, Integer> parameterMap);

    /**
     * 获取交易分析的列出不同时间段的发布成功的寻求的商品的信息
     * @param parameterMap
     * @return
     */
    List<SeekGoods> listSeekAnalysis(Map<String,String> parameterMap);

    /**
     * 统计审核通过的寻求的商品的数量
     *
     * @return
     */
    Integer countAuditedSeekGoods();

    /**
     * 获取在指定时间内(七天)发布寻求的数量
     *
     * @return
     */
    Integer countReleaseSeekNumber(Map<String, String> parameterMap);

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<CommodityDTO> listSeekCommodity(Map<String, Integer> parameterMap);

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    Integer countSeekCommodity(int goodsType);

    /**
     * 获取在指定时间内不同分类的寻求商品的数量
     * @param parameterMap
     * @return
     */
    Integer countReleaseClassifySeekNumber(Map<String,Integer> parameterMap);


}
