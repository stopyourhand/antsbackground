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
public class LeaseGoods {
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
    /** 商品所属小分类id */
    private int goodsClass;
    /** 商品价格 */
    private double goodsPrice;
    /** 商品交易状态：0 -> 未租赁 1 -> 租赁中 */
    private int goodsState;
    /** 商品是否议价：0 -> 可议价  1 -> 不可议价*/
    private int goodsBargin;
    /** 商品所属卖家id */
    private int goodsBelong;
    /** 商送货形式：0 -> 卖家送货上门  1 -> 买家上门自取 2：可待商量 */
    private int goodsWay;
    /** 商品发布时间 */
    private String uploadTime;
    /** 商品库存 */
    private int repertory;
}
