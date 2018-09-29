package com.example.emmons.wordgenerate;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by Emmons on 2018/8/31 0031.
 */

public class Initialize_Activity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION_CODE = 1;
    public String FILE_PATH_NAME = "WordGenerate";


    private ImageView iv_open_folder;

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


    }

    //查看目标文件夹是否创建
    private void get_File_Path(File appDir){

        if (!appDir.exists()) {
            appDir.mkdir();
            notifySystemToScan(appDir);
            //Toast.makeText(Initialize_Activity.this,FILE_PATH_NAME+"创建成功",Toast.LENGTH_LONG).show();
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



    private void Open_Folder(){
        Intent intent = new Intent(Initialize_Activity.this,Word_Relative_List_Activity.class);
        intent.putExtra("path",FILE_PATH_NAME);
        startActivity(intent);

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