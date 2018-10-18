package com.example.emmons.wordgenerate;


import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;

import com.example.emmons.wordgenerate.Adapter.MyFile_Adapter;

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
        listView = (ListView) findViewById(R.id.file_listView);

        // 获得外部存储的路径
        Intent intent=getIntent();
        String stringValue=intent.getStringExtra("path")+"/file/";
        File path = new File(Environment.getExternalStorageDirectory(), stringValue);
        File[] files = path.listFiles(new FileFilter() {
            // 实现之接口
            public boolean accept(File file) {
                return file.getName().endsWith(".doc");
            }
        });

        data = files;

        if(data==null)
            Toast.makeText(this,"文件夹为空",Toast.LENGTH_LONG).show();
        else
            fileAdapter = new MyFile_Adapter(this, data);


        listView.setAdapter(fileAdapter);




//        // 注册监听器
//        listView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
////                        String path  = fileAdapter.getItem(position).getPath();
////                        Intent intent = new Intent(Word_Relative_List_Activity.this,Html_show_Activity.class);
////                        intent.putExtra("path",path);
////                        startActivity(intent);
////                        Toast.makeText(Word_Relative_List_Activity.this,path,Toast.LENGTH_LONG).show();
//                    }
//                }
//        );

    }
}

