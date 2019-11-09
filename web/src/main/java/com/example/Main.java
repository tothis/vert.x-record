package com.example;

import com.example.verticle.*;
import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(); // 获取实例
        //deploy1(vertx);
        deploy2(vertx);
        //deploy3(vertx);
        //deploy4(vertx);
        //deploy5(vertx);
        //deploy6(vertx);
    }

    //cookie 与 session 处理
    private static void deploy3(Vertx vertx) {
        //vertx.deployVerticle(CookieVerticle.class.getName());
        vertx.deployVerticle(SessionVeerticle.class.getName());
    }

    // 子路由
    private static void deploy2(Vertx vertx) {
        vertx.deployVerticle(TestRouterVerticle.class.getName());
    }

    //router
    private static void deploy1(Vertx vertx) {
        //vertx.deployVerticle(RouterVerticle.class.getName());
//        vertx.deployVerticle(RouterManyHandlerVerticle.class.getName());
//        vertx.deployVerticle(RouterBlockHandlerVerticle.class.getName());
        vertx.deployVerticle(RouterPathVerticle.class.getName());
    }
}