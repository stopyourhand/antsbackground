package com.ants.antsbackground.service.sell;

import com.ants.antsbackground.entity.sell.Sell;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理交易完成的service层
 *
 * @Author czd
 * @Date:createed in 2019/10/24
 * @Version: V1.0
 */
@Service
public interface SellService {
    /**
     * 获取交易分析的列出不同时间段的交易完成的商品的信息
     * @param parameterMap
     * @return
     */
    List<Sell> listSellAnalysis(Map<String,String> parameterMap);

    /**
     * 获取在指定时间内(七天)不同类型商品的交易数量
     * @param parameterMap
     * @return
     */
    Integer countSellNumberByCommodity(Map parameterMap);

    /**
     * 获取所有交易完成的商品的数量
     * @return
     */
    Integer countAllSellGoodsNumber();

    /**
     * 获取在指定时间内不同分类的交易完成的商品的数量
     * @param parameterMap
     * @return
     */
    Integer countReleaseClassifySellNumber(Map<String,Integer> parameterMap);


}
