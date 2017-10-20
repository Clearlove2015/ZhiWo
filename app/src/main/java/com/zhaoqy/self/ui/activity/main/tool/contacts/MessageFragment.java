package com.zhaoqy.self.ui.activity.main.tool.contacts;


import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


public class MessageFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ContactAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Map<String, List<MessageBean>> messages = new HashMap<>();
    private List<ContactBean> contacts = new ArrayList<>();

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initData() {
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ContactAdapter(getActivity(), contacts, 2);
        recyclerView.setAdapter(adapter);

        getMessages();
        getContacts();
    }

    private void getMessages() {
        Uri uri = Uri.parse("content://sms/");
        Cursor cursor = getActivity().getContentResolver().query(uri,
                new String[]{
                        "_id",
                        "address",
                        "person",
                        "body",
                        "date",
                        "type"
                }, null, null, "date desc");
        if (cursor != null) {
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.moveToPosition(i);
                String number = cursor.getString(1);
                String body = cursor.getString(3);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date msgDate = new Date(Long.parseLong(cursor.getString(4)));
                String date = sdf.format(msgDate);
                int type = Integer.parseInt(cursor.getString(5));
                MessageBean item = new MessageBean();
                item.setContent(body);
                item.setNumber(number);
                item.setType(type);
                item.setDate(date);
                if (messages.get(number) != null) {
                    messages.get(number).add(item);
                } else {
                    List<MessageBean> items = new ArrayList<MessageBean>();
                    items.add(item);
                    messages.put(number, items);
                }
            }
        }
        getActivity().startManagingCursor(cursor);
    }

    private void getContacts() {
        Iterator iter = messages.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String number = (String) entry.getKey();
            List<MessageBean> message = (List<MessageBean>) entry.getValue();
            ContactBean contact = new ContactBean();
            contact.setMsgs(message);
            contact.setNumber(number);
            contact.setName(number);

            if (message.get(0).getContent().contains("【") && message.get(0).getContent().contains("】")) {
                contact.setName(message.get(0).getContent().split("【")[1].split("】")[0]);
            }
            if (number != null && !number.equals("")) {
                contacts.add(contact);
            }
        }
    }
}
