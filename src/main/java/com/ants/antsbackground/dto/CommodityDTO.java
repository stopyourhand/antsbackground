package com.ants.antsbackground.dto;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class CommodityDTO {
    /** 主键 */
    private Integer id;
    /** 卖家用户名称 */
    private String userName;
    /** 商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求 */
    private Integer goodsType;
    /** 商品名称 */
    private String name;
    /** 商品价格 */
    private Double price;
}
