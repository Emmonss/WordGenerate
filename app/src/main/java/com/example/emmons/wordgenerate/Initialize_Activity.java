package com.example.emmons.wordgenerate;

import android.Manifest;
import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.emmons.utils.FileUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Emmons on 2018/8/31 0031.
 */

public class Initialize_Activity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_CODE = 1;
    public String FILE_PATH_NAME = "WordGenerate";


    private ImageView iv_open_folder;
    private ImageView iv_open_model;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        File appDir = new File(Environment.getExternalStorageDirectory(), FILE_PATH_NAME);
        get_SDcard_permission();

        get_File_Path(appDir);


        iv_open_folder = (ImageView)findViewById(R.id.open_folder);

        iv_open_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Open_Folder();
            }
        });


        iv_open_model = (ImageView)findViewById(R.id.make_model);

        iv_open_model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Make_Model();
            }
        });

    }
    private void Open_Folder(){
        Intent intent = new Intent(Initialize_Activity.this,Word_Relative_List_Activity.class);
        intent.putExtra("path",FILE_PATH_NAME);
        startActivity(intent);

    }


    private void Make_Model(){
        Intent intent = new Intent(Initialize_Activity.this,Model_Relative_List_Activity.class);
        intent.putExtra("path",FILE_PATH_NAME);
        startActivity(intent);
    }

    //查看目标文件夹是否创建
    private void get_File_Path(File appDir){
        if (!appDir.exists())
            appDir.mkdir();

        add_Default_File(FILE_PATH_NAME);
        add_Mode_File(FILE_PATH_NAME);
        notifySystemToScan(appDir);
    }

    private void add_Default_File(String path){
        try {
            File default_file = new File(Environment.getExternalStorageDirectory(), FILE_PATH_NAME + "/file/");
            String default_file_doc = path+"/file/1.doc";
            if (!default_file.exists())
                default_file.mkdir();
            File[] list = default_file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(".doc");
                }
            });

            if(list.length==0){
                InputStream inputStream = getAssets().open("1.doc");
                FileUtils.writeFile(new File(Environment.getExternalStorageDirectory(),default_file_doc), inputStream);
            }
            notifySystemToScan(default_file);
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }


    private void add_Mode_File(String path){
        File default_file = new File(Environment.getExternalStorageDirectory(), FILE_PATH_NAME + "/mode/");
        if (!default_file.exists())
            default_file.mkdir();

        //添加一号模板
        get_Mode1_1(FILE_PATH_NAME + "/mode/");


    }

    private void get_Mode1_1(String model_path){
        try {
            String model = model_path + "/隐患整改通知/";
            File default_dir = new File(Environment.getExternalStorageDirectory(), model);
            if (!default_dir.exists())
                default_dir.mkdir();

            String default_file_doc = model + "demo.doc";
            String company_name = model + "companyname.txt";

            File default_file = new File(Environment.getExternalStorageDirectory(), default_file_doc);
            File COMName = new File(Environment.getExternalStorageDirectory(), company_name);
            if (!default_file.exists()) {
                InputStream inputStream = getAssets().open("隐患整改通知.doc");
                FileUtils.writeFile(new File(Environment.getExternalStorageDirectory(), default_file_doc), inputStream);
            }
            if (!COMName.exists()) {
                COMName.createNewFile();
                Write_TXT(COMName,"companyname.txt");
            }
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }


    }
    //广播创建的文件
    private void notifySystemToScan(File file) {
        try {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            this.sendBroadcast(intent);
        }
        catch (Exception e){
            Toast.makeText(Initialize_Activity.this,"创建失败！",Toast.LENGTH_LONG).show();
        }
//这里是context.sendBroadcast(intent);

    }


    public void Write_TXT(File outfile, String inpath){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(outfile));

            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("comname.txt"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                Result = line;
                Result += "\n";
                bw.write(Result);
                bw.flush();
            }

            bw.close();
            inputReader.close();
            bufReader.close();
        }
        catch (Exception err){
            Log.e("file",err.toString());
        }
    }

    //获得手机相机和图册的权限
    private void get_SDcard_permission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(Initialize_Activity.this, Manifest.permission.CAMERA);
            int checkWriteSDPermission = ContextCompat.checkSelfPermission(Initialize_Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED || checkWriteSDPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(Initialize_Activity.this, new String[]{Manifest.permission.CAMERA
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
            }
            else {
            }
        }
        else {
        }
    }


}
