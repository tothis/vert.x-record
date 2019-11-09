package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.Session;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;

public class SessionVeerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        // 存储
        // 不指定名称创建 适用于只需要一个应用session管理
        SessionStore store1 = LocalSessionStore.create(vertx);
        // 通过指定的Map名称创建了一个本地会话存储 并设置了检查过期Session的周期为10秒
        SessionStore store3 = LocalSessionStore.create(vertx, "app01", 10000);
        // cookie处理器
        router.route().handler(CookieHandler.create());
        // 创建session处理器
        SessionHandler sessionHandler = SessionHandler.create(store1);
        // 确保所有请求都会经过session处理器
        router.route().handler(sessionHandler);
        router.route("/session").handler(routingContext -> {
            Session session = routingContext.session();
            session.put("foo", "bar");
            // 从会话中删除值
            JsonObject obj = session.remove("myobj");
            routingContext.response().end(session.get("foo").toString());
        });
    }
}