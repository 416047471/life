package com.example.life_assistant.index.study.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.life_assistant.R;

public class studyModelActivityLock extends AppCompatActivity {

    private TextView study_model_startTargetContent,study_model_startTimeMinuteContent,
            study_model_startTimeSecondContent;
    private Button study_model_endButtn;

    private int time;
    private int minute,second;
    private CountDownTimer countDownTimer;

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_model_lock);
//        getWindow().addPrivateFlags(WindowManager.LayoutParams.PRIVATE_FLAG_HOMEKEY_DISPATCHED);
        init();
    }

    private void init() {
        //实例化
        study_model_startTargetContent = findViewById(R.id.study_model_lockTargetContent);
        study_model_startTimeMinuteContent = findViewById(R.id.study_model_lockTimeMinuteContent);
        study_model_startTimeSecondContent = findViewById(R.id.study_model_lockTimeSecondContent);
        study_model_endButtn = findViewById(R.id.study_model_endButtn);

        dialog = setDialog().create();

        //获取Intent传来的数据
        Intent intent = getIntent();
        String targetContent = intent.getStringExtra("study_model_targetContent");
        study_model_startTargetContent.setText(targetContent);
        String timeContent = intent.getStringExtra("study_model_timeContent").trim();
        //将传来的时间数据解析
        int index = timeContent.indexOf(":");
        time = Integer.parseInt(timeContent.substring(0,index));
        //此时time单位为分，更新UI
        minute = time;
        second = 0;
        study_model_startTimeMinuteContent.setText(minute > 9 ? ""+minute : "0"+minute);
        study_model_startTimeSecondContent.setText("0"+second);
        time *= 60;//单位为秒

        showCountdown();

        study_model_endButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }


    //展示倒计时，每秒执行一次
    private void showCountdown() {
        countDownTimer = new CountDownTimer(time*1000,1000) {
            //倒计时时，在没有完成倒计时时会多次调用，直到结束倒计时
            @Override
            public void onTick(long millisUntilFinished) {
                second--;
                if (second<0){
                    minute--;
                    second = 59;
                    study_model_startTimeMinuteContent.setText(minute > 9 ? ""+minute : "0"+minute);
                }
                study_model_startTimeSecondContent.setText(second >9 ? ""+second : "0"+second);
            }
            //倒计时完成时调用
            @Override
            public void onFinish() {
                startActivity(new Intent(studyModelActivityLock.this,studyModelActivity.class));
                finish();
            }
        };
        countDownTimer.start();
    }

    //定义完成后的弹框
    private AlertDialog.Builder setDialog() {
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确认要中途退出吗")
                .setPositiveButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(studyModelActivityLock.this,studyModelActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        return alertDialog;
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_HOME)
            return false;
        return super.onKeyDown(keyCode, event);
    }
}