package com.szu.main.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szu.AppTest.R;

/**
 * Created by lgp on 2014/8/16.
 */
public class PivFragment extends Fragment{
    public static PivFragment  newInstance()
    {
        PivFragment pivFragment = new PivFragment();
        return pivFragment;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_piv,container,false);
    }
}
