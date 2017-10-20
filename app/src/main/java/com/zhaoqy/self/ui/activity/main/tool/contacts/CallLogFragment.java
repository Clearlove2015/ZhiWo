package com.zhaoqy.self.ui.activity.main.tool.contacts;


import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;


public class CallLogFragment extends BaseFragment {

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
        adapter = new ContactAdapter(getActivity(), list, 0);
        recyclerView.setAdapter(adapter);

        getCallLog();
    }

    private void getCallLog() {
        Uri uri = CallLog.Calls.CONTENT_URI;
        Cursor cursor = getActivity().getContentResolver().query(uri,
                new String[]{
                        CallLog.Calls.CACHED_NAME,
                        CallLog.Calls.NUMBER,
                        CallLog.Calls.TYPE,
                        CallLog.Calls.DATE,
                        CallLog.Calls.DURATION
                }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String name = cursor.getString(0);
                String number = cursor.getString(1);
                if (name == null || name.equals("")) {
                    name = number;
                }
                int type = Integer.parseInt(cursor.getString(2));
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date callDate = new Date(Long.parseLong(cursor.getString(3)));
                String date = sdf.format(callDate);
                int callDuration = Integer.parseInt(cursor.getString(4));
                int min = callDuration / 60;
                int sec = callDuration % 60;
                String duration = min + " 分 " + sec + " 秒 ";
                ContactBean item = new ContactBean();
                item.setName(name);
                item.setNumber(number);
                item.setType(type);
                item.setDate(date);
                item.setDuration(duration);
                list.add(item);
            }
        }
        getActivity().startManagingCursor(cursor);
    }
}
