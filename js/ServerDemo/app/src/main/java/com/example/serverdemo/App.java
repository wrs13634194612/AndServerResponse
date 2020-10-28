package com.example.serverdemo;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.example.serverdemo.util.FileUtils;
import com.yanzhenjie.andserver.util.IOUtils;

import java.io.File;

public class App extends Application {
    private static App mInstance;
    private File mRootDir;

    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = this;
            initRootPath(this);
        }
    }

    @NonNull
    public static App getInstance() {
        return mInstance;
    }

    @NonNull
    public File getRootDir(){
        return mRootDir;
    }

    private void initRootPath(Context context){
        if (mRootDir!=null) return;
        if (FileUtils.storageAvailable()){
            mRootDir = Environment.getExternalStorageDirectory();
        }else {
            mRootDir = context.getFilesDir();
        }
        mRootDir=new File(mRootDir,"AndServer");
        IOUtils.createFolder(mRootDir);
    }
}
