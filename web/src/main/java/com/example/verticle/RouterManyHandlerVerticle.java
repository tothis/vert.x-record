package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class RouterManyHandlerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 一个请求可以绑定多个处理器
        Router router = Router.router(vertx);
        router.route("/test").handler(routingContext -> {
            // 由于我们会在不同的处理器里写入响应 因此需要启用分块传输 且仅当需要通过多个处理器输出响应时才需要
            routingContext.response().setChunked(true).write("route1\n");
            // 2秒后调用下一个处理器
            routingContext.vertx().setTimer(2000, tid -> routingContext.next());
        });
        router.route("/test").handler(routingContext -> {
            routingContext.response().write("route2\n");
            // 2秒后调用下一个处理器
            routingContext.vertx().setTimer(2000, tid -> routingContext.next());
        });
        router.route("/test").handler(routingContext -> {
            routingContext.response().write("route3");
            routingContext.response().end(); // 结束响应 等待全部结束相应 才会返回response
        });
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}