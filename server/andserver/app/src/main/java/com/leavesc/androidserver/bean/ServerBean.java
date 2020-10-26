package com.leavesc.androidserver.bean;

import org.apache.httpcore.HttpRequest;
import org.apache.httpcore.HttpResponse;

public class ServerBean {

    HttpRequest httpRequest;
    HttpResponse httpResponse;

    public ServerBean(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }
}
