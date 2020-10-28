package com.example.serverdemo.component;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.serverdemo.util.JsonUtils;
import com.yanzhenjie.andserver.annotation.Interceptor;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.http.HttpMethod;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.util.MultiValueMap;


@Interceptor
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean onIntercept(@NonNull HttpRequest request, @NonNull HttpResponse response, @NonNull RequestHandler handler) throws Exception {
        String path = request.getPath();
        HttpMethod method = request.getMethod();
        MultiValueMap<String,String> valueMap=request.getParameter();
        //path表示 网页端请求路径，这个路径需要跟服务端一一对应
        // method表示请求的方式
        //params表示网页端向服务端请求的内容
        //这三个字段务必不能为空，如果为空 表示网页端与服务端 通信不成功
        //搞不懂  这个地方他做了封装处理  反正就是能接收到  网页端发送过来的各种消息
        Log.e("TAG","LoggerInterceptor=path:"+path);
        Log.e("TAG","LoggerInterceptor=method:"+method.value());
        Log.e("TAG","LoggerInterceptor=param:"+ JsonUtils.toJsonString(valueMap));
        return false;
    }
}
