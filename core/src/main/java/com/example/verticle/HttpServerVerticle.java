package com.example.verticle;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.MultiMap;
import io.vertx.core.http.HttpServerOptions;

// vert.x支持 HTTP/1.0 HTTP/1.1 HTTP/2协议
public class HttpServerVerticle extends AbstractVerticle {
    @Override
    public void start() {
        // 创建HttpServer
        HttpServerOptions options = new HttpServerOptions().setMaxWebsocketFrameSize(1000000);
        vertx.createHttpServer(options).requestHandler(request -> { // 请求处理
            // 响应
            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("vert.x output");
        }).listen(8080, "127.0.0.1", http -> { // 监听地址绑定
            System.out.println(http.succeeded() ? "---server started---" : "---server fail---"); // 成功通知
        });


        vertx.createHttpServer(options).requestHandler(request -> { // 请求处理

            request.setExpectMultipart(true); // 必须先设置获取表单数据
            request.uploadHandler(upload -> {
                System.out.println("获取上传文件name : " + upload.name());
                // 将文件写入磁盘
                upload.streamToFileSystem("myuploads_directory/" + upload.filename());
            });
            request.endHandler(v -> {
                // 请求体被完全读取 才能读取表单属性
                MultiMap formAttributes = request.formAttributes();
                System.out.println("获取表单" + formAttributes.get("name"));
            });
            // 获取请求数据
            System.out.println(request.method());
            System.out.println(request.absoluteURI()); // 绝对路径
            System.out.println(request.path()); // 路径 不包含参数
            System.out.println(request.uri()); // 相对路径
            System.out.println(request.version()); // http版本
            System.out.println(request.query()); // url中查询参数部分
            System.out.println(request.host()); // 请求主机名
            System.out.println(request.params()); // 请求路径中的参数
            System.out.println(request.remoteAddress()); // 请求者地址
            // 获取请求头
            MultiMap headers = request.headers();
            System.out.println("User agent is " + headers.get("user-agent"));
            // 整个请求完全读取调用
            request.endHandler(v -> {
                System.out.println("请求完成读取" + v);
            });
            // 响应
            request.response()
                    .putHeader("content-type", "text/plain")
                    .end("Hello from Vert.x!");
        }).listen(8080, "127.0.0.1", http -> { // 监听地址绑定
            System.out.println(http.succeeded() ? "---server started---" : "---server fail---"); // 成功通知
        });
    }
}