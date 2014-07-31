package com.szu.main.fragments;

import android.app.ListFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
import com.szu.AppTest.R;
import com.szu.library.pulltorefresh.PullToRefreshListView;
import com.szu.library.utils.Logger;
import com.szu.main.adapter.ListAdapter;
import com.szu.main.object.ListItemData;

/**
 * Created by lgp on 2014/7/29.
 */
public class PtrFragment extends ListFragment {
    private final String TAG = "PtrFragment";
    private ListItemData mItemData;
    private ListAdapter mAdapter;
    private PullToRefreshListView mPullToRefreshListView;

    public static PtrFragment newInstance(ListItemData data)
    {
        PtrFragment fragment = new PtrFragment();
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
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            @Override
            public void OnRefresh(PullToRefreshListView.Orientation orientation) {
                new Task().execute(orientation);
            }

            @Override
            public void OnRefreshComplete() {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPullToRefreshListView = (PullToRefreshListView)inflater.inflate(R.layout.ptr_layout,container,false);
        return mPullToRefreshListView;
    }

    class Task extends AsyncTask<PullToRefreshListView.Orientation , Integer , String>
    {
        private PullToRefreshListView.Orientation mOrientation;
        @Override
        protected String doInBackground(PullToRefreshListView.Orientation... params) {
            mOrientation = params[0];
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            return "item"+(mItemData.getList().size());
        }

        @Override
        protected void onPostExecute(String s) {
            if(mOrientation == PullToRefreshListView.Orientation.FROM_START)
            {
                mItemData.getList().add(0, s);
            }else if(mOrientation == PullToRefreshListView.Orientation.FROM_END)
            {
                mItemData.getList().add(s);
            }

            mPullToRefreshListView.setOnRefreshDone();
            mAdapter.notifyDataSetChanged();

        }
    }
}

