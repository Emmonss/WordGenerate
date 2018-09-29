package com.example.emmons.Function;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Emmons on 2018/9/28 0028.
 */

public class Common_Fuction {
    //将html文件保存到sd卡
    public void writeFile(String content, String path) {

        FileOutputStream fos = null;
        BufferedWriter bw = null;
        try {
            File file = new File(path);
            if(!file.exists()){
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bw = new BufferedWriter(new OutputStreamWriter(fos,"utf-8"));
            bw.write(content);
            //scanFile(path);
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            try {
                if (bw != null)
                    bw.close();
                if (fos != null)
                    fos.close();
            }
            catch (IOException ie) {
            }
        }
    }

    //清空html
    //清空html
    public void deleteFile(Context context,File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                File f = files[i];
                deleteFile(context,f);
            }
            //file.delete();//如要保留文件夹，只删除文件，请注释这行
        } else if (file.exists()) {
            file.delete();
        }
        notifySystemToScan(context,file);
    }

    //广播文件
    public void notifySystemToScan(Context context, File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
        }
        catch (Exception e){
        }
    }
}
