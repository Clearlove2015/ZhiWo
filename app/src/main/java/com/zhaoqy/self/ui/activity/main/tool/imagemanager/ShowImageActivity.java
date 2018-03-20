package com.zhaoqy.self.ui.activity.main.tool.imagemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.util.FileUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

public class ShowImageActivity extends BaseBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.gridView)
    GridView gridView;

    private ArrayList<String> list;
    private ChildAdapter adapter;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        title = getIntent().getStringExtra("folderName");
        list = getIntent().getStringArrayListExtra("fileList");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_show_image;
    }

    @Override
    protected void initData() {
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
        mTitle.setText(title);
        adapter = new ChildAdapter(this, list, gridView);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent intent = new Intent();
        intent.setClass(ShowImageActivity.this, GalleryActivity.class);
        intent.putStringArrayListExtra(GalleryActivity.EXTRA_IMAGE_LIST, list);
        intent.putExtra(GalleryActivity.EXTRA_IMAGE_POSITION, position);
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

        final String path = list.get(position);
        final File file = new  File(path);
        String size = FileUtils.format(file.length());
        String desc = "<p><span>文件名:</span><span style=\"word-break:break-all\">" + file.getName() + "</span></p>" +
                "<p><span>图片大小:</span><span style=\"word-break:break-all;\">" + size + "</span></p>" +
                "<p><span>图片路径:</span><span style=\"word-break:break-all\">" + path + "</span></p>";

        return true;
    }
}
