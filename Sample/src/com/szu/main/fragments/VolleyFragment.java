package com.szu.main.fragments;

import android.app.Fragment;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.szu.AppTest.R;
import com.szu.main.cache.BitmapCache;

/**
 * Created by lgp on 2014/8/3.
 */
public class VolleyFragment extends Fragment{
    private TextView mTextView;
    private ImageView mImageView;
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
        volleyStringTest();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mRequestQueue != null)
        {
            mRequestQueue.stop();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View  view  = inflater.inflate(R.layout.fragment_volley,container,false);
        mTextView = (TextView) view.findViewById(R.id.text);
        mImageView = (ImageView) view.findViewById(R.id.image);
        return view;
    }

    private void volleyStringTest()
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
            String stringUrl = "http://www.baidu.com";
            StringRequest stringRequest = new StringRequest(Request.Method.GET,stringUrl,new Response.Listener<String>() {
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

            ImageLoader imageLoader = new ImageLoader(mRequestQueue,new BitmapCache(getActivity()));
            String imageUrl = "http://www.baidu.com/img/baidu_sylogo1.gif";
            imageLoader.get(imageUrl , new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    if(!isImmediate)
                    {
                        mImageView.setImageBitmap(response.getBitmap());
                        mImageView.clearAnimation();
                        mImageView.startAnimation(getSimpleAnimation());
                    }
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    mImageView.setImageResource(R.drawable.ic_launcher);
                }
            });
        }
    }

    private Animation getSimpleAnimation (long duration)
    {
         AlphaAnimation simpleImageAnimation = new AlphaAnimation(0,1);
        simpleImageAnimation.setDuration(duration);
         return simpleImageAnimation;
    }

    private Animation getSimpleAnimation ()
    {
        return  getSimpleAnimation(2000);
    }
}
