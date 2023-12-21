package com.example.wpg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class boot_activity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 3000; // 启动屏延迟时间，单位：毫秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boot);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 循环等待MainActivity的onCreate方法完成
//                while (!MainActivity.isMainActivityCreated()) {
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }

                // 跳转到应用的主界面
                Intent intent = new Intent(boot_activity.this, MainActivity.class);
                startActivity(intent);
                finish(); // 结束当前Activity
            }
        }, SPLASH_DELAY);
    }
}