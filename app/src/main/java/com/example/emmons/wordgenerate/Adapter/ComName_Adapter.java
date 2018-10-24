package com.example.emmons.wordgenerate.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.emmons.wordgenerate.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emmons on 2018/10/16 0016.
 * 公司名称列表Adapter
 */

public class ComName_Adapter extends BaseAdapter {
    int currentNum = -1;
    private Context mContext;
    private List<Person> mDatas;

    public ComName_Adapter(Context context,List<Person> datas) {

        mContext = context;
        mDatas = new ArrayList<Person>();
        mDatas.addAll(datas);
    }

    public void setDatas(List<Person> datas){
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Person getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_comname_chosebox, parent, false);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);

        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        if (mDatas.get(position).isChecked()) {
            mViewHolder.mCbCheckbox.setChecked(true);
        } else {
            mViewHolder.mCbCheckbox.setChecked(false);
        }

        mViewHolder.mTvTitle.setText(mDatas.get(position).getTitle());


        return convertView;

    }

    static class ViewHolder {
        TextView mTvTitle;
        CheckBox mCbCheckbox;
        RelativeLayout mR1;

        ViewHolder(View view) {
            mR1 = (RelativeLayout) view.findViewById(R.id.Yinhuan_tv_comname_r1);
            mTvTitle = (TextView)view.findViewById(R.id.Yinhuan_tv_comname_item);
            mCbCheckbox = (CheckBox)view.findViewById(R.id.Yinhuan_cb_comname_checkbox);
        }
    }
}
