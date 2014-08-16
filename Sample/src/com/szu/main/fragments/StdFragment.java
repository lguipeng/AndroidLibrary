package com.szu.main.fragments;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.szu.AppTest.R;
import com.szu.library.swipetodismiss.SwipeToDismissListView;
import com.szu.library.utils.Logger;
import com.szu.main.adapter.ListAdapter;
import com.szu.main.object.ListItemData;

/**
 * Created by lgp on 2014/7/29.
 */
public class StdFragment extends ListFragment{
    private final String TAG = "StdFragment";
    private ListItemData mItemData;
    private ListAdapter mAdapter;
    private SwipeToDismissListView mSwipeToDismissListView;

    public static StdFragment newInstance(ListItemData data)
    {
        StdFragment fragment = new StdFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list",data);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mItemData = (ListItemData) getArguments().getSerializable("list");
        if(mItemData != null)
        {
            mAdapter = new ListAdapter(getActivity(),mItemData.getList());
            Logger.getInstance().debug(TAG,"new adapter");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), mItemData.getList().get(position), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mAdapter != null)
        {
            setListAdapter(mAdapter);
            Logger.getInstance().debug(TAG,"setListAdapter");
        }
        mSwipeToDismissListView.setOnDismissListener(new SwipeToDismissListView.OnDismissListener() {
            @Override
            public void onDismiss(int index) {
                Toast.makeText(getActivity(), mItemData.getList().get(index), Toast.LENGTH_SHORT).show();
                mItemData.getList().remove(index);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mSwipeToDismissListView = (SwipeToDismissListView)inflater.inflate(R.layout.fragment_std,container,false);
        return mSwipeToDismissListView;
    }
}
