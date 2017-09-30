package com.zhaoqy.self.ui.activity.drawer;

import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import butterknife.OnClick;

public class NoteActivity extends BaseToolboxActivity implements View.OnClickListener {

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_note;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.yinxiang, R.id.notepad, R.id.gitbook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.yinxiang: {
                /**
                 * 印象笔记
                 */
                break;
            }
            case R.id.notepad: {
                /**
                 * Notepad
                 */
                break;
            }
            case R.id.gitbook: {
                /**
                 * GitBook
                 */
                break;
            }
        }
    }
}
