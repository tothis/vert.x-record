package com.example.verticle;

import com.example.pojo.OrderMessage;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

public class JsonVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 创建
        JsonObject object = new JsonObject();
        object.put("id", 123).put("name", "leilei"); // 流式API
        object.getString("foo");//获取
        // 转对象
        OrderMessage order = object.mapTo(OrderMessage.class);
        System.out.println("name:" + order.getName());
        // 转换为优美字符串
        System.out.println(object.encodePrettily());
        // json数组
        String jsonString = "[\"li\",\"lei\"]";
        JsonArray array = new JsonArray(jsonString);
        array.add("name").add(123).add(false);
        array.getBoolean(4); // 类型必须与索引位置上数据类型一样
        array.encode(); // 编码成字符串
    }
}