package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class GetVerticle extends AbstractVerticle {
    @Override
    public void start() {
        WebClient client = WebClient.create(vertx);
        // 发送GET请求
        client.get(8080, "localhost", "/get/test")
                .addQueryParam("param", "paramValue") // 添加参数
                .setQueryParam("param", "newParam") // 会覆盖之前key相同的参数
                .send(ar -> {
                    if (ar.succeeded()) {
                        // 获取响应
                        HttpResponse<Buffer> response = ar.result();
                        System.out.println(response.statusCode());
                    } else {
                        System.out.println(ar.cause().getMessage());
                    }
                });
    }
}