package com.example.wpg;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.wpg.ItemSave.Item;
import com.example.wpg.ItemSave.ItemDao;
import com.example.wpg.ItemSave.ItemDatabase;
import com.example.wpg.ItemSave.ItemTool;
import com.example.wpg.entity.SlideDialog;
import com.example.wpg.utils.switchDate;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class addItem extends AppCompatActivity {

    private static final String TAG = "addItem";
    private int datePickerType = 1;
    private int timeoutPickerType = 2;
    private int remindPickerType = 3;

    TextView birthDateTextView;
    TextView expirationDateTextView;
    private long remindDate = 0;


    // 获取当前日期
    Calendar calendar = Calendar.getInstance();
    int afterMonth = calendar.get(Calendar.MONTH) + 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        Toolbar toolbar = (Toolbar) findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        initDate();

        birthDateTextView = findViewById(R.id.birthDate_text);
        expirationDateTextView = findViewById(R.id.expirationDate_text);
        ImageButton cancel_btn = findViewById(R.id.no_save_Button);
        ImageButton save_btn = findViewById(R.id.save_Button);
        EditText editTextView = findViewById(R.id.add_editText);


        cancel_btn.setOnClickListener(v -> { onBackPressed(); });
        save_btn.setOnClickListener(v ->{
            String item_name = editTextView.getText().toString();
            long birthDate = switchDate.switchTimestamp(birthDateTextView.getText().toString());
            long expirationDate = switchDate.switchTimestamp(expirationDateTextView.getText().toString());

            // 这里的存储 随便做做 一般 这类信息 都应该存储到后端 然后 通过API 调用 重新获取
            Item item = new Item();
            item.setName(item_name);
            item.setBirthDate(birthDate);
            item.setExpirationDate(expirationDate);
            item.setRemindDate(remindDate);
            new ItemTool.InsertItemTask(this).execute(item);
            onBackPressed();
        });
    }


    private void initDate() {

        // 获取当前日期
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int sixAfterMonth = calendar.get(Calendar.MONTH) + 7;
        int timeoutYear = currentYear;

        if (afterMonth > 12){
            sixAfterMonth -= 12;
            timeoutYear += 1;
        }

        TextView dateText = findViewById(R.id.birthDate_text);
        TextView timeoutText = findViewById(R.id.expirationDate_text);

        dateText.setText(formatDateString((currentYear + "-" + currentMonth + "-" + currentDay)));
        timeoutText.setText(formatDateString((timeoutYear + "-" + sixAfterMonth + "-" + currentDay)));

        // dialog 型的日期选择器
        View Date_layout = findViewById(R.id.birthDate_ly);
        View Timeout_layout = findViewById(R.id.timeout_ly);
        Switch Remind_switch = findViewById(R.id.remind_switch);

        Date_layout.setOnClickListener(v -> {
            handleButtonClick(datePickerType, Remind_switch);
        });

        Timeout_layout.setOnClickListener(v -> {
            handleButtonClick(timeoutPickerType, Remind_switch);
        });

        Remind_switch.setOnClickListener(v -> {
            if (Remind_switch.isChecked()) {
                handleButtonClick(remindPickerType, Remind_switch);
            }else {
                remindDate = 0; // 如果关闭switch 将提醒日期清空
            }
        });
    }

    private void handleButtonClick(int DialogType, Switch Remind_switch){
        SlideDialog dialog = new SlideDialog(addItem.this, DialogType);
        dialog.show();
        Button btnSave = dialog.findViewById(R.id.picker_save);
        Button btnCancel = dialog.findViewById(R.id.picker_cancel);

        switch (DialogType) {
            case 1:
                btnSave.setOnClickListener(v -> {
                    String date = dialog.datePicker_finish();
                    birthDateTextView.setText(date);
                    dialog.dismiss();
                });
                break;
            case 2:
                btnSave.setOnClickListener(v -> {
                    int days = dialog.timeoutPicker_finish();
                    long birthDate = switchDate.switchTimestamp(birthDateTextView.getText().toString());

                    long date = birthDate + TimeUnit.DAYS.toMillis(days);
                    String dateString = switchDate.convertTimestampToString(date);

                    expirationDateTextView.setText(dateString);
                    dialog.dismiss();
                });
                break;
            case 3:
                btnSave.setOnClickListener(v -> {
                    Remind_switch.setChecked(true);
                    int Tyep = dialog.remindPicker_finish();
                    long date = getRemindDate(Tyep, expirationDateTextView.getText().toString());
                    remindDate = date;
                    dialog.dismiss();
                });
                break;
        }

        btnCancel.setOnClickListener(v -> {
            Remind_switch.setChecked(false);
            dialog.dismiss();
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (remindDate == 0){
                    Remind_switch.setChecked(false);
                }
            }
        });
    }

    public static String formatDateString(String inputDate) {
        String date = switchDate.convertTimestampToString(switchDate.switchTimestamp(inputDate));
        return date;
    }

    private static long getRemindDate(int Type, String date){
        long timestamp = switchDate.switchTimestamp(date);
        switch (Type) {
            case 0:{ break; }
            case 1:{
                timestamp = timestamp - TimeUnit.DAYS.toMillis(1);
                break;
            }
            case 2:{
                timestamp = timestamp - TimeUnit.DAYS.toMillis(2);
                break;
            }
            case 3:{
                timestamp = timestamp - TimeUnit.DAYS.toMillis(7);
                break;
            }
            case 4:{
                timestamp = timestamp - TimeUnit.DAYS.toMillis(30);
                break;
            }
        }
        return timestamp;
    }

}

