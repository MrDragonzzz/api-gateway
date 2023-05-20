package com.ljy.gateway.core.session.defaults;

import com.ljy.gateway.core.datasource.DataSource;
import com.ljy.gateway.core.datasource.DataSourceFactory;
import com.ljy.gateway.core.datasource.unpooled.UnpooledDataSourceFactory;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.executor.Executor;
import com.ljy.gateway.core.session.GatewaySession;
import com.ljy.gateway.core.session.GatewaySessionFactory;

/**
 * @description:默认网关会话工厂
 * @author: 龙嘉翼
 * @Date: 2023/5/6
 */
public class DefaultGatewaySessionFactory implements GatewaySessionFactory {
    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public GatewaySession openSession(String uri) {
        // 获取数据源连接信息：这里把 Dubbo、HTTP 抽象为一种连接资源
        DataSourceFactory dataSourceFactory=new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration,uri);
        DataSource dataSource = dataSourceFactory.getDataSource();
        // 创建执行器
        Executor executor = configuration.newExecutor(dataSource.getConnection());
        // 创建会话：DefaultGatewaySession
        return new DefaultGatewaySession(configuration,uri,executor);

    }
}
