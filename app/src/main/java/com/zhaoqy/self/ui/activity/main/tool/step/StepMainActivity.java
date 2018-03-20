package com.zhaoqy.self.ui.activity.main.tool.step;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.tool.step.service.StepService;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.util.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class StepMainActivity extends BaseBarActivity implements View.OnClickListener{

    @BindView(R.id.stepArcView)
    StepArcView stepArcView;

    private boolean isBind = false;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_step_main;
    }

    @Override
    protected void initData() {
        //获取用户设置的计划锻炼步数，没有设置过的话默认7000
        int planWalk_QTY = SPUtil.getInt("planWalk_QTY", 7000);
        //设置当前步数为0
        stepArcView.setCurrentCount(planWalk_QTY, 0);
        setupService();
    }

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            //设置初始化数据
            int planWalk_QTY = SPUtil.getInt("planWalk_QTY", 7000);
            stepArcView.setCurrentCount(planWalk_QTY, stepService.getStepCount());

            //设置步数监听回调
            stepService.registerCallback(new UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    int planWalk_QTY = SPUtil.getInt("planWalk_QTY", 7000);
                    stepArcView.setCurrentCount(planWalk_QTY, stepCount);
                }
            });
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @OnClick({R.id.tv_set, R.id.tv_data})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_set: {
                /**
                 * 设置锻炼计划
                 */
                Intent intent = new Intent(this, SetPlanActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.tv_data: {
                /**
                 * 查看历史步数
                 */
                Intent intent = new Intent(this, SetpHistoryActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }
}
