package com.ants.antsbackground.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 审核的数据参数对象
 * @Author czd
 * @Date:createed in
 * @Version: V1.0
 */
@Data
public class AuditDTO {
    /** 商品主键 */
    private Integer goodsId;
    /** 商品名称 */
    private String goodsName;
    /** 商品价格 */
    private Double goodsPrice;
    /** 商品审核类型 0:闲置 1:赠送 2:租赁 3:寻求 */
    private Integer goodsType;
    /** 商品所属的卖家名称 */
    private String userName;
}
