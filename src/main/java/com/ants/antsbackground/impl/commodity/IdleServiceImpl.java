package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.dto.CommodityDTO;
import com.ants.antsbackground.entity.commodity.IdleGoods;
import com.ants.antsbackground.mapper.commodity.IdleMapper;
import com.ants.antsbackground.mapper.sell.SellMapper;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.sell.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理闲置的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("IdleService")
public class IdleServiceImpl implements IdleService {
    @Autowired
    private IdleMapper idleMapper;

    /**
     * 获取在指定时间内(七天)发布闲置的数量
     *
     * @return
     */
    public Integer countReleaseIdleNumber(Map<String, String> parameterMap) {
        return idleMapper.countReleaseIdleNumber(parameterMap);
    }

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditedIdleGoods(Map<String, Integer> parameterMap) {
        return idleMapper.listAuditedIdleGoods(parameterMap);
    }

    /**
     * 获取交易分析的列出不同时间段的发布成功的闲置的商品的信息
     * @param parameterMap
     * @return
     */
    public List<IdleGoods> listIdleAnalysis(Map<String,String> parameterMap){
        return idleMapper.listIdleAnalysis(parameterMap);
    }

    /**
     * 统计审核通过的闲置的商品的数量
     *
     * @return
     */
    public Integer countAuditedIdleGoods() {
        return idleMapper.countAuditedIdleGoods();
    }

    /**
     * 获取在闲置表的所有商品(审核通过,回收站)的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    public List<CommodityDTO> listIdleCommodity(Map<String, Integer> parameterMap) {
        return idleMapper.listIdleCommodity(parameterMap);
    }

    /**
     * 统计闲置的商品中通过审核或者正在回收站的商品的数量
     *
     * @param goodsType
     * @return
     */
    public Integer countIdleCommodity(int goodsType) {
        return idleMapper.countIdleCommodity(goodsType);
    }

    /**
     * 获取在指定时间内不同分类的闲置商品的数量
     * @param parameterMap
     * @return
     */
    public Integer countReleaseClassifyIdleNumber(Map<String,Integer> parameterMap){
        return idleMapper.countReleaseClassifyIdleNumber(parameterMap);
    }
}
