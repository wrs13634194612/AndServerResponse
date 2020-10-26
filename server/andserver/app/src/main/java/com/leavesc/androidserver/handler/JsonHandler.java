package com.leavesc.androidserver.handler;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.leavesc.androidserver.JsonUtil;
import com.leavesc.androidserver.MainActivity;
import com.leavesc.androidserver.bean.ResultBean;
import com.leavesc.androidserver.bean.ServerBean;
import com.leavesc.androidserver.bean.UserDateBean;
import com.leavesc.androidserver.server.DateBean;
import com.yanzhenjie.andserver.RequestHandler;

//import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.RequestMethod;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.util.HttpRequestParser;

import org.apache.httpcore.HttpException;
import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;
import org.apache.httpcore.entity.StringEntity;
import org.apache.httpcore.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：leavesC
 * 时间：2018/4/5 16:30
 * 描述：https://github.com/leavesC/AndroidServer
 * https://www.jianshu.com/u/9df45b87cfdf
 */
public class JsonHandler implements RequestHandler {

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.GET})
    @Override
    public void handle(HttpRequest httpRequest, HttpResponse httpResponse, HttpContext httpContext) throws HttpException, IOException {
        Log.e("TAG", "客户端请求方式：" + httpRequest.getRequestLine().getMethod()
                +": "+HttpRequestParser.getRequestMethod(httpRequest).getValue());
        switch (HttpRequestParser.getRequestMethod(httpRequest).getValue()) {
            case "GET":
                Log.d("TAG", "客户端发送到服务端：" +  HttpRequestParser.getContent(httpRequest));
                StringEntity stringEntity2 = new StringEntity("服务端发送给客户端："
                        +HttpRequestParser.parseParams(httpRequest).get("user")
                        +HttpRequestParser.parseParams(httpRequest).get("password"), "utf-8");
                httpResponse.setStatusCode(200);
                httpResponse.setEntity(stringEntity2);
                break;
            case "POST":
                String content = HttpRequestParser.getContentFromBody(httpRequest);
                String decodeStr = URLDecoder.decode(content, "utf-8");
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(decodeStr);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("TAG", "客户端发送到服务端：" + jsonObject);
                StringEntity stringEntity = new StringEntity("服务端发送给客户端：" + jsonObject.toString(), "utf-8");
                httpResponse.setStatusCode(200);
                httpResponse.setEntity(stringEntity);
                break;
            default:
                Log.d("TAG", "未知的请求方式");
                break;
        }
    }
}
