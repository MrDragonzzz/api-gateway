package com.ljy.gateway.center.application;


import com.ljy.gateway.center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import com.ljy.gateway.center.domain.manage.model.vo.GatewayServerVO;


import java.util.List;

/**
 * @author 小傅哥，微信：fustack
 * @description 网关配置服务
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
public interface IConfigManageService {

    List<GatewayServerVO> queryGatewayServerList();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress);

    ApplicationSystemRichInfo queryApplicationSystemRichInfo(String gatewayId,String systemId);


    String queryGatewayDistribution(String systemId);

    void distributionGatewayServerNode(String groupId, String gatewayId, String systemId);
}
