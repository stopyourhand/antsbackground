package com.ants.antsbackground.entity.commodity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 对应着ants_goods_lease这张数据库表
 * 租赁物品
 * @Author czd
 * @Date:created in 2019/10/11
 * @Version: V1.0

 */
@Component
@Data
public class SeekGoods {
    /** 商品id */
    private int goodsId;
    /** 商品名称 */
    private String goodsName;
    /** 商品描述 */
    private String goodsDescribe;
    /** 商品图片 */
    private String goodsPicture;
    /** 商品所属的子类的id */
    private int goodsClass;
    /** 商品价格 */
    private double goodsPrice;
    /** 商品交易状态：0 -> 寻求中 1 -> 已寻求 */
    private int goodsState;
    /** 商品是否议价：0 -> 可议价 1 -> 不可议价*/
    private int  goodsBargin;
    /** 商品所属卖家id */
    private int goodsBelong;
    /** 送货形式：0 -> 卖家送货上门  1 -> 买家上门自取 2 -> 可待商量 */
    private int goodsWay;
    /** 商品发布寻求时间 */
    private String uploadTime;
    /** 商品期望寻求的商品的数目 */
    private int repertory;

}
