package com.example.wpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private static boolean isCreated = false;

//    private MyAdapter_item adapter;
    private ImageView addImageView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        setClick();

        isCreated = true;
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
        getSupportActionBar().setTitle(R.string.home_title);

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

        // 监听item
//        adapter.setOnItemClickListener(new MyAdapter_item.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                // 处理子项的点击事件
//            }
//        });
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