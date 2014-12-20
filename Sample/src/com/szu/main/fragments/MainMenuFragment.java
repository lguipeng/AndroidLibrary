package com.szu.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.szu.main.FunctionActivity;
import com.szu.main.adapter.SimpleListAdapter;
import com.szu.main.objects.ListItemData;

/**
 * Created by lgp on 2014/7/29.
 */
public class MainMenuFragment  extends BaseListFragment{
    private ListItemData mItemData;
    private SimpleListAdapter mAdapter;

    public static MainMenuFragment newInstance(ListItemData data)
    {
        MainMenuFragment fragment = new MainMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",data);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        mItemData = (ListItemData) getArguments().getSerializable("list");
        if(mItemData != null)
        {
            mAdapter = new SimpleListAdapter(getActivity(),mItemData.getList());
//            Logger.getInstance().debug(TAG,"new adapter");
        }

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mAdapter != null)
        {
            setListAdapter(mAdapter);
//            Logger.getInstance().debug(TAG,"setListAdapter");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), mItemData.getList().get(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), FunctionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater,container,savedInstanceState);
    }


}
