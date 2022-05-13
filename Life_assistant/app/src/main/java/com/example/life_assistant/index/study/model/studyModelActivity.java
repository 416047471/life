package com.example.life_assistant.index.study.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.life_assistant.R;

public class studyModelActivity extends AppCompatActivity {

    private RadioGroup study_model_buttonGroup;
    private RadioButton study_model_countDownButton,study_model_positiveTimingButton,
            study_model_lockMachineButton;
    private Button study_model_startButton;
    private EditText study_model_targetContent;
    private Spinner study_model_timeContent;

    private static int study_model_state = 0;//用于判断当前选中的哪一种模式(0为倒计时，1为正计时，2为锁机)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_study_model);

        init();//初始化
    }

    private void init() {
        //实例化对象
        study_model_buttonGroup = findViewById(R.id.study_model_buttonGroup);
        study_model_countDownButton = findViewById(R.id.study_model_countDownButton);
        study_model_positiveTimingButton = findViewById(R.id.study_model_positiveTimingButton);
        study_model_lockMachineButton = findViewById(R.id.study_model_lockMachineButton);
        study_model_targetContent = findViewById(R.id.study_model_targetContent);
        study_model_timeContent = findViewById(R.id.study_model_timeContent);
        study_model_startButton = findViewById(R.id.study_model_startButton);

        //开始按钮的点击事件
        study_model_startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStudyModel();
            }
        });

    }

    //开始学习模式按钮操作
    private void startStudyModel() {
        String study_model_targetContentNow = study_model_targetContent.getText().toString();
        String study_model_timeContentNow = study_model_timeContent.getSelectedItem().toString();
        //跳到新的activity中，携带参数
        Intent intent = new Intent(studyModelActivity.this,studyModelActivityStart.class);
        Intent intent1 = new Intent(studyModelActivity.this,studyModelActivityLock.class);
        Bundle bundle = new Bundle();
        bundle.putString("study_model_targetContent",study_model_targetContentNow);
        bundle.putString("study_model_timeContent",study_model_timeContentNow);
        switch (study_model_buttonGroup.getCheckedRadioButtonId()){
            case R.id.study_model_countDownButton://倒计时选中
                study_model_state = 0;
                bundle.putInt("study_model_state",study_model_state);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.study_model_positiveTimingButton://正计时选中
                study_model_state = 1;
                bundle.putInt("study_model_state",study_model_state);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.study_model_lockMachineButton://锁机选中
                bundle.putInt("study_model_state",study_model_state);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
        }

    }

    //再oncreate前修改主题颜色
    private void setStyle(String theme) {
        switch (theme){
            case "green":
                setTheme(R.style.greenTheme);
                break;
            case "yellow":
                setTheme(R.style.yellowTheme);
                break;
            case "purple":
                setTheme(R.style.purpleTheme);
                break;
            case "blue":
                setTheme(R.style.blueTheme);
                break;
            case "pink":
                setTheme(R.style.pinkTheme);
                break;
            case "black":
                setTheme(R.style.blackTheme);
                break;
            case "white":
                setTheme(R.style.whiteTheme);
                break;
        }
    }
}