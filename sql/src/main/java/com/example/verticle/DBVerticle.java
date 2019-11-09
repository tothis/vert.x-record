package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.UpdateResult;

import java.util.List;

public class DBVerticle extends AbstractVerticle {
    @Override
    public void start() {
        JsonObject asyncSQLClientConfig = new JsonObject()
                .put("host", "192.168.1.56").put("port", 3306)
                .put("username", "root").put("password", "123456")
                .put("database", "vert.x");
        // 默认共享连接池 所有Verticle共享 只有第一次调用创建 后面都直接获取
        AsyncSQLClient asyncSQLClient = MySQLClient.createShared(vertx, asyncSQLClientConfig);
        // 创建不共享数据源 每个Verticle都会创建新的
        // AsyncSQLClient asyncSQLClient = MySQLClient.createNonShared(vertx, asyncSQLClientConfig);
        asyncSQLClient.queryWithParams("select * from text where text like ?"
                , new JsonArray().add("lei"), resultSet -> {
                    // 参数按顺序赋值到sql
                    if (resultSet.succeeded()) {
                        ResultSet result = resultSet.result(); // 获得result set
                        result.getColumnNames(); // 获取列名
                        List<JsonArray> results = result.getResults(); // 获取返回的数据
                        results.forEach(x -> {
                            System.out.println(x.getInteger(0));
                            System.out.println(x.getString(1));
                        });
                    } else {
                        System.out.println("---查询失败---" + resultSet.cause().toString());
                    }
                });
        // 增删改都用update
        asyncSQLClient.updateWithParams("insert text (text) values (?)"
                , new JsonArray().add("lilei"), res -> {
                    if (res.succeeded()) {
                        UpdateResult result = res.result();
                        System.out.println(result.getUpdated() + " lines updated");
                        // 如果是insert 可以获得主键
                        System.out.println("generated keys : " + result.getKeys());
                    }
                });
    }
}