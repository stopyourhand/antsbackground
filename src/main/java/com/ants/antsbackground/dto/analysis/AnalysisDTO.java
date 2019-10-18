package com.ants.antsbackground.dto.analysis;

import lombok.Data;

/**
 * @Author czd
 * @Date:createed in 2019/10/8
 * @Version: V1.0
 */
@Data
public class AnalysisDTO {
    /** 商品类型 */
    private String classify;
    /** 发布物品的名称 */
    private String name;
    /** 发布的商品类型(闲置，寻求，租赁，赠送) */
    private Integer goodsState;
    /** 发布的金额 */
    private Double price;
}
