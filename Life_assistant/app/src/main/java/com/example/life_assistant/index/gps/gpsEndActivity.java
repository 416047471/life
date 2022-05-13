package com.example.life_assistant.index.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.life_assistant.R;
import com.example.life_assistant.index.user.userSecurityCancelActivity;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class gpsEndActivity extends AppCompatActivity {

    private TextView gps_end_pace,gps_end_distance,gps_end_time,gps_end_heat;
    private Button gps_end_returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_end);

        init();
    }

    private void init() {
        gps_end_pace = findViewById(R.id.gps_end_pace);
        gps_end_distance = findViewById(R.id.gps_end_distance);
        gps_end_time = findViewById(R.id.gps_end_time);
        gps_end_heat = findViewById(R.id.gps_end_heat);
        gps_end_returnButton = findViewById(R.id.gps_end_returnButton);

        //获取传来的数据
        Intent intent = getIntent();
        final String pace = intent.getStringExtra("pace");
        final String distance = intent.getStringExtra("totalDistanceKm");
        final String time = intent.getStringExtra("time");
        final String heat = intent.getStringExtra("heat");
        //将数据显示在控件上
        gps_end_pace.setText(pace);
        gps_end_distance.setText(distance);
        gps_end_time.setText(time);
        gps_end_heat.setText(heat);

        //返回按钮点击事件
        gps_end_returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 点击后将数据上传至Bmob云数据端
                * 跳转至主界面
                * */
                Sport sport = new Sport();
                sport.setSportPace(pace);
                sport.setSportDistance(distance);
                sport.setSportTime(time);
                sport.setSportHeat(heat);
                sport.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null){
                            //上传成功，清空栈，跳转主界面
                            Intent intent = new Intent(gpsEndActivity.this,
                                    com.example.life_assistant.index.indexActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        });

    }
}