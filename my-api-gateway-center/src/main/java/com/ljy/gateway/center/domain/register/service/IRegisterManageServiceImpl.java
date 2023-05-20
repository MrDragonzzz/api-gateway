package com.ljy.gateway.center.domain.register.service;

import com.ljy.gateway.center.application.IRegisterManageService;
import com.ljy.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.ljy.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.ljy.gateway.center.domain.register.model.vo.ApplicationSystemVO;
import com.ljy.gateway.center.domain.register.repository.IRegisterManageRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @description:接口注册服务
 * @author: 龙嘉翼
 * @Date: 2023/5/13
 */
@Service
public class IRegisterManageServiceImpl implements IRegisterManageService {
    @Resource
    private IRegisterManageRepository registerManageRepository;


    @Override
    public void registerApplication(ApplicationSystemVO applicationSystemVO) {
        registerManageRepository.registerApplication(applicationSystemVO);

    }

    @Override
    public void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO) {
        registerManageRepository.registerApplicationInterface(applicationInterfaceVO);

    }

    @Override
    public void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO) {
        registerManageRepository.registerApplicationInterfaceMethod(applicationInterfaceMethodVO);

    }
}
