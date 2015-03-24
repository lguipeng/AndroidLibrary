package com.szu.main.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.szu.AppTest.R;
import com.szu.library.imageview.CircleImageView;

/**
 * Created by lgp on 2014/8/16.
 */
public class PivFragment extends BaseFragment{
    private CircleImageView mCircleImageView;
    public static PivFragment  newInstance()
    {
        PivFragment pivFragment = new PivFragment();
        return pivFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.power_imageView);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_piv,container,false);
        mCircleImageView = (CircleImageView)view.findViewById(R.id.circle_image);
        mCircleImageView.setImageDrawable(R.drawable.welcome04);
        return view;
    }
}
