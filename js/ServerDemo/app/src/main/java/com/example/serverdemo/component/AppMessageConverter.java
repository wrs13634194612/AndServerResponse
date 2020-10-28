package com.example.serverdemo.component;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.serverdemo.util.JsonUtils;
import com.yanzhenjie.andserver.annotation.Converter;
import com.yanzhenjie.andserver.framework.MessageConverter;
import com.yanzhenjie.andserver.framework.body.JsonBody;
import com.yanzhenjie.andserver.http.ResponseBody;
import com.yanzhenjie.andserver.util.IOUtils;
import com.yanzhenjie.andserver.util.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.Charset;


@Converter
public class AppMessageConverter implements MessageConverter {
    @Override
    public ResponseBody convert(@NonNull Object output, @Nullable MediaType mediaType) {
        //原来是在这个地方  把字符串升级成json的
        String mConvert = JsonUtils.successfulJson(output);
        Log.e("TAG","AppMessageConverter:"+mConvert);
        //这个地方的return   才是真正发送给网页端的内容
        return new JsonBody(mConvert);
    }

    @Nullable
    @Override
    public <T> T convert(@NonNull InputStream stream, @Nullable MediaType mediaType, Type type) throws IOException {
        Charset charset = mediaType == null ? null:mediaType.getCharset();
        if (charset==null){
            return JsonUtils.parseJson(IOUtils.toString(stream),type);
        }
        return JsonUtils.parseJson(IOUtils.toString(stream,charset),type);
    }
}
