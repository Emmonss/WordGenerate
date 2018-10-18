package com.example.emmons.Model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.emmons.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Emmons on 2018/10/15 0015.
 * 隐患整改通知的模型
 */

public class Model_YinHuan {
    private String path;
    private InputStream mode;
    private InputStream comname;
    private Context context;
    public  Model_YinHuan(Context context,String path,InputStream mode,InputStream comname){
        this.context = context;
        this.comname = comname;
        this.path = path;
        this.mode = mode;
        get_Mode1();
     }


    private void get_Mode1(){
        try {
            String model = path + "/隐患整改通知/";
            File default_dir = new File(Environment.getExternalStorageDirectory(), model);
            if (!default_dir.exists())
                default_dir.mkdir();

            String default_file_doc = model + "demo.doc";
            String company_name = model + "companyname.db";

            File default_file = new File(Environment.getExternalStorageDirectory(), default_file_doc);
            File COMName = new File(Environment.getExternalStorageDirectory(), company_name);
            if (!default_file.exists())
                FileUtils.writeFile(new File(Environment.getExternalStorageDirectory(), default_file_doc), mode);

            if (!COMName.exists()) {
                COMName.createNewFile();
                Write_TXT(COMName);
            }
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }

    public void Write_TXT(File outfile){
        try {
            FileOutputStream out = new FileOutputStream(outfile);
            InputStream in = comname;
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


}
