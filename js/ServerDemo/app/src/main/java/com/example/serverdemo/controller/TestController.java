package com.example.serverdemo.controller;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.example.serverdemo.component.LoginInterceptor;
import com.example.serverdemo.model.UserInfo;
import com.example.serverdemo.util.FileUtils;
import com.example.serverdemo.util.JsonUtils;
import com.yanzhenjie.andserver.annotation.Addition;
import com.yanzhenjie.andserver.annotation.CookieValue;
import com.yanzhenjie.andserver.annotation.FormPart;
import com.yanzhenjie.andserver.annotation.GetMapping;
import com.yanzhenjie.andserver.annotation.PathVariable;
import com.yanzhenjie.andserver.annotation.PostMapping;
import com.yanzhenjie.andserver.annotation.PutMapping;
import com.yanzhenjie.andserver.annotation.RequestBody;
import com.yanzhenjie.andserver.annotation.RequestMapping;
import com.yanzhenjie.andserver.annotation.RequestParam;
import com.yanzhenjie.andserver.annotation.RestController;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.cookie.Cookie;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;
import com.yanzhenjie.andserver.http.session.Session;
import com.yanzhenjie.andserver.util.MediaType;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class TestController {

    //post请求
    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String login(HttpRequest request, HttpResponse response, @RequestParam(name = "account") String account,
                 @RequestParam(name = "password") String password) {
        Log.e("TAG", "TestController=login: ");
        //                JsonUtils.toJsonString(request.getParameter())
        Session session = request.getValidSession();
        session.setAttribute(LoginInterceptor.LOGIN_ATTRIBUTE, true);
        Cookie cookie = new Cookie("account", account + "=" + password);
        response.addCookie(cookie);
        return JsonUtils.toJsonString(request.getParameter());
    }

    //post请求
    @PostMapping(path = "/loginuser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    String loginuser(HttpRequest request,
                     HttpResponse response,
                     @RequestParam(name = "username") String username,
                     @RequestParam(name = "password") String password
    ) {
        Log.e("TAG", "TestController=loginuser: ");
        // JsonUtils.toJsonString(request.getParameter())
        Session session = request.getValidSession();
        session.setAttribute(LoginInterceptor.LOGIN_ATTRIBUTE, true);
        Cookie cookie = new Cookie("username", username + "=" + password);
        response.addCookie(cookie);
        return JsonUtils.toJsonString(request.getParameter());
    }


    //get请求
    @GetMapping(path = "/message", produces = {"application/json; charset=utf-8"})
    String message() {
        Log.e("TAG", "TestController=message: ");
        UserInfo userInfo=new UserInfo();
        userInfo.setUserName("张飞");
        userInfo.setUserId("NO.9587");
        return JSON.toJSONString(userInfo);
    }

}
