package com.ljy.gateway.center.infrastructure.dao;

import com.ljy.gateway.center.domain.manage.model.vo.GatewayDistributionVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 小傅哥，微信：fustack
 * @description 网关分配
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@Mapper
public interface IGatewayDistributionDao {

    List<String> queryGatewayDistributionSystemIdList(String gatewayId);
    String queryGatewayDistribution(String systemId);

    void insert(GatewayDistributionVO gatewayDistribution);
}
