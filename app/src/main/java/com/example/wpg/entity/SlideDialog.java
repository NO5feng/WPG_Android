package com.example.wpg.entity;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDayPicker;
import com.aigestudio.wheelpicker.widgets.WheelMonthPicker;
import com.aigestudio.wheelpicker.widgets.WheelYearPicker;
import com.example.wpg.R;
import com.example.wpg.utils.DataUtils;

import java.util.Calendar;
import java.util.List;

public class SlideDialog extends Dialog {
    private int buttonId;
    private Context mContext;

    private WheelYearPicker yearPicker;
    private WheelMonthPicker monthPicker;
    private WheelDayPicker dayPicker;

    private WheelPicker datePicker;
    private WheelPicker typePicker;

    private WheelPicker remindPicker;


    // 获取当前日期
    Calendar calendar = Calendar.getInstance();
    int currentYear = calendar.get(Calendar.YEAR);
    int currentMonth = calendar.get(Calendar.MONTH) + 1;
    int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

    public SlideDialog(Context context, int buttonId) {
        super(context);
        this.buttonId = buttonId;
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View datePick_view = inflater.inflate(R.layout.dialog_datepick, null);
        View timeOut_view = inflater.inflate(R.layout.dialog_timeout, null);
        View remind_view = inflater.inflate(R.layout.dialog_remind, null);

        // 设置对话框的布局
        switch (buttonId) {
            case 1:
                initWindow(datePick_view);
                initDatePicker(datePick_view);
                break;
            case 2:
                initWindow(timeOut_view);
                initTimeoutPicker(timeOut_view);
                break;
            case 3:
                initWindow(remind_view);
                initRemindPicker(remind_view);
                break;
        }
    }

    public String datePicker_finish() {
        int selectedYear = yearPicker.getCurrentYear();
        int selectedMonth = monthPicker.getCurrentMonth();
        int selectedDay = dayPicker.getCurrentDay();

        return (selectedYear + "-" + selectedMonth + "-" + selectedDay);
    }

    public int timeoutPicker_finish() {
        // 用于返回有效日期选择滚轮 选择的时间
        int type = typePicker.getCurrentItemPosition();
        int num = datePicker.getCurrentItemPosition();
        List<Integer> numList;
        int days = 0;
        int day = 0;
        switch (type) {
            case 0:
                numList = DataUtils.getYearData();
                day = numList.get(num);
                days = day * 365;
                break;
            case 1:
                numList = DataUtils.getMonthData();
                day = numList.get(num);
                days = day * 60;
                break;
            case 2:
                numList = DataUtils.getDayData();
                days = numList.get(num);
        }
        return days;
    }

    public int remindPicker_finish() {
        int type = remindPicker.getCurrentItemPosition();
        return type;
    }

    private void initDatePicker(View view) {
        yearPicker = view.findViewById(R.id.yearPicker);
        monthPicker = view.findViewById(R.id.monthPicker);
        dayPicker = view.findViewById(R.id.dayPicker);

        // 初始化年、月、日选择器
        yearPicker.setYearFrame(2023, 2030);
        yearPicker.setSelectedYear(currentYear);
        monthPicker.setSelectedMonth(currentMonth);
        dayPicker.setSelectedDay(currentDay);

        // 监听年月变化时候 对 日的影响
        yearPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                dayPicker.setYearAndMonth(yearPicker.getCurrentYear(), monthPicker.getCurrentMonth());
            }
        });

        monthPicker.setOnItemSelectedListener(new WheelPicker.OnItemSelectedListener() {
            @Override
            public void onItemSelected(WheelPicker picker, Object data, int position) {
                dayPicker.setYearAndMonth(yearPicker.getCurrentYear(), monthPicker.getCurrentMonth());
            }
        });
    }

    private void initTimeoutPicker(View view) {
        datePicker = view.findViewById(R.id.date_Picker);
        typePicker = view.findViewById(R.id.typePicker);

        typePicker.setData(DataUtils.getTypeItems());
        datePicker.setData(DataUtils.getMonthData());
        typePicker.setOnItemSelectedListener((picker, data, position) -> {
            switch (typePicker.getCurrentItemPosition()) {
                case 0:
                    datePicker.setData(DataUtils.getYearData());
                    break;
                case 1:
                    datePicker.setData(DataUtils.getMonthData());
                    break;
                case 2:
                    datePicker.setData(DataUtils.getDayData());
                    break;
            }
        });
    }

    private void initRemindPicker(View view) {
        remindPicker = view.findViewById(R.id.remind_picker);
        remindPicker.setData(DataUtils.getRemindData());
    }

    protected void initWindow (View view) {
        setContentView(view);

        Window window = getWindow();
        if (window != null) {
            // 设置对话框的位置为底部
            window.setGravity(Gravity.BOTTOM);
            // 设置对话框的动画效果
            window.setWindowAnimations(R.style.SlideDialogAnimation);
            // 设置对话框的宽度和高度
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }
    }

}
