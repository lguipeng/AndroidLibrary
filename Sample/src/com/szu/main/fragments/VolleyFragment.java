package com.szu.main.fragments;

import android.app.Fragment;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.szu.AppTest.R;

/**
 * Created by lgp on 2014/8/3.
 */
public class VolleyFragment extends Fragment{
    private TextView mTextView;
    private final String VOLLEY_TAG = "VOLLEY_STRING_TAG";
    private RequestQueue mRequestQueue;
    public static VolleyFragment  newInstance()
    {
        VolleyFragment volleyFragment = new VolleyFragment();
        return volleyFragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        volleyTest();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mRequestQueue != null)
        {
            mRequestQueue.cancelAll(VOLLEY_TAG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view  = inflater.inflate(R.layout.fragment_volley,container,false);
        mTextView = (TextView) view.findViewById(R.id.text);
        return view;
    }

    private void volleyTest()
    {
        if(getActivity() != null)
        {

            Cache cache = new DiskBasedCache(getActivity().getCacheDir(),1024*1024);
            Network network;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD)
            {
                network = new BasicNetwork(new HurlStack());
            }else
            {
                network = new BasicNetwork(new HttpClientStack(AndroidHttpClient.newInstance(" Mozilla/5.0 ")));
            }
            mRequestQueue = new RequestQueue(cache,network);
            mRequestQueue.start();
            String url = "http://www.baidu.com";
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if(mTextView != null)
                    {
                        mTextView.setText(response);
                    }
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mTextView != null)
                    {
                        mTextView.setText("get error on www.baidu.com");
                    }
                }
            });
            stringRequest.setTag(VOLLEY_TAG);
            mRequestQueue.add(stringRequest);

        }

    }
}
