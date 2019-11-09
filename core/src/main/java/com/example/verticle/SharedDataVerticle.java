package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.shareddata.*;

/**
 * 共享数据SharedData包含的功能允许安全地在应用程序的不同部分之间 同一vert.x实例中不同应用程序之间或集群中的不同vert.x实例之间安全地共享数据
 * 共享数据包括
 * 1.本地共享Map
 * 2.分布式
 * 3.集群范围Map
 * 4.异步集群范围锁和异步集群范围计数器
 */
public class SharedDataVerticle extends AbstractVerticle {

    private static final String MAP = "map";
    private static final String LOCK = "lock";
    private static final String COUNT = "count";

    @Override
    public void start() {
        SharedData sd = vertx.sharedData();
        // 本地共享map
        LocalMap<String, String> map1 = sd.getLocalMap("map1");
        // LocalMap kv只允许不可变值或者Buffer
        map1.put("one", "one");
        LocalMap<String, Buffer> map2 = sd.getLocalMap("map2");
        // Buffer将会在添加到Map之前拷贝
        Buffer buffer = Buffer.buffer().appendString("two");
        map2.put("two", buffer);
        // 获取集群map
        sd.<String, String>getClusterWideMap(MAP, res -> {
            // 集群api都会有通知处理方法
            if (res.succeeded()) {
                AsyncMap<String, String> map = res.result();
                map.put("one", "onw", resPut -> { // 添加数据通知
                    if (resPut.succeeded()) {
                        // 成功放入值
                    } else { // 出现问题
                    }
                });
            } else {
                // 出现问题
            }
        });
        // 使用集群锁
        sd.getLockWithTimeout(LOCK, 10000, res -> {
            if (res.succeeded()) {
                // 获得锁
                Lock lock = res.result();
                // 5秒后我们释放该锁其他人可以得到它
                vertx.setTimer(5000, tid -> lock.release());
            } else {
                // 出现问题
            }
        });
        // 集群原子计数器
        sd.getCounter(COUNT, res -> {
            if (res.succeeded()) {
                Counter counter = res.result();
                counter.addAndGet(1);
            } else {
                // 出现问题
            }
        });
    }
}