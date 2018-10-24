package com.example.emmons.Model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.emmons.Function.Common_Fuction;

import java.io.File;
import java.io.InputStream;


/**
 * Created by Emmons on 2018/10/15 0015.
 * 隐患整改通知的模型
 */

public class Model_YinHuan {
    private String path;
    private InputStream mode;
    private InputStream comname;
    private Context context;
    private Common_Fuction cf;

    public  Model_YinHuan(Context context,String path,InputStream comname){
        this.context = context;
        this.comname = comname;
        this.path = path;
        cf = new Common_Fuction();
        get_Mode1();
     }

    //写入必要的数据库或者其他文件
    private void get_Mode1(){
        try {
            String model = path + "/隐患整改通知/";
            File default_dir = new File(Environment.getExternalStorageDirectory(), model);
            if (!default_dir.exists())
                default_dir.mkdir();

            String company_name = model + "companyname.db";

            File COMName = new File(Environment.getExternalStorageDirectory(), company_name);

            if (!COMName.exists()) {
                COMName.createNewFile();
                cf.Write_TXT(COMName,comname);
            }
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }




}
