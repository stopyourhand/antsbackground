package com.ants.antsbackground.impl.sell;

import com.ants.antsbackground.mapper.sell.SellMapper;
import com.ants.antsbackground.service.sell.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
