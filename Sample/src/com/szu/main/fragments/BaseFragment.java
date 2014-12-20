package com.szu.main.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.szu.AppTest.R;
import com.szu.library.utils.ToastUtils;

/**
 * Created by lgp on 2014/8/28.
 */
public class BaseFragment extends Fragment{
    protected final String TAG = getClass().getSimpleName();
    protected ActionBar actionBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        actionBar = getActivity().getActionBar();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_actions, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                return true;
            case android.R.id.home:
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void setTitle(CharSequence title)
    {
        actionBar.setTitle(title);
    }

    protected void setTitle(int title)
    {
        actionBar.setTitle(title);
    }
}
