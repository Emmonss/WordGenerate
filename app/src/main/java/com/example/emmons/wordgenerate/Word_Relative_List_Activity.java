package com.example.emmons.wordgenerate;

/**
 * Created by Emmons on 2018/10/12 0012.
 * 文档加载列表
 */

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;

import com.example.emmons.Function.Common_Fuction;
import com.example.emmons.wordgenerate.Adapter.MyFile_Adapter;

public class Word_Relative_List_Activity extends AppCompatActivity {
    ListView listView;
    MyFile_Adapter fileAdapter;
    File[] data;

    private final String IMAGE_DIR = Environment.getExternalStorageDirectory() + "/WordGenerate/gridview/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_file_list);

        initView();
    }

    // 初始化
    private void initView() {
        listView = (ListView) findViewById(R.id.file_listView);

        Intent intent=getIntent();
        String stringValue=intent.getStringExtra("path")+"/file/";
        File path = new File(Environment.getExternalStorageDirectory(), stringValue);
        File[] files = path.listFiles(new FileFilter() {
            // 实现之接口
            public boolean accept(File file) {
                return file.getName().endsWith(".doc")||file.getName().endsWith(".docx");
            }
        });
        data = files;

        //删掉grid中的图片
        File dir = new File(IMAGE_DIR);
        new Common_Fuction().deleteFile(this,dir);

        if(data==null)
            Toast.makeText(this,"文件夹为空",Toast.LENGTH_LONG).show();
        else
            fileAdapter = new MyFile_Adapter(this, data);

        listView.setAdapter(fileAdapter);

    }
}

