package com.example.emmons.wordgenerate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emmons.wordgenerate.Adapter.Model_Adapter;
import java.io.File;
import java.io.FileFilter;

/**
 * Created by Emmons on 2018/10/12 0012.
 * 模型加载列表
 */

public class Model_Relative_List_Activity extends AppCompatActivity {
    ListView listView;

    Model_Adapter fileAdapter;

    File[] data;

    String stringValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 加载布局
        setContentView(R.layout.activity_model_list);

        // 初始化
        initView();
    }
    private void initView() {
        listView = (ListView) findViewById(R.id.model_listView);
        // 获得外部存储的路径
        Intent intent=getIntent();
        stringValue=intent.getStringExtra("path")+"/mode/";
        File path = new File(Environment.getExternalStorageDirectory(), stringValue);
        File[] files = path.listFiles(new FileFilter() {
            // 实现之接口
            public boolean accept(File file) {
                return file.isDirectory();
            }
        });

        data = files;

        if(data==null)
            Toast.makeText(Model_Relative_List_Activity.this,"当前暂无模板",Toast.LENGTH_LONG).show();
        else {
            fileAdapter = new Model_Adapter(this, data);
            //Toast.makeText(Model_Relative_List_Activity.this,files[0].getName(),Toast.LENGTH_LONG).show();
        }
        listView.setAdapter(fileAdapter);


        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Model_Relative_List_Activity.this,Info_Edit_Activity.class);
                        intent.putExtra("path",Environment.getExternalStorageDirectory()+"/"+stringValue);
                        startActivity(intent);
                    }
                }
        );

    }
}
