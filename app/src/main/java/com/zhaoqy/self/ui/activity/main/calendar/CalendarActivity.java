package com.zhaoqy.self.ui.activity.main.calendar;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.zhaoqy.self.R;
import com.zhaoqy.self.ui.activity.main.calendar.decorator.HighlightWeekendsDecorator;
import com.zhaoqy.self.ui.activity.main.calendar.decorator.LunarDecorator;
import com.zhaoqy.self.ui.activity.main.calendar.decorator.TodayDecorator;
import com.zhaoqy.self.ui.activity.main.calendar.utils.DateUtils;
import com.zhaoqy.self.ui.activity.main.calendar.utils.Lunar;
import com.zhaoqy.self.ui.base.BaseToolboxActivity;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

public class CalendarActivity extends BaseToolboxActivity implements OnDateSelectedListener, OnMonthChangedListener {

    @BindView(R.id.calendarView_calendar)
    MaterialCalendarView widget;
    @BindView(R.id.animals_year_calendar)
    TextView mAnimalsYearCalendar;
    @BindView(R.id.distance_todaynumber_calendar)
    TextView mDistanceTodaynumberCalendar;
    @BindView(R.id.nongli_date_calendar)
    TextView mNongliDateCalendar;
    @BindView(R.id.cyclical_calendar)
    TextView mCyclical;
    @BindView(R.id.beforeorback_calendar)
    TextView mBeforeOrBack;

    @BindView(R.id.back_today_calendar)
    TextView mBackTodayCalendar;
    @BindView(R.id.month_view_calendar)
    TextView mMonthViewCalendar;
    @BindView(R.id.week_view_calendar)
    TextView mWeekViewCalendar;

    private CalendarDay today;
    private Lunar mLunar;
    public LunarDecorator mLunarDecorator;
    public String year;
    public String month;

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_calendar;
    }

    @Override
    protected void initData() {
        today = CalendarDay.today();
        initTodayInfo(today.getDate(), today.getDate());
        year = DateUtils.date2String(today.getDate(), "yyyy");
        month = DateUtils.date2String(today.getDate(), "MM");
        mLunarDecorator = new LunarDecorator(year, month);
        widget.setCurrentDate(today);

        /**
         * 设置在头和尾显示其他月的日期
         */
        widget.setShowOtherDates(MaterialCalendarView.SHOW_DEFAULTS);
        //widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);
        //当点击头和尾处的其他月的日期时，跳转到其他月
        widget.setAllowClickDaysOutsideCurrentMonth(true);
        widget.setOnDateChangedListener(this);
        widget.setOnMonthChangedListener(this);
        /**
         * 设置自定义标题style、WeekDay style
         */
        widget.setHeaderTextAppearance(R.style.MCV_HeaderText);
        widget.setWeekDayTextAppearance(R.style.MCV_WeekDayText);
        //widget.setDateTextAppearance(R.style.MCV_DateText);
        widget.addDecorators(new TodayDecorator(),//当前日期的标志
                mLunarDecorator,//显示农历
                new HighlightWeekendsDecorator()//周末日期的标志
        );
    }

    /**
     * 显示菜单栏
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.year_view:
                break;
            case R.id.skip_to_day:
                skipToDay();
                break;
            case R.id.holidays:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initTodayInfo(Date nowdate, Date selectDate) {
        mLunar = new Lunar(selectDate);
        mAnimalsYearCalendar.setText(mLunar.animalsYear() + "年");
        mNongliDateCalendar.setText(mLunar.toString());
        mCyclical.setText(mLunar.cyclical());
        int distanceDay = (int) ((selectDate.getTime() - nowdate.getTime()) / 86400000L);
        String distanceDayStr = null;
        if (distanceDay == 0) {
            mBeforeOrBack.setText("\b\b");
            distanceDayStr = "今";
        } else if (distanceDay > 0) {
            mBeforeOrBack.setText("后");
            distanceDayStr = String.valueOf(distanceDay);
        } else if (distanceDay < 0) {
            mBeforeOrBack.setText("前");
            distanceDayStr = String.valueOf(-distanceDay);
        }
        mDistanceTodaynumberCalendar.setText(distanceDayStr);
    }

    private void skipToDay() {
        Calendar cal = today.getCalendar();
        final DatePickerDialog mDialog = new DatePickerDialog(this, null,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "完成",
                new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //通过mDialog.getDatePicker()获得dialog上的DatePicker组件，然后可以获取日期信息
                DatePicker datePicker = mDialog.getDatePicker();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                CalendarDay calendarDay = CalendarDay.from(year, month, day);
                widget.setCurrentDate(calendarDay);
                widget.setSelectedDate(calendarDay);
            }
        });
        mDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        mDialog.show();
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date,
                               boolean selected) {
        initTodayInfo(today.getDate(), date.getDate());
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        mLunarDecorator.setYear(DateUtils.date2String(date.getDate(), "yyyy"));
        mLunarDecorator.setMonth(DateUtils.date2String(date.getDate(), "MM"));
        widget.invalidateDecorators();
    }

    @OnClick({R.id.back_today_calendar, R.id.month_view_calendar, R.id.week_view_calendar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_today_calendar:
                widget.setCurrentDate(today);
                break;
            case R.id.month_view_calendar:
                widget.state().edit()
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();
                break;
            case R.id.week_view_calendar:
                widget.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit();
                break;
        }
    }
}
