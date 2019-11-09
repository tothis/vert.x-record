package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.WebClient;

public class PostVerticle extends AbstractVerticle {
    @Override
    public void start() {
        WebClient client = WebClient.create(vertx);
        // post请求
        client.post(8080, "localhost", "/sub02/test")
                .sendBuffer(Buffer.buffer("name"), ar -> { // 填充Buffer请求体
                    if (ar.succeeded()) System.out.println(ar.result().bodyAsString());
                });
        client.post(8080, "localhost", "/sub02/test")
                .sendJsonObject(new JsonObject() // 发送json请求体参数
                        .put("firstName", "Dale")
                        .put("lastName", "Cooper"), ar -> {
                    if (ar.succeeded()) System.out.println(ar.result().bodyAsString());
                });
    }
}