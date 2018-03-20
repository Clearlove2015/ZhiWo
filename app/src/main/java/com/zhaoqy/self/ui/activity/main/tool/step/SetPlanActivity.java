package com.zhaoqy.self.ui.activity.main.tool.step;

import android.app.TimePickerDialog;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TimePicker;

import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.base.BaseBarActivity;
import com.zhaoqy.self.ui.widget.VerticalCard;
import com.zhaoqy.self.util.SPUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class SetPlanActivity extends BaseBarActivity implements View.OnClickListener {

    @BindView(R.id.step_number)
    EditText step_number;
    @BindView(R.id.sc_remind)
    SwitchCompat sc_remind;
    @BindView(R.id.remind_time)
    VerticalCard remind_time;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_set_plan;
    }

    @Override
    protected void initData() {
        int planWalk_QTY = SPUtil.getInt("planWalk_QTY", 7000);
        int remind = SPUtil.getInt("remind", 1);
        String achieveTime = SPUtil.getString("achieveTime", "20:00");
        if (planWalk_QTY == 0) {
            step_number.setText(7000 + "");
        } else {
            step_number.setText(planWalk_QTY + "");
        }

        if (remind == 0) {
            sc_remind.setChecked(false);
        } else if (remind == 1) {
            sc_remind.setChecked(true);
        }

        if (!achieveTime.isEmpty()) {
            remind_time.setContent(achieveTime);
        }

        sc_remind.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

            }
        });
    }

    @OnClick({R.id.save, R.id.remind_time})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save: {
                save();
                break;
            }
            case R.id.remind_time: {
                showTimeDialog();
                break;
            }
        }
    }

    private void save() {
        if (sc_remind.isChecked()) {
            SPUtil.put("remind", 1);
        } else {
            SPUtil.put("remind", 0);
        }

        String walk_qty = step_number.getText().toString().trim();
        if (!walk_qty.isEmpty()) {
            int planWalk_QTY = Integer.parseInt(walk_qty);
            if (planWalk_QTY == 0) {
                SPUtil.put("planWalk_QTY", 7000);
            } else {
                SPUtil.put("planWalk_QTY", planWalk_QTY);
            }
        } else {
            SPUtil.put("planWalk_QTY", 7000);
        }

        String remindTime = remind_time.getContent();
        if (remindTime.isEmpty()) {
            SPUtil.put("achieveTime", "21:00");
        } else {
            SPUtil.put("achieveTime", remindTime);
        }
        finish();
    }

    private void showTimeDialog() {
        final Calendar calendar = Calendar.getInstance(Locale.CHINA);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        final DateFormat df = new SimpleDateFormat("HH:mm");
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String remaintime = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
                Date date = null;
                try {
                    date = df.parse(remaintime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (null != date) {
                    calendar.setTime(date);
                }
                remind_time.setContent(df.format(date));
            }
        }, hour, minute, true).show();
    }
}
