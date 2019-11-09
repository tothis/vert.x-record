package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

public class RouterPathVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        router.route(HttpMethod.POST, "/pre/*"); // 匹配post请求 /pre/前缀的路径
        router.route().pathRegex(".*foo"); //匹配正则路径
        // 可直接使用get方法创建route 且method匹配多种类型
        router.get("/some/path/").method(HttpMethod.POST).handler(routingContext -> {
        });
        // 获取路径中的参数
        router.route("/params/:name/:id").handler(routingContext -> {
            System.out.println(routingContext.request().getParam("name"));
            System.out.println(routingContext.request().getParam("id"));
            routingContext.response().end("params");
        });
        // 基于请求类型匹配 可以匹配多个精确的值MIME类型
        router.route().consumes("text/html").consumes("text/plain").handler(routingContext -> {
            // 所有content-type消息头的值为text/html或text/plain的请求会调用这个处理器
        });

        router.get("/some/path/B").handler(routingContext -> {
            routingContext.response().end();
        });

        router.get("/some/path").handler(routingContext -> {
            routingContext.reroute("/some/path/B"); // 转发 重新执行上一个处理器
        });

    }
}