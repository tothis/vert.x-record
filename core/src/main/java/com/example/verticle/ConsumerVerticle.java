package com.example.verticle;

import com.example.pojo.OrderMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class ConsumerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 每个Vertx实例默认是单例
        EventBus eb = vertx.eventBus();
        // 注册处理器 消费com.example发送的消息
        MessageConsumer<Object> consumer = eb.consumer("com.example"); // 订阅地址
        consumer.handler(message -> { // 消息处理器
            if (message.body() instanceof OrderMessage) {
                System.out.println("接受对象 : " + ((OrderMessage) message.body()).getName());
            }
            System.out.println("普通消费者 : " + message.body());
            message.reply("收到"); // 回复生产者 send才能接受
        }).completionHandler(res -> { // 注册完成后通知事件 适用于集群中比较慢的情况下
            System.out.println("注册处理器结果" + res.succeeded());
        });
        // 撤销处理器
        // consumer.unregister();
    }
}