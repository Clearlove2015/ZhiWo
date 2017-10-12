package com.zhaoqy.self.ui.activity.main.calendar.decorator;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.zhaoqy.self.ui.activity.main.calendar.span.LunarSpan;

public class LunarDecorator implements DayViewDecorator {

    private String year;
    private String month;

    public LunarDecorator(String year, String month) {
        this.year = year;
        this.month = month;
    }

    /**
     * 这个方法是先于 decorate() 执行的
     * @param day
     * @return
     */
    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new LunarSpan(year, month));
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}