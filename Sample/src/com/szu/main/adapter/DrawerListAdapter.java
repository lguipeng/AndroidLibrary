package com.szu.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.szu.AppTest.R;

import java.util.List;

/**
 * Created by lgp on 2014/8/27.
 */
public class DrawerListAdapter  extends BaseAdapter {
    private List<String> lists;
    private Context mContext;

    public DrawerListAdapter(Context mContext, List<String> lists) {
        this.mContext = mContext;
        this.lists = lists;
    }
    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public Object getItem(int position) {
        return lists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView==null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.drawer_listview_item,null);
            holder =new Holder();
            holder.textView =(TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else
        {
            holder =(Holder)convertView.getTag();
        }
        holder.textView.setText(lists.get(position));
        return convertView;
    }
    private class Holder {
        TextView textView;
    }

}
