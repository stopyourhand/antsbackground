package com.ants.antsbackground.entity.commodity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 对应着ants_goods_base这张数据库表
 * 闲置物品
 * @Author czd
 * @Date:created in 2019/10/11
 * @Version: V1.0

 */
@Component
@Data
public class IdleGoods {
    /** 商品id */
    private int goodsId;
    /** 商品名称 */
    private String goodsName;
    /** 商品描述 */
    private String goodsDescribe;
    /** 商品图片名称 */
    private String goodsPicture;
    /** 商品视频名称 */
    private String goodsVideo;
    /** 视频所属小分类id */
    private int goodsClass;
    /** 商品价格 */
    private double goodsPrice;
    /** 商品交易状态：0 -> 未交易 1 -> 交易中 */
    private int goodsState;
    /** 商品是否议价：0 -> 可议价 1 -> 不可议价 */
    private int goodsBargin;
    /** 商品所属卖家id */
    private int goodsBelong;
    /** 送货形式：0 -> 卖家送货上门  1 -> 买家上门自取 2 -> 可待商量 */
    private int goodsWay;
    /** 商品发布时间  */
    private String uploadTime;
    /**  商品库存 */
    private int repertory;
}
