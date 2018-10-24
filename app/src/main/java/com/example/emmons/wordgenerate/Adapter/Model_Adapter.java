package com.example.emmons.wordgenerate.Adapter;


import com.example.emmons.wordgenerate.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by Emmons on 2018/10/12 0012.
 * 模型栏目生成器Adapter
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

            convertView = layoutInflater.inflate(R.layout.activity_model_item, null);

            holder = new Model_Adapter.ViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (Model_Adapter.ViewHolder) convertView.getTag();
        }

        holder.bindData(files[i]);

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
