package com.example.handlertime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private TextView mTv;
    private String timestring;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x01) {
                String time = (String) msg.obj;
                mTv.setText(time);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv = findViewById(R.id.tv1);

        //子线程
        new Thread() {
            @Override
            public void run() {
                super.run();
                while (true) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //获取当前系统时间
                    timestring = simpleDateFormat.format(System.currentTimeMillis());
                    Message message = new Message();
                    message.what = 0x01;
                    message.obj = timestring;
                    handler.sendMessage(message);
                    try {
                        //每隔一秒变化一次
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}