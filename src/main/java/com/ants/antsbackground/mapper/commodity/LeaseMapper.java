package com.ants.antsbackground.mapper.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理租赁的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface LeaseMapper {
    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    Integer countReleaseLeaseNumber(Map<String,String> parameterMap);

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedLeaseGoods(Map<String,Integer> parameterMap);

    /**
     * 统计审核通过的闲置的商品的数量
     * @return
     */
    Integer countAuditedLeaseGoods();

}
