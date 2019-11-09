package com.example.verticle;

import io.vertx.core.AbstractVerticle;

import java.util.concurrent.TimeUnit;

public class TestVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 获取部署时候传递的参数 通过config方法访问
        System.out.println(config().getString("one") + config().getString("two"));
        vertx.createHttpServer().requestHandler(req -> { // 请求处理函数
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            req.response().putHeader("content-type", "text/plain").end("vert.x output");
        }).listen(8080); //监听端口
    }
}