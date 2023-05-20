package com.ljy.gateway.rpc;


import com.ljy.gateway.rpc.dto.XReq;

public interface IActivityBooth {

    String sayHi(String str);

    String insert(XReq req);

    String test(String str, XReq req);

}
