package com.ants.antsbackground.impl.commodity;

import com.ants.antsbackground.dto.AuditDTO;
import com.ants.antsbackground.mapper.commodity.LeaseMapper;
import com.ants.antsbackground.service.commodity.IdleService;
import com.ants.antsbackground.service.commodity.LeaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 处理租赁的service实现类
 *
 * @Author czd
 * @Date:createed in 2019/10/11
 * @Version: V1.0
 */
@Service("LeaseService")
public class LeaseServiceImpl implements LeaseService {
    @Autowired
    private LeaseMapper leaseMapper;

    /**
     * 获取在指定时间内(七天)发布租赁的数量
     * @return
     */
    public Integer countReleaseLeaseNumber(Map<String,String> parameterMap){
        return leaseMapper.countReleaseLeaseNumber(parameterMap);
    }

    /**
     * 获取在已经通过审核的闲置的商品名称,价格,发布类型以及所属卖家信息的列表
     * @param parameterMap
     * @return
     */
    public List<AuditDTO> listAuditedLeaseGoods(Map<String,Integer> parameterMap){
        return leaseMapper.listAuditedLeaseGoods(parameterMap);
    }

    /**
     * 统计审核通过的闲置的商品的数量
     * @return
     */
    public Integer countAuditedLeaseGoods(){
        return leaseMapper.countAuditedLeaseGoods();
    }


}
