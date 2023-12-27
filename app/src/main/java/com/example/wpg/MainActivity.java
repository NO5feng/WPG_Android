package com.example.wpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wpg.ItemSave.Item;
import com.example.wpg.ItemSave.ItemDatabase;
import com.example.wpg.ItemSave.ItemTool;
import com.example.wpg.utils.switchDate;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ItemTool.OnTaskCompleted {
    private static boolean isCreated = false;

    private ItemDatabase database;
    private ImageView addImageView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = Room.databaseBuilder(getApplicationContext(), ItemDatabase.class, "my-database")
                .build();
        initView();
        isCreated = true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initView();
    }

    // 页面初始化
    private void initView() {
        setContentView(R.layout.activity_main);
        // 这段的作用是将电源等 设置为黑色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.home_title);
        getAllItems();
        setClick();
    }

    private void getAllItems() {
        new ItemTool.GetAllItemsTask(this, this).execute();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onTaskCompleted(List<Item> items) {
        if (items != null && !items.isEmpty()) {
            for (Item item : items) {
                // 将itemCard 加载到 主页面
                ViewGroup mainLayout = null;
                View includedView = LayoutInflater.from(this).inflate(R.layout.home_item_layout, null, false);
                mainLayout = findViewById(R.id.item_card);
                mainLayout.addView(includedView);

                String name = item.getName();
                Long currentTimestamp = System.currentTimeMillis();
                Long ExpirationDate = item.getExpirationDate();
                Long Days = switchDate.getDaysBetweenTimestamps(currentTimestamp, ExpirationDate);

                RelativeLayout itemStateBoxView = includedView.findViewById(R.id.item_state_box);
                TextView itemStateView = includedView.findViewById(R.id.item_state);
                TextView itemDateView = includedView.findViewById(R.id.item_date);

                if (currentTimestamp <= ExpirationDate) {
                    itemStateBoxView.setBackgroundResource(R.drawable.rounded_background_right_pink);
                    itemStateView.setText(String.valueOf(Days));
                    itemStateView.setTypeface(null, Typeface.BOLD);
                    itemDateView.setText("天");
                } else {
                    itemStateBoxView.setBackgroundResource(R.drawable.rounded_background_right_yellow);
                    itemStateView.setText("已过期");
                    itemDateView.setText(Days + "天");
                }

                TextView itemNameTextView = includedView.findViewById(R.id.item_name);
                itemNameTextView.setText(name);

                // 添加点击监听器
                includedView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showToolsDialog(MainActivity.this, item.getId());
                    }
                });
            }
        }
    }


    private void setClick() {
        addImageView = findViewById(R.id.home_add_button);
        addImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, addItem.class);
                startActivity(intent);
            }
        });
    }

    private void showToolsDialog(Context context, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_item_function);

        // 设置Dialog的位置为底部
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.BOTTOM;
            window.setAttributes(layoutParams);
        }
        dialog.show();
        LinearLayout btn_copy = dialog.findViewById(R.id.btn_copy);
        LinearLayout btn_edit = dialog.findViewById(R.id.btn_edit);
        LinearLayout btn_delete = dialog.findViewById(R.id.btn_delete);
        LinearLayout btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_copy.setOnClickListener(v -> {
            new ItemTool.CopyItemTask(this, id).execute();
            initView();
            dialog.dismiss()
        ;});
        btn_delete.setOnClickListener(v -> {
            new ItemTool.DeleteItemTask(context, id).execute();
            initView();
            dialog.dismiss();
        });
        btn_cancel.setOnClickListener(v -> dialog.dismiss());
    }

    // 设置按钮
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_toolbar, menu);
        return true;
    }

    // 添加一个静态方法，用于获取isCreated的值 用于判断是否已经加载完成
    public static boolean isMainActivityCreated() {
        return isCreated;
    }
}