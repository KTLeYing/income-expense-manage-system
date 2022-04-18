package com.mzl.incomeexpensemanagesystem.grpc;

import io.grpc.examples.hello.ConRequest;
import io.grpc.examples.hello.ConResponse;
import io.grpc.examples.hello.sayHelloGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;

/**
 * @ClassName :   HelloServiceImpl
 * @Description: 服务端实现类
 * @Author: v_ktlema
 * @CreateDate: 2022/4/18 18:32
 * @Version: 1.0
 */
@Slf4j
public class HelloServiceImpl extends sayHelloGrpc.sayHelloImplBase{

    @Override
    public void sayHello(ConRequest request, StreamObserver<ConResponse> responseObserver) {
        String res = "My name is gRPC-Server. And your name is: " + request.getName();
        ConResponse response = ConResponse.newBuilder().setRes(res).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
        log.info("gRPC示例====>" + "gRPC客户端的消息：" + request.getName());
    }
}
