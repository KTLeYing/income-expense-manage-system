package com.mzl.incomeexpensemanagesystem.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName :   HelloServer
 * @Description: 服务端
 * @Author: v_ktlema
 * @CreateDate: 2022/4/18 18:31
 * @Version: 1.0
 */
@Slf4j
public class HelloServer {

    //服务端端口号
    private int port = 50052;
    private Server server;

    private void start() throws IOException {
        server = ServerBuilder.forPort(port)
                .addService(new HelloServiceImpl())
                .build()
                .start();
        log.info("gRPC示例====>" + "gRPC服务启动成功...端口号: " + port);

        Runtime.getRuntime().addShutdownHook(new Thread(){

            @Override
            public void run(){
               HelloServer.this.stop();
                log.info("gRPC示例====>" + "gRPC服务关闭...");
            }
        });
    }

    /**
     * 服务关闭
     */
    private void stop(){
        if (server != null){
            server.shutdown();
        }
    }

    /**
     * block 一直到退出程序
     * @throws InterruptedException
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null){
            server.awaitTermination();
        }
    }


    public  static  void main(String[] args) throws IOException, InterruptedException {
        HelloServer server = new HelloServer();
        //启动服务端
        server.start();
        server.blockUntilShutdown();
    }

}
