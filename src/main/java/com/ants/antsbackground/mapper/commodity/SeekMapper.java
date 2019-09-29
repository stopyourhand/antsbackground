package com.ants.antsbackground.mapper.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 处理寻求的数据库层
 *
 * @Author czd
 * @Date:created in 2019/9/24
 * @Version: V1.0
 */
@Mapper
public interface SeekMapper {

    /**
     * 获取在已经通过审核的寻求的商品名称,价格,发布类型以及所属卖家信息的列表
     *
     * @param parameterMap
     * @return
     */
    List<AuditDTO> listAuditedSeekGoods(Map<String, Integer> parameterMap);

    /**
     * 统计审核通过的寻求的商品的数量
     *
     * @return
     */
    Integer countAuditedSeekGoods();

    /**
     * 获取在指定时间内(七天)发布寻求的数量
     *
     * @return
     */
    Integer countReleaseSeekNumber(Map<String, String> parameterMap);
}
