package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;

public class RouterVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // Router接收HTTP请求 并查找首个匹配该请求的Route 并将请求传递给此Route
        Router router = Router.router(vertx);
        // Route 可以持有一个与之关联的处理器用于接收请求
        router.route().handler(routingContext -> { // 创建一个匹配任何地址的route
            /**
             * 调用处理器的参数是一个RoutingContext对象
             * 它不仅包含了vert.x中标准的HttpServerRequest和HttpServerResponse
             * 还包含了各种用于简化vert.x Web使用的东西
             * 每一个被路由的请求对应一个唯一的RoutingContext
             */
            // 所有的请求都会调用这个处理器处理
            HttpServerResponse response = routingContext.response();
            response.putHeader("content-type", "text/plain");
            // 写入响应并结束处理
            response.end("Hello World from Vert.x-Web!");
        });
        // 设置http服务通过router处理请求
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
    }
}