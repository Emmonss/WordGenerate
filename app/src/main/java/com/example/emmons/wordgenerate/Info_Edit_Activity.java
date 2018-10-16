package com.example.emmons.wordgenerate;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.emmons.wordgenerate.time.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Emmons on 2018/10/15 0015.
 */

public class Info_Edit_Activity  extends AppCompatActivity {
    private CustomDatePicker customDatePicker_LimitDate,customDatePicker_SigDate,customDatePicker_ZGDate,customDatePicker_FJDate;


    TextView tv_LimitDate,tv_SigDate,tv_ZGDate,tv_FJDate;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yinhuan_mode_generation);


        init();
    }

    private void init(){
        tv_LimitDate = (TextView)findViewById(R.id.Yinhuan_tv_limitdate);
        tv_SigDate = (TextView)findViewById(R.id.Yinhuan_tv_Singdate);
        tv_ZGDate = (TextView)findViewById(R.id.Yinhuan_tv_ZhengGaidate);
        tv_FJDate = (TextView)findViewById(R.id.Yinhuan_tv_Fujiandate);


        init_datepicker();
        get_LimtDate();
    }

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
}
