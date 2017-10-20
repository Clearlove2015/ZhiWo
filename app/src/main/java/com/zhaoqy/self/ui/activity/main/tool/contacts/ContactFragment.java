package com.zhaoqy.self.ui.activity.main.tool.contacts;


import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class ContactFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ContactAdapter adapter;
    private LinearLayoutManager layoutManager;
    private List<ContactBean> list = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(getActivity(), list, 2);
        recyclerView.setAdapter(adapter);

        getContacts();
    }

    private void getContacts() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getActivity().getContentResolver().query(uri,
                new String[]{
                        ContactsContract.PhoneLookup.DISPLAY_NAME,
                        "data1",
                        "sort_key"
                }, null, null, "sort_key");
        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                ContactBean item = new ContactBean();
                item.setName(name);
                item.setNumber(number);
                list.add(item);
            }
        }
        getActivity().startManagingCursor(cursor);
    }
}
