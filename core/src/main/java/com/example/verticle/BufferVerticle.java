package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;

/**
 * 在vert.x内部 大部分数据被重新组织shuffle 意为洗牌成Buffer格式
 * Buffer是可以读取或写入的0个或多个字节序列 并且可以自动扩容
 */
public class BufferVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 创建Buffer
        Buffer buffer = Buffer.buffer(); // 创建空
        Buffer.buffer("hello world", "UTF-8"); // 默认是UTF-8编码
        Buffer.buffer(new byte[]{1, 3, 5}); // 根据字节数组创建
        Buffer.buffer(50000); // 创建指定大小 比写入重新调整效率更高
        // 写-追加
        buffer.appendInt(9527).appendString("李磊");
        // 写-随机set(根据索引指定开始写入位置)
        buffer.setInt(5, 123456);
        // 读
        for (int i = 0; i < buffer.length(); i++) {
            System.out.println("int value at " + i + " is " + buffer.getInt(i));
        }
        // 操作
        buffer.length();
        buffer.copy(); // 拷贝
    }
}