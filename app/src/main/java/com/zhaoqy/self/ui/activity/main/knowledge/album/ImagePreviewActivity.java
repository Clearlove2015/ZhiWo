package com.zhaoqy.self.ui.activity.main.knowledge.album;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.bean.SingleImageModel;
import com.zhaoqy.self.ui.adapter.PreviewPageAdapter;
import com.zhaoqy.self.ui.base.BaseBarActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ImagePreviewActivity extends BaseBarActivity implements ViewPager.OnPageChangeListener, View.OnClickListener{

    /** 选择的照片文件夹 */
    public final static String EXTRA_DATA = "extra_data";
    /** 所有被选中的图片 */
    public final static String EXTRA_ALL_PICK_DATA = "extra_pick_data";
    /** 当前被选中的照片 */
    public final static String EXTRA_CURRENT_PIC = "extra_current_pic";
    /** 剩余的可选择照片 */
    public final static String EXTRA_LAST_PIC = "extra_last_pic";
    /** 总的照片 */
    public final static String EXTRA_TOTAL_PIC = "extra_total_pic";

    @BindView(R.id.page)
    TextView page;
    @BindView(R.id.submit)
    TextView submit;
    @BindView(R.id.vp_content)
    ViewPager viewPager;
    @BindView(R.id.choose)
    ImageView choose;

    PreviewPageAdapter adapter;
    ArrayList<SingleImageModel> allimages;
    ArrayList<String> picklist;
    /** 当前选中的图片 */
    private int currentPic;
    private int total_pics;
    private boolean isFinish = false;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_image_preview;
    }

    @Override
    protected void initData() {
        allimages = (ArrayList<SingleImageModel>) getIntent().getSerializableExtra(EXTRA_DATA);
        picklist = (ArrayList<String>) getIntent().getSerializableExtra(EXTRA_ALL_PICK_DATA);
        if (picklist == null) {
            picklist = new ArrayList<String>();
        }
        currentPic = getIntent().getIntExtra(EXTRA_CURRENT_PIC, 0);
        total_pics = getIntent().getIntExtra(EXTRA_TOTAL_PIC, 9);

        updatePage();
        updatePicked();
        updateChoose(currentPic);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new PreviewPageAdapter(this, viewPager, allimages);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        viewPager.setCurrentItem(currentPic);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateChoose(position);
        updatePage();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @OnClick({R.id.submit, R.id.choose})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit: {
                isFinish = true;
                finish();
                break;
            }
            case R.id.choose: {
                toggleChooseState(currentPic);
                if(getChooseStateFromList(currentPic)){
                    int pickedCount = getPickedCount();
                    if (pickedCount > total_pics) {
                        toggleChooseState(currentPic);
                        String hint = getString(R.string.choose_pic_num_out_of_index);
                        String text = String.format(hint, total_pics);
                        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                        return ;
                    }
                    picklist.add(getPathFromList(currentPic));
                    choose.setBackgroundResource(R.drawable.image_choose);
                    updatePicked();
                } else{
                    picklist.remove(getPathFromList(currentPic));
                    choose.setBackgroundResource(R.drawable.image_not_chose);
                    updatePicked();
                }
                break;
            }
        }
    }

    private void updatePage() {
        page.setText((currentPic + 1) + "/" + getImagesCount());
    }

    /**
     * 更新图片是否被选中
     */
    private void updateChoose(int position) {
        if (getChooseStateFromList(position)){
            choose.setBackgroundResource(R.drawable.image_choose);
        }else{
            choose.setBackgroundResource(R.drawable.image_not_chose);
        }
        currentPic = position;
    }

    private void updatePicked() {
        int pickedCount = getPickedCount();
        if (pickedCount > 0) {
            String hint = getString(R.string.choose_pic_finish_with_num);
            String text = String.format(hint, pickedCount, total_pics);
            submit.setText(text);
        } else {
            submit.setText("发送");
        }
    }

    private int getPickedCount() {
        if (allimages == null)
            return  0;

        int picked = 0;
        for (SingleImageModel image :allimages) {
            if (image.isPicked) {
                picked++;
            }
        }
        return picked;
    }

    /**
     * 通过位置获取该位置图片的path
     */
    private String getPathFromList(int position){
        return allimages.get(position).path;
    }

    /**
     * 通过位置获取该位置图片的选中状态
     */
    private boolean getChooseStateFromList(int position){
        return allimages.get(position).isPicked;
    }

    /**
     * 反转图片的选中状态
     */
    private void toggleChooseState(int position){
        allimages.get(position).isPicked = !allimages.get(position).isPicked;
    }

    /**
     * 获得所有的图片数量
     */
    private int getImagesCount(){
        return allimages.size();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("pick_data", picklist);
        data.putExtra("isFinish", isFinish);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
