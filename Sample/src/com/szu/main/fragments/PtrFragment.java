package com.szu.main.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.szu.AppTest.R;
import com.szu.library.pulltorefresh.PullToRefreshListView;
import com.szu.library.utils.ToastUtils;
import com.szu.main.adapter.SimpleListAdapter;
import com.szu.main.objects.ListItemData;

/**
 * Created by lgp on 2014/7/29.
 */
public class PtrFragment extends BaseListFragment {
    private ListItemData mItemData;
    private SimpleListAdapter mAdapter;
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
        setTitle(R.string.pull_refresh_listView);
        mItemData = (ListItemData) getArguments().getSerializable("list");
        if(mItemData != null)
        {
            mAdapter = new SimpleListAdapter(getActivity(),mItemData.getList());
//            Logger.getInstance().debug(TAG,"new adapter");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        ToastUtils.show(getActivity(),mItemData.getList().get(position-1));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(mAdapter != null)
        {
            setListAdapter(mAdapter);
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
        mPullToRefreshListView = (PullToRefreshListView)inflater.inflate(R.layout.fragment_ptr,container,false);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_ptr_actions,menu);
//        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ptr_normal:
                mPullToRefreshListView.setMode(PullToRefreshListView.Mode.NORMAL);
                mPullToRefreshListView.updateFootView();
                return true;
            case R.id.ptr_auto_refresh_in_end:
                mPullToRefreshListView.setMode(PullToRefreshListView.Mode.AUTO_REFRESH_IN_END);
                mPullToRefreshListView.updateFootView();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

