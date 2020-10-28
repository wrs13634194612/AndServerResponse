package com.example.serverdemo.component;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.serverdemo.util.JsonUtils;
import com.yanzhenjie.andserver.annotation.Resolver;
import com.yanzhenjie.andserver.error.BasicException;
import com.yanzhenjie.andserver.framework.ExceptionResolver;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.util.StatusCode;

@Resolver
public class AppExceptionResolver implements ExceptionResolver {
    @Override
    public void onResolve(@NonNull HttpRequest request, @NonNull HttpResponse response, @NonNull Throwable e) {
        e.printStackTrace();
        if (e instanceof BasicException){
            BasicException exception = (BasicException)e;
            Log.e("TAG","AppExceptionResolver-if:"+exception.getStatusCode());
            response.setStatus(exception.getStatusCode());
        }else {
            //访问出错 会执行这个
            Log.e("TAG","AppExceptionResolver-else:"+StatusCode.SC_INTERNAL_SERVER_ERROR);
            response.setStatus(StatusCode.SC_INTERNAL_SERVER_ERROR);
        }
        String body= JsonUtils.failedJson(response.getStatus(),e.getMessage());
        Log.e("TAG","AppExceptionResolver:"+body);
        response.setBody(new JsonBody(body));
    }
}
