package com.zhaoqy.self.ui.activity.main.tool.imagemanager;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.dialog.ProgressDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ImageManagerActivity extends BaseToolboxActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener{

    private final static int SCAN_OK = 1;
    private final static int DEL_OK = 2;
    private List<String> group = new ArrayList<String>();
    private List<List<String>> child = new ArrayList<List<String>>();
    private List<ImageBean> list = new ArrayList<ImageBean>();
    private ImageManagerAdapter mAdapter;
    private int imageCount = 0;
    private int selectedGroupIndex;

    @BindView(R.id.gridView)
    GridView gridView;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_image_manager;
    }

    @Override
    protected void initData() {
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(this);
        initImages();
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中
     */
    private void initImages() {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }

        String str = getResources().getString(
                R.string.tip_loading);
        ProgressDialog.showProgress(this, str, null);

        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();
                //只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[] { "image/jpeg", "image/png" }, MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    //获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));

                    //获取该图片的父路径名
                    File file=new File(path);
                    String parentName = file.getName();
                    if(file.getParentFile()!=null){
                        parentName= file.getParentFile().getName();
                    }

                    //根据父路径名将图片放入到mGruopMap中
                    int index = group.indexOf(parentName);
                    if (index>-1) {
                        child.get(index).add(path);
                        imageCount++;
                    } else {
                        group.add(parentName);
                        List<String> childList = new ArrayList<String>();
                        childList.add(path);
                        child.add(childList);
                    }
                }
                mCursor.close();

                //通知Handler扫描图片完成
                mHandler.sendEmptyMessage(SCAN_OK);
            }
        }).start();
    }

    /**
     * 组装分组界面GridView的数据源，因为我们扫描手机的时候将图片信息放在HashMap中
     * 所以需要遍历HashMap将数据组装成List
     *
     * @return
     */
    private List<ImageBean> subGroupOfImage(){
        for(int i=0;i<group.size();i++){
            ImageBean mImageBean = new ImageBean();
            mImageBean.setFolderName(group.get(i));
            mImageBean.setImageCounts(child.get(i).size());
            //获取该组的第一张图片
            mImageBean.setTopImagePath(child.get(i).get(0));
            list.add(mImageBean);
        }
        return list;
    }

    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    //关闭进度条
                    if (ProgressDialog.isShowProgress()) {
                        ProgressDialog.cancelProgress();
                    }

                    subGroupOfImage();
                    mAdapter = new ImageManagerAdapter(ImageManagerActivity.this, list, gridView);
                    gridView.setAdapter(mAdapter);
                    //setIntCache(EnumUtil.Navigation.Image.toString(), imageCount);
                    //sendBroadcast(new Intent(ActionManager.ACTION_FILE_CHANGED));
                    break;
                case DEL_OK:
                    mAdapter.refresh();
                    //showToast("图片删除成功!");
                    //setIntCache(EnumUtil.Navigation.Image.toString(), imageCount);
                    //sendBroadcast(new Intent(ActionManager.ACTION_FILE_CHANGED));
                    break;
            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent mIntent = new Intent(this, ShowImageActivity.class);
        mIntent.putExtra("folderName", list.get(position).getFolderName());
        mIntent.putStringArrayListExtra("fileList", (ArrayList<String>)child.get(position));
        startActivity(mIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }
}
