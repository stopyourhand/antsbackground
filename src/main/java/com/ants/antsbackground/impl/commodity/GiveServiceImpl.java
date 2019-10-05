package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.mapper.commodity.GiveMapper;
import com.ants.antsbackground.service.commodity.GiveService;
import com.ants.antsbackground.service.commodity.IdleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理赠送的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("GiveService")
public class GiveServiceImpl implements GiveService {

    @Autowired
    private GiveMapper giveMapper;

    /**
     * 获取在指定时间内(七天)发布赠送的数量
     *
     * @return
     */
    public Integer countReleaseGiveNumber(Map<String, String> parameterMap) {
        return giveMapper.countReleaseGiveNumber(parameterMap);
    }

    /**
     * 获取在已经通过审核的赠送的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditedGiveGoods(Map<String, Integer> parameterMap) {
        return giveMapper.listAuditedGiveGoods(parameterMap);
    }

    /**
     * 统计审核通过的赠送的商品的数量
     *
     * @return
     */
    public Integer countAuditedGiveGoods() {
        return giveMapper.countAuditedGiveGoods();
    }

    /**
     * 获取在赠送表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<CommodityDTO> listGiveCommodity(Map<String, Integer> parameterMap) {
        return giveMapper.listGiveCommodity(parameterMap);
    }

    /**
     * 统计赠送的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    public Integer countGiveCommodity(int goodsType) {
        return giveMapper.countGiveCommodity(goodsType);
    }
}
