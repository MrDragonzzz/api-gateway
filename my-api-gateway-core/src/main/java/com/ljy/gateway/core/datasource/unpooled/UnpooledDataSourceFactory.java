package com.ljy.gateway.core.datasource.unpooled;

import com.ljy.gateway.core.datasource.DataSource;
import com.ljy.gateway.core.session.Configuration;
import com.ljy.gateway.core.datasource.DataSourceFactory;
import com.ljy.gateway.core.datasource.DataSourceType;

/**
 * @description:
 * @author: 龙嘉翼
 * @Date: 2023/5/7
 */
public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        this.dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        this.dataSource.setConfiguration(configuration);
        this.dataSource.setDataSourceType(DataSourceType.Dubbo);
        this.dataSource.setHttpStatement(configuration.getHttpStatement(uri));

    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
