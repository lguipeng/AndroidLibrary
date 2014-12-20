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
 * Created by lgp on 2014/7/24.
 */
public class SimpleListAdapter extends BaseAdapter {
    private List<String> lists;
    private Context mContext;

    public SimpleListAdapter(Context mContext, List<String> lists) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view==null)
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.simple_listview_item,null);
            holder =new Holder();
            holder.textView =(TextView)view.findViewById(R.id.textView);
            view.setTag(holder);
        }else
        {
            holder =(Holder)view.getTag();
        }
        holder.textView.setText(lists.get(position));
        return view;
    }
    private class Holder {
        TextView textView;
    }
}
