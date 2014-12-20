package com.szu.main.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.szu.AppTest.R;
import com.szu.library.utils.ToastUtils;
import com.szu.main.adapter.DrawerListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgp on 2014/8/27.
 */
public class DrawerLayoutFragment extends BaseFragment{

    public static DrawerLayoutFragment  newInstance()
    {
        DrawerLayoutFragment drawerLayoutFragment = new DrawerLayoutFragment();
        return drawerLayoutFragment;
    }

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private List<String> lists;
    private int mCurrentTitlePosition = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_drawer,container,false);
        mDrawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) view.findViewById(R.id.left_drawer);
        init();
        return view;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        lists = new ArrayList<String>();
        lists.add(getResources().getString(R.string.hot));
        lists.add(getResources().getString(R.string.latest));
        lists.add(getResources().getString(R.string.about));

        DrawerListAdapter adapter = new DrawerListAdapter(getActivity(),lists);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToastUtils.show(getActivity(),lists.get(position));
                selectItem(position);
                mDrawerList.setItemChecked(position, true);
            }
        });
        selectItem(0);
        mDrawerList.setItemChecked(0,true);
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,R.drawable.ic_drawer,
                R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                setTitle(R.string.drawer_layout);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                setTitle(lists.get(mCurrentTitlePosition));
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        Fragment fragment = DrawerLayoutContentFragment.newInstance(lists.get(position));


        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        mDrawerList.setItemChecked(position, true);
        setTitle(lists.get(position));
        mCurrentTitlePosition = position;
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    public ActionBarDrawerToggle getmDrawerToggle() {
        return mDrawerToggle;
    }
}
