package com.example.emmons.Function;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;

/**
 * Created by Emmons on 2018/9/28 0028.
 * 处理文件的一些公共方法
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


    //清空某一文件夹
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

    //写数据流
    public void Write_TXT(File outfile,InputStream in){
        try {
            FileOutputStream out = new FileOutputStream(outfile);
            byte[] buffer = new byte[1024];
            int readBytes = 0;
            while ((readBytes = in.read(buffer)) != -1)
                out.write(buffer, 0, readBytes);
            in.close();
            out.close();
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
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

    //删掉某一文件
    public boolean deleteSingleFile(Context context,String filePath$Name) {
        File file = new File(filePath$Name);        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                notifySystemToScan(context,file);
                return true;
            }
            else {
                Toast.makeText(context, "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else {
            Toast.makeText(context, "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
