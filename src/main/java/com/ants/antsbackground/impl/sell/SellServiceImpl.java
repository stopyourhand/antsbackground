package com.ants.antsbackground.impl.sell;

import com.ants.antsbackground.entity.sell.Sell;
import com.ants.antsbackground.mapper.sell.SellMapper;
import com.ants.antsbackground.service.sell.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理交易完成的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("SellService")
public class SellServiceImpl implements SellService {
    @Autowired
    private SellMapper sellMapper;

    /**
     * 获取交易分析的列出不同时间段的交易完成的商品的信息
     * @param parameterMap
     * @return
     */
    public List<Sell> listSellAnalysis(Map<String,String> parameterMap){
        return sellMapper.listSellAnalysis(parameterMap);
    }

    /**
     * 获取在指定时间内(七天)不同类型商品的交易数量
     *
     * @param parameterMap
     * @return
     */
    public Integer countSellNumberByCommodity(Map parameterMap) {
        return sellMapper.countSellNumberByCommodity(parameterMap);
    }

    /**
     * 获取所有交易完成的商品的数量
     * @return
     */
    public Integer countAllSellGoodsNumber(){
        return sellMapper.countAllSellGoodsNumber();
    }

    /**
     * 获取在指定时间内不同分类的交易完成的商品的数量
     * @param parameterMap
     * @return
     */
    public Integer countReleaseClassifySellNumber(Map<String,Integer> parameterMap){
        return sellMapper.countReleaseClassifySellNumber(parameterMap);
    }
}
