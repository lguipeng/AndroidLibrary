package com.szu.main;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import com.szu.AppTest.R;
import com.szu.library.utils.Logger;
import com.szu.main.object.ListItemData;
import com.szu.main.fragments.MainMenuFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgp on 2014/7/22.
 */
public class MainActivity extends Activity {

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
        list.add("SwipeToDismissListView");
        list.add("PullToRefreshListView");
        list.add("Volley");
        mListItemData.setList(list);
    }

}
