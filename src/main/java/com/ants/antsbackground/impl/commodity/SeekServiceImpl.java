package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.dto.commodity.AuditDTO;
import com.ants.antsbackground.dto.commodity.CommodityDTO;
import com.ants.antsbackground.entity.commodity.SeekGoods;
import com.ants.antsbackground.mapper.commodity.SeekMapper;
import com.ants.antsbackground.service.commodity.SeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理寻求的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("SeekService")
public class SeekServiceImpl implements SeekService {
    @Autowired
    private SeekMapper seekMapper;


    /**
     * 获取在已经通过审核的寻求的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditedSeekGoods(Map<String, Integer> parameterMap) {
        return seekMapper.listAuditedSeekGoods(parameterMap);
    }

    /**
     * 获取交易分析的列出不同时间段的发布成功的寻求的商品的信息
     *
     * @param parameterMap
     * @return
     */
    public List<SeekGoods> listSeekAnalysis(Map<String, String> parameterMap) {
        return seekMapper.listSeekAnalysis(parameterMap);
    }

    /**
     * 统计审核通过的寻求的商品的数量
     *
     * @return
     */
    public Integer countAuditedSeekGoods() {
        return seekMapper.countAuditedSeekGoods();
    }

    /**
     * 获取在指定时间内(七天)发布寻求的数量
     *
     * @return
     */
    public Integer countReleaseSeekNumber(Map<String, String> parameterMap) {
        return seekMapper.countReleaseSeekNumber(parameterMap);
    }

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<CommodityDTO> listSeekCommodity(Map<String, Integer> parameterMap) {
        return seekMapper.listSeekCommodity(parameterMap);
    }

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    public Integer countSeekCommodity(int goodsType) {
        return countSeekCommodity(goodsType);
    }

    /**
     * 获取在指定时间内不同分类的寻求商品的数量
     *
     * @param parameterMap
     * @return
     */
    public Integer countReleaseClassifySeekNumber(Map<String, Integer> parameterMap){
        return seekMapper.countReleaseClassifySeekNumber(parameterMap);
    }

    /**
     * 彻底删除寻求商品的信息
     * @param goodsId
     * @return
     */
    public Integer deleteSeek(Integer goodsId){
        return seekMapper.deleteSeek(goodsId);
    }

    /**
     * 将寻求商品状态进行更改，即进入回收站 -> 商品状态: goodsType 0:审核通过 1:回收站
     * @param parameterMap
     * @return
     */
    public Integer updateSeek(Map<String,Integer> parameterMap){
        return seekMapper.updateSeek(parameterMap);
    }
}
