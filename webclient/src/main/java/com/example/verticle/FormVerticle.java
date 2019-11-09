package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;

public class FormVerticle extends AbstractVerticle {
    @Override
    public void start() {
        WebClient client = WebClient.create(vertx);
        // Form表单请求
        MultiMap form = MultiMap.caseInsensitiveMultiMap();
        form.set("firstName", "li");
        form.set("lastName", "lei");
        // 用URL编码方式提交表单
        // 缺省情况下 提交表单的请求头中的content-type属性值为application/x-www-form-urlencoded亦可将其设置为multipart/form-data
        client.post(8080, "localhost:8080", "/form")
                .putHeader("content-type", "multipart/form-data")
                .timeout(5000) // 设置超时
                // .as(BodyCodec.jsonObject()) // 返回响应为Json
                .sendForm(form, ar -> { // Ok
                    if (ar.succeeded()) {
                        // 默认响应式Buffer 可使用编解码器换成Json
                        HttpResponse<Buffer> response = ar.result();
                        // HttpResponse<JsonObject> response = ar.result(); // 使用了编解码器
                        System.out.println(response.statusCode());
                    } else {
                        // 超时处理
                        System.out.println(ar.cause().getMessage());
                    }
                });
    }
}