package com.szu.main.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.szu.AppTest.R;
import com.szu.library.textview.Titanic;
import com.szu.library.textview.TitanicTextView;

/**
 * Created by lgp on 2014/8/27.
 */
public class DrawerLayoutContentFragment extends Fragment{
    public static final String CONTENT_TEXT_KEY = "content_text_key";
    private String text;
    private TitanicTextView mTitanicText;
    private Titanic mTitanic;
    public static DrawerLayoutContentFragment  newInstance(String text)
    {
        DrawerLayoutContentFragment drawerLayoutContentFragment = new DrawerLayoutContentFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CONTENT_TEXT_KEY,text);
        drawerLayoutContentFragment.setArguments(bundle);
        return drawerLayoutContentFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null)
        {
           text = bundle.getString(CONTENT_TEXT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drawerlayout_content,container,false);
        mTitanicText  = (TitanicTextView) view.findViewById(R.id.textView);
        mTitanicText.setText(text);
        mTitanic = new Titanic();
        mTitanic.start(mTitanicText);
        return view;
    }
}
