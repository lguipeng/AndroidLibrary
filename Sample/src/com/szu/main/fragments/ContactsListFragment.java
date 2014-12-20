package com.szu.main.fragments;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.szu.AppTest.R;
import com.szu.main.adapter.ContactsListAdapter;

/**
 * Created by lgp on 2014/12/14.
 */
public class ContactsListFragment extends BaseListFragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private ListView mListView;
    private ContactsListAdapter mContactsListAdapter;

    private static final String[] PROJECTION =
    {
        Phone._ID,Phone.DISPLAY_NAME, Phone.NUMBER, Phone.CONTACT_ID
    };

    /**联系人显示名称**/
    public static final int PHONES_DISPLAY_NAME_INDEX = 1;

    /**电话号码**/
    public static final int PHONES_NUMBER_INDEX = 2;

    /**联系人的ID**/
    public static final int PHONES_CONTACT_ID_INDEX = 3;

    public static ContactsListFragment newInstance()
    {
        ContactsListFragment mContactsListFragment = new ContactsListFragment();
        return mContactsListFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
        setTitle(R.string.contacts_list);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContactsListAdapter = new ContactsListAdapter(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mListView  = (ListView)inflater.inflate(R.layout.fragment_cl,container,false);
        setListAdapter(mContactsListAdapter);
        return mListView;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mContactsListAdapter.changeCursor(null);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),Phone.CONTENT_URI,PROJECTION,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mContactsListAdapter.changeCursor(data);
    }
}
