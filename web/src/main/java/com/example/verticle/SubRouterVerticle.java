package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;

public class SubRouterVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router mainRouter = Router.router(vertx); // 主路由
        vertx.createHttpServer().requestHandler(mainRouter::accept).listen(8080);
        // 子路由
        Router sub01 = Router.router(vertx);
        sub01.get("/test").handler(rc -> rc.response().end("sub01Router"));
        Router sub02 = Router.router(vertx);
        sub02.post("/test").handler(rc -> {
            rc.request().handler(buffer -> {
                System.out.println(buffer.toString());
                System.out.println(buffer.toJson().toString());
            });

            rc.response().end("sub02Router");
        });
        // 绑定子路由
        mainRouter.mountSubRouter("/sub01", sub01);
        mainRouter.mountSubRouter("/sub02", sub02);
        // 项目访问路径 /sub01/test01 带主路由绑定前缀
    }
}
