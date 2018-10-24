package com.example.emmons.wordgenerate;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.emmons.wordgenerate.Adapter.Person;
import com.example.emmons.wordgenerate.Adapter.ComName_Adapter;
import com.example.emmons.Model.YinHuan_Company_List;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmons on 2018/10/16 0016.
 * 隐患整改通知的公司名称列表
 */

public class ComName_List_Activity extends AppCompatActivity {
    ListView Clist_view;
    YinHuan_Company_List ReadList;
    String ChoseName;
    Button Confirm,Delete;
    ImageView Add;

    int pos=-1;
    ComName_Adapter cadapter;

    List<Person> cname;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comname_list);


        Intent intent=getIntent();
        ReadList = new YinHuan_Company_List(intent.getStringExtra("path"));
        ChoseName = "未选定";


        Clist_view = (ListView)findViewById(R.id.comname_listView);
        Confirm = (Button)findViewById(R.id.fuckyou);
        Delete = (Button)findViewById(R.id.fuckyou2);
        Add = (ImageView)findViewById(R.id.Yinhuan_iv_process_save);

        init_Listview();
        Add_Item();
        Confirm_buttom();
        Delete_buttom();

    }

    //初始化
    @SuppressWarnings("unchecked")
    private void init_Listview(){

        cname = ReadList.get_List();

        cadapter = new ComName_Adapter(this,cname);
        Clist_view.setAdapter(cadapter);

        //从数据库中抽取数据显示在列表上
        Clist_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int currentNum = -1;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(Person person : cname){
                    person.setChecked(false);
                }

                if(currentNum == -1){ //选中
                    cname.get(position).setChecked(true);
                    currentNum = position;
                }else if(currentNum == position){ //同一个item选中变未选中
                    for(Person person : cname){
                        person.setChecked(false);
                    }
                    currentNum = -1;
                }else if(currentNum != position){ //不是同一个item选中当前的，去除上一个选中的
                    for(Person person : cname){
                        person.setChecked(false);
                    }
                    cname.get(position).setChecked(true);
                    currentNum = position;
                }
                pos = position;
                ChoseName = cname.get(position).getTitle();
                //Toast.makeText(parent.getContext(),datas.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                cadapter.notifyDataSetChanged();
            }
        });

    }


    //增加条目的监听器
    public void Add_Item(){
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComName_List_Activity.this,Add_new_Item_Activity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    //删除条目的监听器，并更新数据库
    private void Delete_buttom(){
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ChoseName.equalsIgnoreCase("未选定"))
                    Toast.makeText(ComName_List_Activity.this,ChoseName,Toast.LENGTH_SHORT).show();
                else {
                    new AlertDialog.Builder(ComName_List_Activity.this)
                            .setMessage("确定删除该文档吗？")
                            .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dia,int whichButton){
                                    ReadList.Delete(ChoseName);
                                    cname.remove(pos);
                                    cadapter.setDatas(cname);
                                    cadapter.notifyDataSetChanged();
                                }})
                            .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dia,int whichButton){}
                            }).create().show();
                }
            }
        });
    }

    //确定条目，返回数值。
    private void Confirm_buttom(){
        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ChoseName.equalsIgnoreCase("未选定"))
                    Toast.makeText(ComName_List_Activity.this,ChoseName,Toast.LENGTH_SHORT).show();
                else {
                    Intent intentTemp = new Intent();
                    intentTemp.putExtra("backString", ChoseName);
                    setResult(1, intentTemp);
                    finish();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //将增加条目返回值放到列表上并插入数据库
        if(requestCode == 1 && data != null){
            cname.add(new Person(data.getStringExtra("backString")));
            cadapter.setDatas(cname);
            cadapter.notifyDataSetChanged();
            ReadList.insert(data.getStringExtra("backString"));
            //Toast.makeText(ComName_List_Activity.this,data.getStringExtra("backString"),Toast.LENGTH_LONG).show();
        }
    }
}
