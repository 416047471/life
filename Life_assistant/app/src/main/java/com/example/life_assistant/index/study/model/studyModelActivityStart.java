package com.example.life_assistant.index.study.model;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.life_assistant.R;

import java.util.concurrent.locks.Lock;

public class studyModelActivityStart extends AppCompatActivity {

    private TextView study_model_startTargetContent,study_model_startTimeMinuteContent,study_model_startTimeSecondContent;
    private Button study_model_endButtn,study_model_continueOrPausingButton;
    private int time;
    private int minute,second;
    private boolean isStop = false;//用户继续暂停按钮的辨别

    private Dialog dialog;
    private CountDownTimer countDownTimer;

    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_model_start);

        init();//初始化
    }

    private void init() {
        //实例化对象
        study_model_startTargetContent = findViewById(R.id.study_model_startTargetContent);
        study_model_startTimeMinuteContent = findViewById(R.id.study_model_startTimeMinuteContent);
        study_model_startTimeSecondContent = findViewById(R.id.study_model_startTimeSecondContent);
        study_model_endButtn = findViewById(R.id.study_model_endButtn);
        study_model_continueOrPausingButton = findViewById(R.id.study_model_continueOrPausingButton);
        dialog = setDialog().create();

        //获取Intent传来的数据
        Intent intent = getIntent();
        String targetContent = intent.getStringExtra("study_model_targetContent");
        study_model_startTargetContent.setText(targetContent);
        String timeContent = intent.getStringExtra("study_model_timeContent").trim();
        final Integer state = intent.getIntExtra("study_model_state",-1);

        //暂停或者继续的按钮监听事件
        study_model_continueOrPausingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStop){
                    if (state == 0){
                        countDownTimer.cancel();
                    }else if (state == 1){
                        handler.removeCallbacks(runnable);
                    }
                    study_model_continueOrPausingButton.setText("继续");
                    isStop = true;
                }else {
                    if (state == 0){
                        countDownTimer.start();
                    }else if (state == 1){
                        handler.postDelayed(runnable,1000);
                    }
                    study_model_continueOrPausingButton.setText("暂停");
                    isStop = false;
                }

            }
        });

        if (state == 0){
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
        }
        else if (state == 1){
            minute = 0;
            second = 0;
            study_model_startTimeMinuteContent.setText("00");
            study_model_startTimeSecondContent.setText("00");
            showPositiveTiming();
        }


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
                study_model_startTimeMinuteContent.setText("00");
                study_model_startTimeSecondContent.setText("00");
                dialog.show();
            }
        };
        countDownTimer.start();
    }

    //展示正计时
    private void showPositiveTiming() {
        //启动线程
        handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    second++;
                    if (second>59){
                        minute++;
                        second = 0;
                        study_model_startTimeMinuteContent.setText(minute > 9 ? minute+"" : "0"+minute);
                    }
                    study_model_startTimeSecondContent.setText(second > 9 ? second+"" : "0"+second);
                    //启动线程，秒一秒执行一次
                    handler.postDelayed(this,1000);
                }
            };
            runnable.run();
    }


    //定义完成后的弹框
    private AlertDialog.Builder setDialog() {
        final AlertDialog.Builder alertDialog= new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("时间已经到啦")
                .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return alertDialog;
    }

}