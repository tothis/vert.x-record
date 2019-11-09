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
        client.get(8080, "localhost", "/sub01/test")
                .addQueryParam("param", "param_value") //添加参数
                .setQueryParam("param", "new_param") //覆盖之前参数
                .send(ar -> {
                    if (ar.succeeded()) {
                        // 获取响应
                        HttpResponse<Buffer> response = ar.result();
                        System.out.println("Received response with status code" + response.statusCode());
                    } else {
                        System.out.println("Something went wrong " + ar.cause().getMessage());
                    }
                });
    }
}