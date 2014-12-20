package com.szu.main.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.szu.AppTest.R;
import com.szu.main.fragments.ContactsListFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by lgp on 2014/12/14.
 */
public class ContactsListAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflater;


    public ContactsListAdapter(Context context) {
        super(context, null, false);
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        Holder holder = getHolder(view);
        StringBuilder sb = new StringBuilder();
        sb.append(cursor.getString(ContactsListFragment.PHONES_DISPLAY_NAME_INDEX)+":");
        sb.append(cursor.getString(ContactsListFragment.PHONES_NUMBER_INDEX));
        holder.contact.setText(sb.toString());
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mLayoutInflater.inflate(R.layout.simple_listview_item,parent, false);
    }

    private Holder getHolder(final View view)
    {
        Holder holder = (Holder)view.getTag();
        if (holder == null)
        {
            holder = new Holder(view);
            holder.contact = (TextView)view.findViewById(R.id.textView);
            view.setTag(holder);
        }
        return holder;
    }
    static class Holder {
        TextView contact;
        public Holder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
