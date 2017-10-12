package com.zhaoqy.self.ui.activity.main.album;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.bean.ImageDirectoryModel;
import com.zhaoqy.self.bean.SingleImageModel;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.util.BitmapHelper;
import com.zhaoqy.self.util.CommonUtil;
import com.zhaoqy.self.util.DpUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;


public class AlbumActivity extends BaseToolboxActivity implements View.OnClickListener,
        AbsListView.OnScrollListener, View.OnTouchListener{

    public static final String EXTRA_NUMS = "extra_nums";
    public static final int CODE_FOR_PIC_BIG = 1;
    public static final int CODE_FOR_PIC_BIG_PREVIEW = 2;
    public static final int CODE_FOR_TAKE_PIC = 3;
    public static final int CODE_FOR_WRITE_PERMISSION = 100;

    @BindView(R.id.gridview)
    GridView gridView;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.directory)
    TextView directory;
    @BindView(R.id.preview)
    TextView preview;

    /** 按时间排序的所有图片list */
    private ArrayList<SingleImageModel> allImages;
    /** 按目录排序的所有图片list */
    private ArrayList<SingleImageDirectories> imageDirectories;
    /** 选中图片的信息 */
    ArrayList<String> picklist = new ArrayList<String>();

    /** 当前显示的文件夹路径，全部-- -1 */
    private int currentShowPosition;

    private int firstVisibleItem = 0;
    private int currentState = SCROLL_STATE_IDLE;
    private int currentTouchState = MotionEvent.ACTION_UP;

    private LayoutInflater inflater = null;
    private GridViewAdapter adapter;

    //拍照的文件的文件名
    String tempPath = null;
    private MyHandler handler;

    /** 每张图片需要显示的高度和宽度 */
    private int perWidth;
    /** 选择图片的数量总数，默认为9 */
    private int picNums = 9;
    /** 当前选中的图片数量 */
    private int currentPicNums = 0;
    /** 最新一张图片的时间 */
    private long lastPicTime = 0;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_album;
    }

    @Override
    protected void initData() {
        gridView.setOnTouchListener(this);
        inflater = LayoutInflater.from(this);
        allImages = new ArrayList<>();
        imageDirectories = new ArrayList<>();
        //默认显示全部图片
        currentShowPosition = -1;
        adapter = new GridViewAdapter();
        handler = new MyHandler(this);
        getAllImages();

        //计算每张图片应该显示的宽度
        perWidth = (DpUtil.getScreenWidth(this) / 3) - 6 ;
        picNums = getIntent().getIntExtra(EXTRA_NUMS, 9);
    }

    /**
     * 6.0版本之后需要动态申请权限
     */
    private void getAllImages(){
        //使用兼容库就无需判断系统版本
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(getApplication(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteContactsPermission == PackageManager.PERMISSION_GRANTED) {
            startGetImageThread();
        } else{
            //需要弹出dialog让用户手动赋予权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CODE_FOR_WRITE_PERMISSION);
        }
    }

    /**
     * 从手机中获取所有的手机图片
     */
    private void startGetImageThread(){
        new Thread(new Runnable() {

            @Override
            public void run() {
                Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver contentResolver = getContentResolver();
                //获取jpeg和png格式的文件，并且按照时间进行倒序
                Cursor cursor = contentResolver.query(uri, null, MediaStore.Images.Media.MIME_TYPE + "=\"image/jpeg\" or " +
                        MediaStore.Images.Media.MIME_TYPE + "=\"image/png\"", null, MediaStore.Images.Media.DATE_MODIFIED+" desc");
                if (cursor != null){
                    allImages.clear();
                    while (cursor.moveToNext()){
                        SingleImageModel singleImageModel = new SingleImageModel();
                        singleImageModel.path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        try {
                            singleImageModel.date = Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)));
                        }catch (NumberFormatException e){
                            singleImageModel.date  = System.currentTimeMillis();
                        }
                        try {
                            singleImageModel.id = Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)));
                        }catch (NumberFormatException e){
                            singleImageModel.id = 0;
                        }
                        allImages.add(singleImageModel);

                        //存入按照目录分配的list
                        String path = singleImageModel.path;
                        String parentPath = new File(path).getParent();
                        putImageToParentDirectories(parentPath, path, singleImageModel.date, singleImageModel.id);
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        }).start();
    }

    private static class MyHandler extends Handler {

        WeakReference<AlbumActivity> activity = null;

        public MyHandler(AlbumActivity context){
            activity = new WeakReference<AlbumActivity>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (activity.get() == null)
                return;
            if (activity.get().gridView.getAdapter() == null){
                activity.get().gridView.setAdapter(activity.get().adapter);
            }else
                activity.get().adapter.notifyDataSetChanged();
            activity.get().gridView.setOnScrollListener(activity.get());
            super.handleMessage(msg);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CODE_FOR_WRITE_PERMISSION){
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    &&grantResults[0] == PackageManager.PERMISSION_GRANTED){
                /**
                 * 用户同意使用该权限
                 */
                startGetImageThread();
            }else{
                /**
                 * 用户不同意，向用户展示该权限作用
                 */
                if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    AlertDialog dialog = new AlertDialog.Builder(this)
                            .setMessage("该相册需要赋予访问存储的权限，不开启将无法正常工作！")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).create();
                    dialog.show();
                    return;
                }
                finish();
            }
        }
    }

    @OnClick({R.id.submit, R.id.directory, R.id.preview})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit: {
                break;
            }
            case R.id.directory: {
                break;
            }
            case R.id.preview: {
                if (currentPicNums > 0) {
                    Intent intent = new Intent();
                    intent.setClass(AlbumActivity.this, ImagePreviewActivity.class);
                    intent.putExtra(ImagePreviewActivity.EXTRA_DATA, getChoosePicFromList());
                    intent.putExtra(ImagePreviewActivity.EXTRA_ALL_PICK_DATA, picklist);
                    intent.putExtra(ImagePreviewActivity.EXTRA_CURRENT_PIC, 0);
                    intent.putExtra(ImagePreviewActivity.EXTRA_LAST_PIC, picNums - currentPicNums);
                    intent.putExtra(ImagePreviewActivity.EXTRA_TOTAL_PIC, picNums);
                    startActivityForResult(intent, CODE_FOR_PIC_BIG_PREVIEW);
                    BitmapHelper.getInstance().releaseHalfSizeCache();
                }
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        BitmapHelper.getInstance().clearCache();
        super.onBackPressed();
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        currentState = i;
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        firstVisibleItem = i;
        //保证当选择全部文件夹的时候，显示的时间为第一个图片，排除第一个拍照图片
        if (currentShowPosition == -1 && firstVisibleItem > 0)
            firstVisibleItem --;
        if(lastPicTime != getImageDirectoryModelDateFromMapById(firstVisibleItem)) {
            lastPicTime = getImageDirectoryModelDateFromMapById(firstVisibleItem);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                currentTouchState = MotionEvent.ACTION_DOWN;
                break;
            case MotionEvent.ACTION_MOVE:
                currentTouchState = MotionEvent.ACTION_MOVE;
                break;
            case MotionEvent.ACTION_UP:
                currentTouchState = MotionEvent.ACTION_UP;
                break;
        }
        return false;
    }

    /**
     * gridview适配器
     */
    private class GridViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            int size = 0;
            //如果显示全部图片,则第一项为
            if(currentShowPosition == -1){
                size = allImages.size() + 1;
            }else{
                size = imageDirectories.get(currentShowPosition).images.getImageCounts();
            }
            return size;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            //第一个要显示拍摄照片图片
            if (currentShowPosition == -1 && position == 0){
                view = inflater.inflate(R.layout.item_camera, null);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        takePic();
                    }
                });
                view.setLayoutParams(new GridView.LayoutParams(perWidth, perWidth));
                return view;
            }
            //在此处直接进行处理最好，能够省去其他部分的处理，其他部分直接可以使用原来的数据结构
            if (currentShowPosition == -1)
                position--;
            //其他部分的处理
            final String path = getImageDirectoryModelUrlFromMapById(position);
            lastPicTime = getImageDirectoryModelDateFromMapById(position);
            if(view==null || view.getTag()==null){
                view = inflater.inflate(R.layout.item_album, null);
                GridViewHolder holder = new GridViewHolder();
                holder.iv_content = (ImageView) view.findViewById(R.id.iv_content);
                holder.iv_pick_or_not = (ImageView) view.findViewById(R.id.iv_pick_or_not);
                if(picNums == 1){
                    holder.iv_pick_or_not.setVisibility(View.GONE);
                }

                OnclickListenerWithHolder listener = new OnclickListenerWithHolder(holder);
                holder.iv_content.setOnClickListener(listener);
                holder.iv_pick_or_not.setOnClickListener(listener);
                view.setTag(holder);
                //要在这进行设置，在外面设置会导致第一个项点击效果异常
                view.setLayoutParams(new GridView.LayoutParams(perWidth, perWidth));
            }
            final GridViewHolder holder = (GridViewHolder) view.getTag();
            //一定不要忘记更新position
            holder.position = position;
            //如果该图片被选中，则讲状态变为选中状态
            if (getImageDirectoryModelStateFromMapById(position)){
                holder.iv_pick_or_not.setImageResource(R.drawable.image_choose);
            }else{
                holder.iv_pick_or_not.setImageResource(R.drawable.image_not_chose);
            }
            //优化显示效果
            if(holder.iv_content.getTag() != null) {
                String remove = (String) holder.iv_content.getTag();
                BitmapHelper.getInstance().removePathFromShowlist(remove);
            }
            BitmapHelper.getInstance().addPathToShowlist(path);
            holder.iv_content.setTag(path);
            Bitmap bitmap = BitmapHelper.getInstance().getBitmap(path, perWidth, perWidth, new BitmapHelper.ILoadImageCallback() {
                @Override
                public void onLoadImageCallBack(Bitmap bitmap, String path1, Object... objects) {
                    if (bitmap == null) {
                        return;
                    }
                    BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                    View v = gridView.findViewWithTag(path1);
                    if (v != null)
                        v.setBackgroundDrawable(bd);
                }
            }, position);
            if (bitmap != null){
                BitmapDrawable bd = new BitmapDrawable(getResources(), bitmap);
                holder.iv_content.setBackgroundDrawable(bd);
            }else{
                holder.iv_content.setBackgroundResource(R.mipmap.default_img);
            }
            return view;
        }
    }

    private class GridViewHolder{
        public ImageView iv_content;
        public ImageView iv_pick_or_not;
        public int position;
    }

    /**
     * 调用系统相机进行拍照
     */
    private void takePic(){
        String name = "temp";
        if (!new File(CommonUtil.getDataPath()).exists())
            new File(CommonUtil.getDataPath()).mkdirs();
        tempPath = CommonUtil.getDataPath() + name + ".jpg";
        File file = new File(tempPath);
        try {
            if (file.exists())
                file.delete();
            file.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, CODE_FOR_TAKE_PIC);
    }

    /**
     * 根据id获取map中相对应的图片路径
     */
    private String getImageDirectoryModelUrlFromMapById(int position){
        //如果是选择的全部图片
        if(currentShowPosition == -1){
            return allImages.get(position).path;
        }else{
            return imageDirectories.get(currentShowPosition).images.getImagePath(position);
        }
    }

    /**
     * 根据id获取map中相对应的图片时间
     */
    private long getImageDirectoryModelDateFromMapById(int position){
        if (allImages.size() ==0){
            return System.currentTimeMillis();
        }
        //如果是选择的全部图片
        if(currentShowPosition == -1){
            return allImages.get(position).date;
        }else{
            return imageDirectories.get(currentShowPosition).images.getImages().get(position).date;
        }
    }

    /**
     * 根据id获取map中相对应的图片选中状态
     */
    private boolean getImageDirectoryModelStateFromMapById(int position){
        //如果是选择的全部图片
        if(currentShowPosition == -1){
            return allImages.get(position).isPicked;
        }else{
            return imageDirectories.get(currentShowPosition).images.getImagePickOrNot(position);
        }
    }

    /**
     * 转变该位置图片的选中状态
     * @param position
     */
    private void toggleImageDirectoryModelStateFromMapById(int position){
        //如果是选择的全部图片
        if(currentShowPosition == -1){
            allImages.get(position).isPicked = !allImages.get(position).isPicked;
            for (SingleImageDirectories directories : imageDirectories){
                directories.images.toggleSetImage(allImages.get(position).path);
            }
        }else{
            imageDirectories.get(currentShowPosition).images.toggleSetImage(position);
            for (SingleImageModel model : allImages){
                if (model.path.equalsIgnoreCase(imageDirectories.get(currentShowPosition).images.getImagePath(position)))
                    model.isPicked = !model.isPicked;
            }
        }
    }

    /**
     * 带holder的监听器
     */
    private class OnclickListenerWithHolder implements View.OnClickListener{
        GridViewHolder holder;

        public OnclickListenerWithHolder(GridViewHolder holder){
            this.holder = holder;
        }

        @Override
        public void onClick(View view) {
            int position = holder.position;
            int i = view.getId();
            if (i == R.id.iv_content) {
                if (picNums > 1) {
                    Intent intent = new Intent();
                    intent.setClass(AlbumActivity.this, ImagePreviewActivity.class);
                    //TODO 这里由于涉及到intent传递的数据不能太大的问题，所以如果需要，这里需要进行另外的处理，写入到内存或者写入到文件中
                    intent.putExtra(ImagePreviewActivity.EXTRA_DATA, getAllImagesFromCurrentDirectory());
                    intent.putExtra(ImagePreviewActivity.EXTRA_ALL_PICK_DATA, picklist);
                    intent.putExtra(ImagePreviewActivity.EXTRA_CURRENT_PIC, position);
                    intent.putExtra(ImagePreviewActivity.EXTRA_LAST_PIC, picNums - currentPicNums);
                    intent.putExtra(ImagePreviewActivity.EXTRA_TOTAL_PIC, picNums);
                    startActivityForResult(intent, CODE_FOR_PIC_BIG);
                    BitmapHelper.getInstance().releaseHalfSizeCache();
                }else{
                    picklist.add(getImageDirectoryModelUrlFromMapById(holder.position));
                    currentPicNums++;
                    returnDataAndClose();
                }

            } else if (i == R.id.iv_pick_or_not) {
                toggleImageDirectoryModelStateFromMapById(position);
                if (getImageDirectoryModelStateFromMapById(position)) {
                    if (currentPicNums == picNums) {
                        toggleImageDirectoryModelStateFromMapById(position);
                        Toast.makeText(AlbumActivity.this, String.format(getString(R.string.choose_pic_num_out_of_index), picNums), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    picklist.add(getImageDirectoryModelUrlFromMapById(holder.position));
                    holder.iv_pick_or_not.setImageResource(R.drawable.image_choose);
                    currentPicNums++;
                    if (currentPicNums == 1) {
                        submit.setTextColor(getResources().getColor(R.color.colorWhite));
                    }
                    preview.setText(String.format(getString(R.string.preview_with_num), currentPicNums));
                    submit.setText(String.format(getString(R.string.choose_pic_finish_with_num), currentPicNums, picNums));
                } else {
                    picklist.remove(getImageDirectoryModelUrlFromMapById(holder.position));
                    holder.iv_pick_or_not.setImageResource(R.drawable.image_not_chose);
                    currentPicNums--;
                    if (currentPicNums == 0) {
                        submit.setTextColor(getResources().getColor(R.color.colorLine));
                        submit.setText("完成");
                        preview.setText(getString(R.string.preview_without_num));
                    } else {
                        preview.setText(String.format(getString(R.string.preview_with_num), currentPicNums));
                        submit.setText(String.format(getString(R.string.choose_pic_finish_with_num), currentPicNums, picNums));
                    }
                }
                //                    adapter.notifyDataSetChanged();

            } else {
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case CODE_FOR_PIC_BIG:
            case CODE_FOR_PIC_BIG_PREVIEW:
                BitmapHelper.getInstance().resizeCache();
                if(resultCode == RESULT_OK && data != null) {
                    ArrayList<String> temp = (ArrayList<String>) data.getSerializableExtra("pick_data");
                    //如果返回的list中含该path，但是picklist不含有该path，选中
                    for (String path : temp){
                        if (!picklist.contains(path)){
                            View v = gridView.findViewWithTag(path);
                            if(v != null) {
                                ((ImageView) ((ViewGroup) (v.getParent())).findViewById(R.id.iv_pick_or_not)).setImageResource(R.drawable.image_choose);
                            }
                            setPickStateFromHashMap(path, true);
                            currentPicNums ++;
                        }
                    }
                    //如果返回的list中不含该path，但是picklist含有该path,不选中
                    for (String path : picklist){
                        if (!temp.contains(path)){
                            View v = gridView.findViewWithTag(path);
                            if(v != null) {
                                ((ImageView) ((ViewGroup) (v.getParent())).findViewById(R.id.iv_pick_or_not)).setImageResource(R.drawable.image_not_chose);
                            }
                            currentPicNums --;
                            setPickStateFromHashMap(path, false);
                        }
                    }
                    picklist = temp;
                    if (currentPicNums == 0) {
                        preview.setText(getString(R.string.preview_without_num));
                        submit.setTextColor(getResources().getColor(R.color.colorLine));
                        submit.setText("完成");
                    }
                    else {
                        submit.setText(String.format(getString(R.string.choose_pic_finish_with_num), currentPicNums, picNums));
                        submit.setTextColor(getResources().getColor(R.color.colorWhite));
                        preview.setText(String.format(getString(R.string.preview_with_num), currentPicNums));
                    }
                    boolean isFinish = data.getBooleanExtra("isFinish", false);
                    if (isFinish){
                        returnDataAndClose();
                    }
                }
                break;
            case CODE_FOR_TAKE_PIC:
                if (resultCode == RESULT_OK){
                    //临时文件的文件名
                    Toast.makeText(this, "拍照的图片 "+tempPath, Toast.LENGTH_LONG).show();

                    //扫描最新的图片进相册
                    Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri uri = Uri.fromFile(new File(tempPath));
                    intent.setData(uri);
                    sendBroadcast(intent);

                    //重新拉取最新数据库文件
                    getAllImages();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 点击完成按钮之后将图片的地址返回到上一个页面
     */
    private void returnDataAndClose(){
        BitmapHelper.getInstance().clearCache();
        if (currentPicNums == 0){
            Toast.makeText(this, getString(R.string.not_choose_any_pick), Toast.LENGTH_SHORT).show();
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String model : picklist){
            sb.append(model+"\n");
        }
        TextView textview = new TextView(this);
        textview.setText(sb);
        Dialog dialog = new Dialog(this);
        dialog.setTitle("结果");
        dialog.setContentView(textview);
        dialog.show();
        if (picNums == 1)
            picklist.clear();
        //        Intent data = new Intent();
        //        data.putExtra("data", list);
        //        setResult(RESULT_OK, data);
        //        finish();
    }

    /**
     * 设置该图片的选中状态
     */
    private void setPickStateFromHashMap(String path, boolean isPick){
        for (SingleImageDirectories directories : imageDirectories){
            if(isPick)
                directories.images.setImage(path);
            else
                directories.images.unsetImage(path);
        }
        for (SingleImageModel model : allImages){
            if (model.path.equalsIgnoreCase(path))
                model.isPicked = isPick;
        }
    }

    /**
     * 获取所有的选中图片
     */
    private ArrayList<SingleImageModel> getChoosePicFromList(){
        ArrayList<SingleImageModel> list = new ArrayList<SingleImageModel>();
        for (String path : picklist){
            SingleImageModel model = new SingleImageModel(path, true, 0, 0);
            list.add(model);
        }
        return list;
    }

    /**
     * 获取当前选中文件夹中给的所有图片
     */
    private ArrayList<SingleImageModel> getAllImagesFromCurrentDirectory(){
        ArrayList<SingleImageModel> list = new ArrayList<SingleImageModel>();
        if(currentShowPosition == -1) {
            for (SingleImageModel model : allImages) {
                list.add(model);
            }
        }else{
            for (SingleImageModel model : imageDirectories.get(currentShowPosition).images.getImages()) {
                list.add(model);
            }
        }
        return list;
    }

    /**
     * 将图片插入到对应parentPath路径的文件夹中
     */
    private void putImageToParentDirectories(String parentPath, String path, long date, long id){
        ImageDirectoryModel model = getModelFromKey(parentPath);
        if (model == null){
            model = new ImageDirectoryModel();
            SingleImageDirectories directories = new SingleImageDirectories();
            directories.images = model;
            directories.directoryPath = parentPath;
            imageDirectories.add(directories);
        }
        model.addImage(path, date, id);
    }

    private ImageDirectoryModel getModelFromKey(String path){
        for (SingleImageDirectories directories : imageDirectories){
            if(directories.directoryPath.equalsIgnoreCase(path)){
                return directories.images;
            }
        }
        return null;
    }

    /**
     * 一个文件夹中的图片数据实体
     */
    private class SingleImageDirectories{
        /** 父目录的路径 */
        public String directoryPath;
        /** 目录下的所有图片实体 */
        public ImageDirectoryModel images;
    }
}
