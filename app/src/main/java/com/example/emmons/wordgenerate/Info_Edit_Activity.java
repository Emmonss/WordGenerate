package com.example.emmons.wordgenerate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emmons.Model.YinHuan_Company_List;
import com.example.emmons.wordgenerate.WheelView.BottomDialog;
import com.example.emmons.wordgenerate.WheelView.WheelView;
import com.example.emmons.wordgenerate.time.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Emmons on 2018/10/15 0015.
 */

public class Info_Edit_Activity  extends AppCompatActivity {
    //日期选择
    private CustomDatePicker customDatePicker_LimitDate,customDatePicker_SigDate,customDatePicker_ZGDate,customDatePicker_FJDate;
    private TextView tv_LimitDate,tv_SigDate,tv_ZGDate,tv_FJDate;
    //公司选择
    private YinHuan_Company_List comlist;
    private ImageView iv_goto_departname;
    private EditText et_depart ;

    //Yinhuan_tv_yinzi
    private TextView tv_yinzi;
    private BottomDialog bottomDialog;

    String com_path;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yinhuan_mode_generation);

        Intent intent=getIntent();
        com_path=intent.getStringExtra("path")+"/隐患整改通知/companyname.db";
        comlist = new YinHuan_Company_List(com_path);


        init();
    }

    private void init(){
        //时间选择
        tv_LimitDate = (TextView)findViewById(R.id.Yinhuan_tv_limitdate);
        tv_SigDate = (TextView)findViewById(R.id.Yinhuan_tv_Singdate);
        tv_ZGDate = (TextView)findViewById(R.id.Yinhuan_tv_ZhengGaidate);
        tv_FJDate = (TextView)findViewById(R.id.Yinhuan_tv_Fujiandate);

        //公司选择
        et_depart = (EditText)findViewById(R.id.Yinhuan_et_edit_departname);
        iv_goto_departname = (ImageView)findViewById(R.id.Yinhuan_iv_goto_departname);

        //隐字号选择
        tv_yinzi = (TextView)findViewById(R.id.Yinhuan_tv_yinzi);



        get_Yinzi();

        get_ComName();


        init_datepicker();
        get_LimtDate();
    }



    private void get_Yinzi(){

        tv_yinzi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View outerView1 = LayoutInflater.from(Info_Edit_Activity.this).inflate(R.layout.activity_pick_yinzi, null);
                final WheelView wv1 = (WheelView) outerView1.findViewById(R.id.Yinhuan_yinzi_wheel);
                wv1.setItems(getNumbers(2017,2022),0);

                TextView tv_ok = (TextView) outerView1.findViewById(R.id.Yinhuan_yinzi_ok);
                TextView tv_cancel = (TextView) outerView1.findViewById(R.id.Yinhuan_yinzi_cancel);

                tv_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        bottomDialog.dismiss();
                        tv_yinzi.setText(wv1.getSelectedItem());
                    }
                });

                //点击取消
                tv_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        bottomDialog.dismiss();
                    }
                });

                //防止弹出两个窗口
                if (bottomDialog !=null && bottomDialog.isShowing())
                    return;

                bottomDialog = new BottomDialog(Info_Edit_Activity.this, R.style.ActionSheetDialogStyle);
                //将布局设置给Dialog
                bottomDialog.setContentView(outerView1);
                bottomDialog.show();//显示对话框

            }
        });

    }

    private void get_ComName(){
        final String list = comlist.getTest();
        iv_goto_departname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list==null)
                    Toast.makeText(Info_Edit_Activity.this,"不存在~",Toast.LENGTH_LONG).show();
                else{
//                    Toast.makeText(Info_Edit_Activity.this,list,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Info_Edit_Activity.this,ComName_List_Activity.class);
                    intent.putExtra("path",com_path);
                    startActivityForResult(intent,1);
                }

            }
        });

    }

    //日期
    private void init_datepicker(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        tv_LimitDate.setText(now.split(" ")[0]);
        tv_SigDate.setText(now.split(" ")[0]);
        tv_ZGDate.setText(now.split(" ")[0]);
        tv_FJDate.setText(now.split(" ")[0]);

        init_DatePicker();


    }

    private void init_DatePicker(){
        //1
        customDatePicker_LimitDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_LimitDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_LimitDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_LimitDate.setIsLoop(false); // 不允许循环滚动

        //2
        customDatePicker_SigDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_SigDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_SigDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_SigDate.setIsLoop(false); // 不允许循环滚动

        //3
        customDatePicker_ZGDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_ZGDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_ZGDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_ZGDate.setIsLoop(false); // 不允许循环滚动

        //4
        customDatePicker_FJDate = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                tv_FJDate.setText(time.split(" ")[0]);
            }
        }, "2016-01-01 00:00", "2020-12-31 23:59"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker_FJDate.showSpecificTime(false); // 不显示时和分
        customDatePicker_FJDate.setIsLoop(false); // 不允许循环滚动
    }

    private void get_LimtDate(){
        tv_LimitDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_LimitDate.show(tv_LimitDate.getText().toString());
            }
        });


        tv_SigDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_SigDate.show(tv_SigDate.getText().toString());
            }
        });

        tv_ZGDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_ZGDate.show(tv_ZGDate.getText().toString());
            }
        });

        tv_FJDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDatePicker_FJDate.show(tv_FJDate.getText().toString());
            }
        });
    }


    private ArrayList getNumbers( int start,int end) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            list.add(i + "");
        }
        return  list;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && data != null){
            et_depart.setText(data.getStringExtra("backString"));
        }
    }
}
