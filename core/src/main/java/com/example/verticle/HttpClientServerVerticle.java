package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;

public class HttpClientServerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 配置
        HttpClientOptions options = new HttpClientOptions()
                .setDefaultHost("127.0.0.1")
                .setDefaultPort(8080);
        // 创建客户端
        HttpClient client = vertx.createHttpClient(options);
        client.getNow("/user", response -> { //发送请求
            System.out.println("获取接口返回数据 : " + response.result().bodyHandler(buffer -> {
                System.out.println(buffer.toString());
            }));
        });
        // 发送带请求体post
        client.post("/user", response -> {
            System.out.println("获取接口返回数据 : " + response.result().bodyHandler(buffer -> {
                System.out.println(buffer.toString());
            }));
        }).putHeader("content-type", "text/plain").end("请求参数"); // 非now的方法end才会真正请求
    }
}