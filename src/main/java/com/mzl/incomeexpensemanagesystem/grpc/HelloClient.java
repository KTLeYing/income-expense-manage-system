package com.mzl.incomeexpensemanagesystem.grpc;

import com.mzl.incomeexpensemanagesystem.utils.ProtoUtil;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.examples.hello.ConRequest;
import io.grpc.examples.hello.ConResponse;
import io.grpc.examples.hello.sayHelloGrpc;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName :   HelloClient
 * @Description: 客户端
 * @Author: v_ktlema
 * @CreateDate: 2022/4/18 18:31
 * @Version: 1.0
 */
@Slf4j
public class HelloClient {

    private final ManagedChannel channel;
    private final sayHelloGrpc.sayHelloBlockingStub blockingStub;

    /**
     * grpc客户端和服务端创建连接
     * @param host
     * @param port
     */
    public HelloClient(String host,int port){
        channel = ManagedChannelBuilder.forAddress(host,port)
                .usePlaintext(true)
                .build();
        blockingStub = sayHelloGrpc.newBlockingStub(channel);
    }

    /**
     * 关闭连接
     * @throws InterruptedException
     */
    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /**
     * 请求和响应具体逻辑
     * @param url
     */
    public ConResponse request(String url){
        //创建请求体
        ConRequest request = ConRequest.newBuilder().setName(url).build();
        ConResponse response = null;
        try{
            response = blockingStub.sayHello(request);
            log.info("gRPC示例====>" + "gRPC请求成功...服务端响应结果: " + ProtoUtil.toStr(response));
        } catch (StatusRuntimeException e) {
            log.info("gRPC示例====>" + "gRPC请求失败:" + e.getStatus());
        }
        return response;
    }

    public static void main(String[] args) throws InterruptedException {
        HelloClient client = new HelloClient("localhost",50052);
        try{
            String url = "gRPC-Client-MaZhenLe";
            client.request(url);
        }finally {
            client.shutdown();
        }
    }

}
