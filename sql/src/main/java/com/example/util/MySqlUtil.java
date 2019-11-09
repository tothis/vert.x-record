package com.example.util;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.AsyncSQLClient;
import io.vertx.ext.asyncsql.MySQLClient;

public class MySqlUtil {

    private static AsyncSQLClient asyncSQLClient;

    /**
     * 获取mysqlclient对象
     *
     * @param vertx
     * @param jsonObject
     * @return
     */
    public static AsyncSQLClient getAsynMysqlClientInstance(Vertx vertx, JsonObject jsonObject) {
        if (asyncSQLClient == null) {
            asyncSQLClient = MySQLClient.createShared(vertx, jsonObject);
            return asyncSQLClient;
        }
        return asyncSQLClient;
    }
}