package com.example.emmons.wordgenerate;
/**
 * Created by Emmons on 2018/8/31 0031.
 * 初始化
 */

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.emmons.Function.Common_Fuction;
import com.example.emmons.Function.replace_data;
import com.example.emmons.Model.Model_YinHuan;

import java.io.File;
import java.io.FileFilter;

import java.io.InputStream;


public class Initialize_Activity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_CODE = 1;
    public String FILE_PATH_NAME = "WordGenerate";

    private Common_Fuction cf;
    private ImageView iv_open_folder;
    private ImageView iv_open_model;


    protected void onCreate(@Nullable final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialize);

        cf = new Common_Fuction();

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

    //添加默认文件夹
    private void add_Default_File(String path){
        try {
            String default_path = Environment.getExternalStorageDirectory()+"/"+FILE_PATH_NAME + "/file/";
            File default_file = new File(Environment.getExternalStorageDirectory(), FILE_PATH_NAME + "/file/");
            //Log.i("docx_1",default_file.getAbsolutePath());
            String default_file_doc = default_path+"1.docx";
            if (!default_file.exists())
                default_file.mkdir();

            //如果默认文件夹没有文件，最有添加默认范例。
            File[] list = default_file.listFiles(new FileFilter() {
                public boolean accept(File file) {
                    return file.getName().endsWith(".docx");
                }
            });

            if(list.length==0){
                InputStream inputStream = getAssets().open("1.docx");
                cf.Write_TXT(new File(default_file_doc),inputStream);
                //Log.i("docx_2",default_file_doc);
                //FileUtils.writeFile(new File(Environment.getExternalStorageDirectory(),default_file_doc), inputStream);
            }
        }
        catch (Exception err){
            //Log.e("docx_err",err.toString());
        }
    }

    //添加模板
    private void add_Mode_File(String path){
        File default_file = new File(Environment.getExternalStorageDirectory(), FILE_PATH_NAME + "/mode/");
        if (!default_file.exists())
            default_file.mkdir();
        try {
            InputStream model_yinhuan_comname = getAssets().open("comname.db");
            //添加一号模板
            new Model_YinHuan(Initialize_Activity.this,FILE_PATH_NAME + "/mode/",model_yinhuan_comname);
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
//



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
