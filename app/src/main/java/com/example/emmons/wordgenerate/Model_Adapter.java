package com.example.emmons.wordgenerate;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Emmons on 2018/10/12 0012.
 */

public class Model_Adapter extends BaseAdapter {

    static int counter = 1;

    Context context;
    File[] files;
    LayoutInflater layoutInflater;

    public Model_Adapter(Context context, File[] files){
        this.context = context;
        this.files = files;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return files.length;
    }

    @Override
    public File getItem(int i) {
        return files[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            // 没有可复用，需要创建
            // 开销很大 加载文件、XML 解析 控件和布局的
            convertView = layoutInflater.inflate(R.layout.activity_model_item, null);

            // 每个视图项需要一个 viewHolder
            // 构造ViewHolder把View convertView传给它
            holder = new Model_Adapter.ViewHolder(convertView);

            // 视图项关联了它的视图结构
            convertView.setTag(holder);
        } else {
            // 有复用视图项，不创建，并直接获得结构
            holder = (Model_Adapter.ViewHolder) convertView.getTag();
        }
        // 加载数据
        holder.bindData(files[i]);

//        holder.iv_model_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
        ImageView iv_model_icon;
        TextView tv_model_name;
        ImageView iv_model_next;
        int id;

        public ViewHolder(View v){
            iv_model_icon = (ImageView)v.findViewById(R.id.iv_model_icon);
            tv_model_name = (TextView)v.findViewById(R.id.tv_model_name);
            iv_model_next = (ImageView)v.findViewById(R.id.iv_model_next);


            id = counter++;
        }
        public void bindData(File file) {
            tv_model_name.setText(file.getName());
        }

    }
}
