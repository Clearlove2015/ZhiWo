package com.zhaoqy.self.ui.activity.setting.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.adapter.BookAdapter;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;
import com.zhaoqy.self.ui.dialog.ProgressDialog;
import com.zhaoqy.self.util.AppInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class AboutActivity extends BaseToolboxActivity implements BookAdapter.OnItemClickListener {

    @BindView(R.id.vername)
    TextView vername;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private BookAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<String> mDatas;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_about;
    }

    @Override
    protected void initData() {
        String verName = AppInfo.getVerName(this);
        vername.setText("版本号：" + verName);

        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mDatas = new ArrayList<>();
        mDatas.addAll(Arrays.asList(getResources().getStringArray(R.array.about)));
        mAdapter = new BookAdapter(mDatas);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case 0: {
                /**
                 * 项目主页
                 */
                Intent intent = new Intent(this, GithubActivity.class);
                intent.putExtra(GithubActivity.GITHUB_KEY, -1);
                startActivity(intent);
                break;
            }
            case 1: {
                /**
                 * 检查更新
                 */
                String str = getResources().getString(
                        R.string.tip_checking);
                ProgressDialog.showProgress(this, str, null);

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        if (ProgressDialog.isShowProgress()) {
                            ProgressDialog.cancelProgress();
                        }
                        Toast.makeText(AboutActivity.this, "已是最新版本", Toast.LENGTH_SHORT).show();
                    }
                }, 2500);
                break;
            }
            case 2: {
                /**
                 * 功能说明
                 */
                Toast.makeText(this, "该功能稍后实现", Toast.LENGTH_SHORT).show();
                break;
            }
            case 3: {
                /**
                 * 版本说明
                 */
                Toast.makeText(this, "该功能暂未实现", Toast.LENGTH_SHORT).show();
                break;
            }
            case 4: {
                /**
                 * 版权信息
                 */
                Intent intent = new Intent(this, CopyrightActivity.class);
                startActivity(intent);
                break;
            }
            case 5: {
                /**
                 * 去评分
                 */
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case 6: {
                /**
                 * 打赏
                 */
                Intent intent = new Intent(this, AwardActivity.class);
                startActivity(intent);
                break;
            }
            case 7: {
                /**
                 * 分享好友
                 */
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "分享给好友");
                intent = Intent.createChooser(intent, "请选择分享方式");
                startActivity(intent);
                break;
            }
            case 8: {
                /**
                 * 致谢
                 */
                Intent intent = new Intent(this, ThanksActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {
        Toast.makeText(this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
    }
}
