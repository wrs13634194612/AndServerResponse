package com.example.serverdemo.component;

import android.util.Log;

import androidx.annotation.NonNull;

import com.yanzhenjie.andserver.annotation.Interceptor;
import com.yanzhenjie.andserver.error.BasicException;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.handler.MethodHandler;
import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.http.session.Session;
import com.yanzhenjie.andserver.mapping.Addition;

import org.apache.commons.lang3.ArrayUtils;


@Interceptor
public class LoginInterceptor implements HandlerInterceptor {
    public static final String LOGIN_ATTRIBUTE = "USER.LOGIN.SIGN";

    @Override
    public boolean onIntercept(@NonNull HttpRequest request, @NonNull HttpResponse response, @NonNull RequestHandler handler) throws Exception {
        if (handler instanceof MethodHandler) {
            MethodHandler methodHandler = (MethodHandler) handler;
            Addition addition = methodHandler.getAddition();
            if (!isLogin(request,addition)){
                throw new BasicException(401,"you are not logged in yet.");
            }
        }

        return false;
    }

    private boolean isLogin(HttpRequest request, Addition addition) {
        if (isNeedLogin(addition)) {
            Session session = request.getSession();
            if (session != null) {
                Object o = session.getAttribute(LOGIN_ATTRIBUTE);
                return o != null && (o instanceof Boolean) && ((boolean) o);
            }
            return false;
        }
        return true;
    }

    private boolean isNeedLogin(Addition addition) {
        if (addition == null) return false;
        String[] stringType = addition.getStringType();
        if (ArrayUtils.isEmpty(stringType)) return false;
        boolean[] booleanType = addition.getBooleanType();
        if (ArrayUtils.isEmpty(booleanType)) return false;
        //  比较两个字符串 不区分大小写
        Log.e("TAG", "LoginInterceptor:" + stringType[0] + ": " + booleanType[0]);
        return stringType[0].equalsIgnoreCase("login") && booleanType[0];
    }


}
