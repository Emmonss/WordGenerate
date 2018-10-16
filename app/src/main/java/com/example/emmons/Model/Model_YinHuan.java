package com.example.emmons.Model;

import android.os.Environment;
import android.util.Log;

import com.example.emmons.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
    private InputStreamReader comname;
    public  Model_YinHuan(String path,InputStream mode,InputStreamReader comname){
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
            String company_name = model + "companyname.txt";

            File default_file = new File(Environment.getExternalStorageDirectory(), default_file_doc);
            File COMName = new File(Environment.getExternalStorageDirectory(), company_name);
            if (!default_file.exists())
                FileUtils.writeFile(new File(Environment.getExternalStorageDirectory(), default_file_doc), mode);

            if (!COMName.exists()) {
                COMName.createNewFile();
                Write_TXT(COMName,"companyname.txt");
            }
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }

    public void Write_TXT(File outfile, String inpath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));
            BufferedReader bufReader = new BufferedReader(comname);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result = line;
                Result += "\n";
                bw.write(Result);
                bw.flush();
            }
            bw.close();
            comname.close();
            bufReader.close();
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }


}
