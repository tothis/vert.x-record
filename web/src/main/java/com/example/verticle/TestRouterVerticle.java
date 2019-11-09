package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class TestRouterVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router mainRouter = Router.router(vertx); // 主路由
        vertx.createHttpServer().requestHandler(mainRouter::accept).listen(8080);
        // 子路由
        Router get = Router.router(vertx);
        get.get("/test").handler(rc -> rc.response().end("GetRouter"));
        Router post = Router.router(vertx);
        post.post("/test").handler(rc -> {
            rc.request().handler(buffer -> {
                System.out.println(buffer.toString());
                System.out.println(buffer.toJson().toString());
            });

            rc.response().end("PostRouter");
        });
        // 绑定子路由
        mainRouter.mountSubRouter("/get", get);
        mainRouter.mountSubRouter("/post", post);
        // 访问路径'/get/test'带主路由绑定前缀
    }
}