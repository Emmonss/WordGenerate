package com.example.emmons.wordgenerate;


import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;

public class Word_Relative_List_Activity extends AppCompatActivity {

    ListView listView;

    MyFile_Adapter fileAdapter;

    File[] data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 加载布局
        setContentView(R.layout.activity_file_list);

        // 初始化
        initView();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.listView);

        // 获得外部存储的路径
        Intent intent=getIntent();
        String stringValue=intent.getStringExtra("path");
        File path = new File(Environment.getExternalStorageDirectory(), stringValue);
        File[] files = path.listFiles(new FileFilter() {
            // 实现之接口
            public boolean accept(File file) {
                return file.getName().endsWith(".doc") || file.getName().endsWith(".docx")|| file.getName().endsWith(".rar");
            }
        });

        data = files;

        fileAdapter = new MyFile_Adapter(this, data);
        listView.setAdapter(fileAdapter);

        // 注册监听器
        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        File f = fileAdapter.getItem(position);
                    }
                }
        );
    }
}

