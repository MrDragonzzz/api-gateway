package com.ljy.gateway.center.application;

import com.ljy.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.ljy.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.ljy.gateway.center.domain.register.model.vo.ApplicationSystemVO;

/**
 * @description:接口注册服务
 * @author: 龙嘉翼
 * @Date: 2023/5/13
 */
public interface IRegisterManageService {
    void registerApplication(ApplicationSystemVO applicationSystemVO);

    void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO);

    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO);

}
