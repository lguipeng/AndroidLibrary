package com.szu.main;

import android.app.FragmentManager;
import android.os.Bundle;
import com.szu.AppTest.R;
import com.szu.library.utils.Logger;
import com.szu.main.objects.ListItemData;
import com.szu.main.fragments.MainMenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgp on 2014/7/22.
 */
public class MainActivity extends BaseActivity {

    private final String TAG = "MainActivity";
    private MainMenuFragment mainMenuFragment = null;
    private ListItemData mListItemData;
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        addMainFragment();
    }

    private void addMainFragment()
    {
        initItemData();
        fragmentManager = getFragmentManager();
        mainMenuFragment = (MainMenuFragment)fragmentManager.findFragmentByTag("main");
        if(mainMenuFragment == null)
        {
            mainMenuFragment = MainMenuFragment.newInstance(mListItemData);
            Logger.getInstance().debug(TAG,"fragment is null");
        }
        fragmentManager.beginTransaction().add(R.id.main,mainMenuFragment,"main").commit();
    }

    private void initItemData()
    {
        mListItemData = new ListItemData();
        List<String> list = new ArrayList<String>();
        list.add(getResources().getString(R.string.swipe_dismiss_listView));
        list.add(getResources().getString(R.string.pull_refresh_listView));
        list.add(getResources().getString(R.string.volley));
        list.add(getResources().getString(R.string.power_imageView));
        list.add(getResources().getString(R.string.drawer_layout));
        list.add(getResources().getString(R.string.contacts_list));
        mListItemData.setList(list);
    }

}
