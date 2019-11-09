package com.example;

import com.example.verticle.TestVerticle;
import io.vertx.core.AbstractVerticle;

/**
 * 打成jar包方式启动 需要配置文件配置主类用shadow插件
 */
public class MainVerticle extends AbstractVerticle {
    @Override
    public void start() {
        vertx.deployVerticle(TestVerticle.class.getName());
    }
}