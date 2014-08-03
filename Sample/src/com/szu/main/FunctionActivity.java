package com.szu.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import com.szu.AppTest.R;
import com.szu.main.fragments.VolleyFragment;
import com.szu.main.object.ListItemData;
import com.szu.main.fragments.PtrFragment;
import com.szu.main.fragments.StdFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgp on 2014/7/29.
 */
public class FunctionActivity extends Activity{
    private final String TAG = "FunctionActivity";
    private FragmentManager fragmentManager;
    private Fragment mFragment;
    private ListItemData mItemData;

    private int position = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.function_activity);
        position = getIntent().getExtras().getInt("position");
        fragmentManager = getFragmentManager();
        function(position);
    }

    private void init()
    {

        mItemData = new ListItemData();
        List<String> list = new ArrayList<String>();
        for(int i=0 ;i<20 ;i++)
        {
            list.add("item"+i);
        }
        mItemData.setList(list);
    }

    private void function(int position)
    {
        switch (position)
        {
            case 0 :
                init();
                mFragment = StdFragment.newInstance(mItemData);
                break;
            case 1 :
                init();
                mFragment = PtrFragment.newInstance(mItemData);
                fragmentManager.beginTransaction().add(R.id.function,mFragment).commit();
                break;
            case 2:
                mFragment = VolleyFragment.newInstance();
                break;
            default:
                break;
        }
        fragmentManager.beginTransaction().add(R.id.function,mFragment).commit();
    }
}
