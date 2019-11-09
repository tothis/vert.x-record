package com.example;

import com.example.verticle.PostVerticle;
import io.vertx.core.Vertx;

public class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(); // 获取实例
        deploy1(vertx);
        // deploy2(vertx);
        // deploy3(vertx);
        // deploy4(vertx);
        // deploy5(vertx);
        // deploy6(vertx);
    }

    // get请求
    private static void deploy1(Vertx vertx) {
        // vertx.deployVerticle(GetVerticle.class.getName());
        vertx.deployVerticle(PostVerticle.class.getName());
    }
}