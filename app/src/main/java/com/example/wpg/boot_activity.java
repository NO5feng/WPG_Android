package com.example.wpg;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class boot_activity extends AppCompatActivity {

    private static final long SPLASH_DELAY = 1000; // 启动屏延迟时间，单位：毫秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_boot);
        // 使用Handler延迟跳转到MainActivity
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent intent = new Intent(boot_activity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}