package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Cookie;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CookieHandler;

import java.util.UUID;

public class CookieVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Router router = Router.router(vertx);
        vertx.createHttpServer().requestHandler(router::accept).listen(8080);
        router.route().handler(CookieHandler.create()); // cookie处理器要能够匹配到所有需要的请求
        router.route("/test").handler(routingContext -> {
            Cookie someCookie = routingContext.getCookie("rememberMe"); // 获取cookie
            routingContext.cookies(); // 获取cookie集合
            routingContext.cookieCount(); // cookie个数
            routingContext.removeCookie(""); // 删除cookie
            // 添加一个cookie会自动回写到响应里
            routingContext.addCookie(Cookie.cookie("token"
                    , UUID.randomUUID().toString().replace("-", "")));
            routingContext.response().end("cookie");
        });
    }
}