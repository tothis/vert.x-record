package com.example;

import com.example.verticle.DBVerticle;
import io.vertx.core.Vertx;

/**
 * main 方法方式运行
 */
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

    // mysql
    private static void deploy1(Vertx vertx) {
        vertx.deployVerticle(DBVerticle.class.getName());
    }
}