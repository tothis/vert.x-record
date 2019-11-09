package com.example;

import com.example.verticle.*;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.WorkerExecutor;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.shareddata.LocalMap;
import io.vertx.core.shareddata.SharedData;

import java.util.concurrent.TimeUnit;

/**
 * main 方法方式运行
 */
public class Main {

    private static final String WORKERPOOL = "worker-pool";

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx(); // 获取实例
        deploy1(vertx);
        // deploy2(vertx);
        // deploy3(vertx);
        // deploy4(vertx);
        // deploy5(vertx);
        // deploy6(vertx);
    }

    // 共享数据
    private static void deploy6(Vertx vertx) {
        vertx.deployVerticle(SharedDataVerticle.class.getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 获取共享数据
        SharedData sd = vertx.sharedData();
        LocalMap<String, String> map1 = sd.getLocalMap("map1");
        System.out.println(map1.get("one"));
        LocalMap<String, Buffer> map2 = sd.getLocalMap("map2");
        System.out.println(map2.get("two").toString());
    }

    // http服务
    private static void deploy5(Vertx vertx) {
        vertx.deployVerticle(HttpServerVerticle.class.getName());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        vertx.deployVerticle(HttpClientServerVerticle.class.getName());
    }

    // json和buffer操作
    private static void deploy4(Vertx vertx) {
        vertx.deployVerticle(JsonVerticle.class.getName());
        vertx.deployVerticle(BufferVerticle.class.getName());
    }

    // eventBus测试
    private static void deploy3(Vertx vertx) throws Exception {
        vertx.deployVerticle(ConsumerVerticle.class.getName());
        TimeUnit.SECONDS.sleep(1);
        vertx.deployVerticle(ProducerVerticle.class.getName());
    }

    // 执行同步代码 两种方式
    private static void deploy2(Vertx vertx) {
        // 创建同步线程池
        WorkerExecutor executor = vertx.
                createSharedWorkerExecutor(WORKERPOOL, 12, 60000);
        // 执行同步阻塞代码 ordered:是否按顺序执行 默认在同一个verticle按顺序执行
        executor.executeBlocking(future -> {
            try {
                TimeUnit.SECONDS.sleep(1);
                future.complete("完成操作"); // 触发回调
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }, false, res -> { // 完成后回调
            System.out.println(res.result());
        });
        // 将Verticle部署成Worker Verticle调用阻塞式代码 不会阻塞event loop
        // options = new DeploymentOptions().setWorker(true);
        // vertx.deployVerticle(TestVerticle.class.getName(), options);
    }

    // 简单部署verticle
    private static void deploy1(Vertx vertx) {
        // 部署组件
        DeploymentOptions options = new DeploymentOptions().setInstances(1); // 部署多个实例
        // 部署时可以传递json配置
        JsonObject config = new JsonObject().put("one", "one").put("two", "two");
        options.setConfig(config);
        vertx.deployVerticle(TestVerticle.class.getName(), options);
    }
}