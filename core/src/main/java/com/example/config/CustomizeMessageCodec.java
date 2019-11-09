package com.example.config;

import com.example.pojo.OrderMessage;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.eventbus.MessageCodec;

import java.io.*;

/**
 * 自定义对象编解码器 两个类型可用于消息转换 即发送对象转换为接受需要的对象
 */
public class CustomizeMessageCodec implements MessageCodec<OrderMessage, OrderMessage> {
    /**
     * 将消息实体封装到Buffer用于传输
     * 实现方式 使用对象流从对象中获取Byte数组然后追加到Buffer
     */
    @Override
    public void encodeToWire(Buffer buffer, OrderMessage orderMessage) {
        final ByteArrayOutputStream b = new ByteArrayOutputStream();
        try (ObjectOutputStream o = new ObjectOutputStream(b)) {
            o.writeObject(orderMessage);
            o.close();
            buffer.appendBytes(b.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从Buffer中获取消息对象
    @Override
    public OrderMessage decodeFromWire(int pos, Buffer buffer) {
        final ByteArrayInputStream b = new ByteArrayInputStream(buffer.getBytes());
        OrderMessage msg = null;
        try (ObjectInputStream o = new ObjectInputStream(b)) {
            msg = (OrderMessage) o.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    // 消息转换
    @Override
    public OrderMessage transform(OrderMessage orderMessage) {
        System.out.println("消息转换---"); // 可对接受消息进行转换 比如转换成另一个对象等
        orderMessage.setName("姚振");
        return orderMessage;
    }

    @Override
    public String name() {
        return "myCodec";
    }

    // 识别是否是用户自定义编解码器 通常为-1
    @Override
    public byte systemCodecID() {
        return -1;
    }

    public static MessageCodec create() {
        return new CustomizeMessageCodec();
    }
}