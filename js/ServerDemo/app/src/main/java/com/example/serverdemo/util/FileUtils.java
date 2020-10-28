package com.example.serverdemo.util;


import android.os.Environment;
import android.webkit.MimeTypeMap;

import com.example.serverdemo.App;
import com.yanzhenjie.andserver.http.multipart.MultipartFile;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.UUID;

public class FileUtils {

    public static File createRandomFile(MultipartFile file) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(file.getContentType().toString());
        if (StringUtils.isEmpty(extension)) {
            extension = MimeTypeMap.getFileExtensionFromUrl(file.getFilename());
        }
        String uuid = UUID.randomUUID().toString();
        return new File(App.getInstance().getRootDir(), uuid + "." + extension);
    }

    public static boolean storageAvailable() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sd = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            return sd.canWrite();
        } else {
            return false;
        }
    }
}
