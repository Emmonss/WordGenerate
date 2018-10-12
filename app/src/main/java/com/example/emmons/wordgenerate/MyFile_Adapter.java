package com.example.emmons.wordgenerate;

import com.example.emmons.Function.Common_Fuction;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 自定义适配器
 */
public class MyFile_Adapter extends BaseAdapter {

    int clickPosition = -1;
    static int counter = 1;

    Context context;
    File[] files;
    ArrayList<File> fileList;
    LayoutInflater layoutInflater;
    Common_Fuction cf;

//    ImageButton action ;


    public MyFile_Adapter(Context context, File[] files) {
        this.context = context;
        this.files = files;
        this.cf = new Common_Fuction();

        // 获得服务实例
        layoutInflater = LayoutInflater.from(context);

    }
    public void delete_file(int i){
        List<File> l1 = new ArrayList<File>();
        for(int count = 0;count<this.files.length;count++)
            l1.add(this.files[count]);
        l1.remove(i);
        this.files = l1.toArray(new File[l1.size()]);
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
    public View getView(
            final int i,
            View convertView,
            ViewGroup viewGroup) {

        ViewHolder holder;
        if (convertView == null) {
            // 没有可复用，需要创建
            // 开销很大 加载文件、XML 解析 控件和布局的
            convertView = layoutInflater.inflate(R.layout.activity_file_item, null);

            // 每个视图项需要一个 viewHolder
            // 构造ViewHolder把View convertView传给它
            holder = new ViewHolder(convertView);

            // 视图项关联了它的视图结构
            convertView.setTag(holder);
        } else {
            // 有复用视图项，不创建，并直接获得结构
            holder = (ViewHolder) convertView.getTag();
        }

        Log.d("viewHolder", holder.id + " : " + i);

        // 加载数据
        holder.bindData(files[i]);

        //点击右侧图片打开隐藏条目
        open_hide_item(holder,i);

        //给属性增加监听器
        add_item_listener(holder,i);




        return convertView;
    }


    //点击右侧图片打开隐藏条目
    public void open_hide_item(ViewHolder holder,final int i){
        if (clickPosition == i) {
            if (holder.iv_action.isSelected()) {
                holder.iv_action.setSelected(false);
                holder.ll_hide.setVisibility(View.GONE);
                clickPosition=-1;
            } else {
                holder.iv_action.setSelected(true);
                holder.ll_hide.setVisibility(View.VISIBLE);
            }
        }
        else {
            holder.ll_hide.setVisibility(View.GONE);
            holder.iv_action.setSelected(false);
        }

        holder.iv_action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickPosition = i;
                notifyDataSetChanged();
            }
        });
    }

    //给属性增加监听器
    public void add_item_listener(final ViewHolder holder,final int i){

        holder.tv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String path = getItem(i).getPath();
                Intent intent = new Intent(context,Html_show_Activity.class);
                intent.putExtra("path",path);
                context.startActivity(intent);
                //Toast.makeText(context,"打开："+path,Toast.LENGTH_LONG).show();
            }
        });

        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dia = new AlertDialog.Builder(context)
                        .setMessage("确定删除该文档吗？")
                        .setPositiveButton("确定",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dia,int whichButton){
                                                cf.deleteSingleFile(context,getItem(i).getPath());
                                                delete_file(i);
                                                notifyDataSetChanged();
                            }})
                        .setNegativeButton("取消",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dia,int whichButton){}
                        }).create();
                dia.show();
            }
        });

        holder.tv_entity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"entity",Toast.LENGTH_LONG).show();
            }
        });

        holder.tv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"update",Toast.LENGTH_LONG).show();
            }
        });
    }

    static class ViewHolder {

        ImageView iv_file_icon;
        TextView tv_title;
        TextView tv_info;
        ImageView iv_action;
        TextView tv_date;

        TextView tv_open, tv_update, tv_delete, tv_entity;
        LinearLayout ll_hide;
        int id;


        public ViewHolder(View v) {
            iv_file_icon = (ImageView) v.findViewById(R.id.imageView_icon);
            tv_title = (TextView) v.findViewById(R.id.textView_name);
            tv_info = (TextView) v.findViewById(R.id.textView_info);
            tv_date = (TextView) v.findViewById(R.id.textView_date);
            iv_action = (ImageView) v.findViewById(R.id.imageButton_action);


            tv_open = (TextView) v.findViewById(R.id.open);
            tv_update = (TextView) v.findViewById(R.id.update);
            tv_delete = (TextView) v.findViewById(R.id.delete);
            tv_entity = (TextView) v.findViewById(R.id.entity);
            ll_hide = (LinearLayout) v.findViewById(R.id.ll_hide);

            id = counter++;
        }




        public void bindData(File file) {

            tv_title.setText(file.getName());

            tv_info.setText(formatFileSize(getFileSize(file)));

            tv_date.setText(getDate(file));
        }


        public String getDate(File file) {
            String date = new SimpleDateFormat("yy-M-d HH:mm:ss").format(new Date(file.lastModified()));
            return date;
        }

        public long getFileSize(File f){
            long l=0;
            try {
                if (f.isFile()) {
                    FileInputStream mFIS = new FileInputStream(f);
                    l = mFIS.available();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return l;

        }

        public String formatFileSize(long fSize){
            DecimalFormat df = new DecimalFormat("#.00");

            String fileSizeString = "";

            if(fSize<1024)
                fileSizeString = df.format((double) fSize) + "B";
            else if ( fSize >1024 && fSize < 1048576)
                fileSizeString = df.format((double) fSize/1024) + "K";
            else if ( fSize > 1048576 && fSize <1073741824)
                fileSizeString = df.format((double) fSize/1048756 ) + "M";
            else
                fileSizeString = df.format((double) fSize/1073741824) + "G";
            return fileSizeString;

        }
    }



}
