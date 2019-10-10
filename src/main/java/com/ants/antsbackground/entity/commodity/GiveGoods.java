package com.ants.antsbackground.entity.commodity;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 对应着ants_goods_give这张数据库表
 * 赠送物品
 * @Author czd
 * @Date:created in 2019/10/11
 * @Version: V1.0

 */
@Component
@Data
public class GiveGoods {
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
    /** 商品对应的子类的id */
    private int goodsClass;
    /** 商品的价格 */
    private double goodsPrice;
    /** 商品交易状态：0 -> 未赠送 1-> 赠送 */
    private int goodsState;
    /** 商品是否议价：0 -> 可议价 1 -> 不可议价 */
    private int goodsBargin;
    /** 商品所属卖家主键id */
    private int goodsBelong;
    /** 送货形式：0 -> 买家上门自取 1 -> 可待商量*/
    private int goodsWay;
    /** 商品上传时间 */
    private String uploadTime;
    /** 商品库存 */
    private int repertory;

}
