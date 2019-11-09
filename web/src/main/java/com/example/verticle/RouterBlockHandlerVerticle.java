package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

import java.util.concurrent.TimeUnit;

public class RouterBlockHandlerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        // vert.x会使用 Worker Pool 中的线程而不是 Event Loop 线程来处理阻塞请求
        router.route("/block").blockingHandler(routingContext -> { // 默认在一个Verticle实例中按顺序执行
            // 执行某些同步的耗时操作
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            routingContext.response().end("block handler");
        });
    }
}