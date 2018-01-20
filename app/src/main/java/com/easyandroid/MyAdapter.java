package com.easyandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.easytools.tools.CheckAdapter;

import java.util.List;

/**
 * package: com.easyandroid.MyAdapter
 * author: gyc
 * description:
 * time: create at 2017/1/8 15:39
 */

public class MyAdapter extends CheckAdapter<ItemBean>{

    private List<ItemBean> items;
    private LayoutInflater inflater;

    public MyAdapter(Context context,List<ItemBean> items) {
        super(items);
        inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_check, parent, false);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.cb);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tvAge = (TextView) convertView.findViewById(R.id.tv_age);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.checkBox.setChecked(items.get(position).isChecked());
        holder.tvName.setText(items.get(position).getName());
        holder.tvAge.setText(items.get(position).getAge());
        handleCompoundButton(holder.checkBox,items.get(position));
        return convertView;
    }

    public class ViewHolder{
        CheckBox checkBox;
        TextView tvName, tvAge;
    }
}
