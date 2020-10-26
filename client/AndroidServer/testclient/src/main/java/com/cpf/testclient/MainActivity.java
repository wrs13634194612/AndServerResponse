package com.cpf.testclient;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpf.testclient.bean.PersonEntity;
import com.cpf.testclient.bean.UserBean;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int ACTION_REQUEST_PERMISSIONS = 0x001;
    private int i;

    private ImageView mIv_default_image;
     private TextView mTv_error_content;
    private Button mBtn_send_json;
    private Button mBtn_getImage;
    private Button mBtn_save_image;

    private Button btn_login;
    private Button btn_user_message;
    String urljson3 = "http://192.168.1.5:8080/json";
    String urlupload = "http://192.168.1.5:8080/upload";
    String urlimage = "http://192.168.1.5:8080/image/";
    String urljson2 = "http://192.168.1.5:8080/json";
    String urljson1 = "http://192.168.1.5:8080/json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        mTv_error_content = findViewById(R.id.home_error_content);
        mIv_default_image = findViewById(R.id.home_image);
        mBtn_getImage = findViewById(R.id.home_btn_getImage);
        mBtn_send_json = findViewById(R.id.home_btn_send_json);
        mBtn_save_image = findViewById(R.id.home_btn_save_image);

        btn_login = findViewById(R.id.btn_login);
        btn_user_message = findViewById(R.id.btn_user_message);

        mBtn_getImage.setOnClickListener(this);
        mBtn_save_image.setOnClickListener(this);
        mBtn_send_json.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_user_message.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_btn_save_image:
                mIv_default_image.setDrawingCacheEnabled(true);
                Bitmap bitmap = mIv_default_image.getDrawingCache();
                if (bitmap != null) {
                    postImage(bitmap);
                } else {
                    Toast.makeText(this, "请先获取图片，再次进行上传操作", Toast.LENGTH_SHORT).show();
                }
                mIv_default_image.setDrawingCacheEnabled(false);
                break;
            case R.id.home_btn_send_json:
                getUserInformation();
                break;
            case R.id.home_btn_getImage:
                getAllUserPicture();
                break;
            case R.id.btn_login:
                getLogin();
                break;
            case R.id.btn_user_message:
                getUserMessage();
                break;
        }

    }




    private void getUserMessage() {
        OkHttpUtils
                .get()
                .url(urljson2)
                .addParams("user", "010")
                .addParams("password","admin123")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "服务器返回的结果失败101: ");
                        mTv_error_content.setText("失败：" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "服务器返回的结果成功102: " + response);
                        mTv_error_content.setText("成功：" + response);
                    }
                });
    }

    /**
     * 获取用户图片
     */
    private void getAllUserPicture() {
        OkHttpUtils
                .get()//
                .url(urlimage)//
                .build()//
                .execute(new BitmapCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "服务器返回的结果失败103: ");
                        mTv_error_content.setText("失败：获取图片失败");
                    }

                    @Override
                    public void onResponse(Bitmap response, int id) {
                        Log.e("TAG", "服务器返回的结果成功104: ");
                        mIv_default_image.setImageBitmap(response);
                        mTv_error_content.setText("成功：获取图片成功");
                    }
                });
    }


    public void postImage(Bitmap bitmap) {
        String fileName = System.currentTimeMillis() + ".jpg";
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("name", "张三");//
        File file = CommUtils.saveImg(bitmap, fileName, this);
        if (file != null) {
            try {
                OkHttpUtils.post()
                        .addFile("avatarFile", fileName, file)//
                        .url(urlupload)
                        .params(map)
                        .build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mTv_error_content.setText("失败：上传图片失败");
                        Log.e("TAG", "服务器返回的结果失败105: ");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mTv_error_content.setText("成功：上传图片成功");
                        Log.e("TAG", "服务器返回的结果成功106: ");
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getUserInformation() {
        PersonEntity entity = new PersonEntity();
        entity.age = 18;
        entity.desc = "认真的人总是最可爱";
        entity.name = "最可爱的人";
        OkHttpUtils
                .postString()
                .url(urljson3)
                .content(new Gson().toJson(entity))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG", "服务器返回的结果失败107: ");
                        mTv_error_content.setText("失败：" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("TAG", "服务器返回的结果成功108: "+response);
                        mTv_error_content.setText("成功：" + response);
                    }
                });
    }


    private void getLogin() {
        UserBean userBean = new UserBean("admin", "123456");
        OkHttpUtils
                .postString()
                .url(urljson1)
                .content(new Gson().toJson(userBean))
                .mediaType(MediaType.parse("application/json; charset=utf-8"))
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mTv_error_content.setText("失败：" + e.toString());
                        Log.e("TAG", "服务器返回的结果失败109: ");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        mTv_error_content.setText("成功：" + response);
                        Log.e("TAG", "服务器返回的结果成功110: "+response);
                    }
                });
    }



    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACTION_REQUEST_PERMISSIONS);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();
        }
    }


}
