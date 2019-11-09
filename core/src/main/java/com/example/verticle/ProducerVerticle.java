package com.example.verticle;

import com.example.config.CustomizeMessageCodec;
import com.example.pojo.OrderMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;

public class ProducerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        EventBus eventBus = vertx.eventBus();
        // 发布消息(群发)
        eventBus.publish("com.example", "群发祝福!");
        // 发送消息(单发) 只会发送注册此地址的一个 采用不严格的轮询算法选择
        DeliveryOptions options = new DeliveryOptions(); // 设置消息头等
        options.addHeader("some-header", "some-value");
        eventBus.send("com.example", "单发消息", options);
        // 发送自定义对象 需要编解码器
        eventBus.registerCodec(CustomizeMessageCodec.create()); // 注册编码器
        DeliveryOptions options1 = new DeliveryOptions().setCodecName("myCodec"); // 必须指定名字
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setName("李磊");
        eventBus.send("com.example", orderMessage, options1);
    }
}