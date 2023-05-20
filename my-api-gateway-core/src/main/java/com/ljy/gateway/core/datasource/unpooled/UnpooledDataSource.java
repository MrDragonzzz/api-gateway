package com.ljy.gateway.core.datasource.unpooled;

import com.ljy.gateway.core.datasource.Connection;
import com.ljy.gateway.core.datasource.DataSource;
import com.ljy.gateway.core.datasource.connection.DubboConnection;
import com.ljy.gateway.core.mapping.HttpStatement;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.datasource.DataSourceType;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @description:无池化的连接池
 * @author: 龙嘉翼
 * @Date: 2023/5/7
 */
public class UnpooledDataSource implements DataSource {

    private Configuration configuration;
    private HttpStatement httpStatement;
    private DataSourceType dataSourceType;
    @Override
    public Connection getConnection() {
        switch (dataSourceType){
            case HTTP:
                // TODO 预留接口，暂时不需要实现
                break;
            case Dubbo:
                //配置信息
                String application = httpStatement.getApplication();
                String interfaceName = httpStatement.getInterfaceName();
                // 获取服务
                ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
                RegistryConfig registryConfig = configuration.getRegistryConfig(application);
                ReferenceConfig<GenericService> reference = configuration.getReferenceConfig(interfaceName);
                return new DubboConnection(applicationConfig, registryConfig, reference);
            default:
                break;
        }
        throw new RuntimeException("DataSourceType：" + dataSourceType + "没有对应的数据源实现");
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setHttpStatement(HttpStatement httpStatement) {
        this.httpStatement = httpStatement;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

}


