package com.ljy.gateway.core.datasource;

import com.ljy.gateway.core.session.Configuration;

/**
 * @description:数据源工厂
 * @author: 龙嘉翼
 * @Date: 2023/5/7
 */
public interface DataSourceFactory {
    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();
}
