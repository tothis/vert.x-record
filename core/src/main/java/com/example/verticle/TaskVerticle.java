package com.example.verticle;

import io.vertx.core.AbstractVerticle;

public class TaskVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 一次性操作 返回唯一计时器id 该id可用于取消该计时器
        long timerID1 = vertx.setTimer(1000, id -> {
            System.out.println("one time");
        });

        // 周期性操作 延时2秒启动 每隔2秒执行
        long timerID2 = vertx.setPeriodic(2000, id -> {
            System.out.println("repeat mession");
        });
        // 取消计时器或者Verticle中撤销 定时器会自动被清除
        // vertx.cancelTimer(timerID2);
    }
}