package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.life_assistant.R;
import com.example.life_assistant.calculator.bean.HealthyOneAll;
import com.example.life_assistant.calculator.bean.HealthyTwoAll;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class healthyInformation_Activity extends AppCompatActivity {
    private ImageView heightAdd,heightReduce,weightAdd,weightReduce,ageAdd,ageReduce;//三个加减按钮
    private TextView heightData,weightData,ageData;//三个数据控件
    private SeekBar heightSeekBar,weightSeekBar,ageSeekBar;//三个拖动条
    private RadioGroup genderRadioGroup,areaRadioGroup;//两个单击按钮组
    private Button countButton;//计算按钮

    private Integer nowHeightData,nowWeightData,nowAgeData,nowGenderData,nowAreaData;//五个个数据值

    private OkHttpClient okHttpClient;
    private String url1,url2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_healthy_information);

        init();//初始化


    }

    //初始化操作
    private void init() {
        heightAdd = findViewById(R.id.heightAdd);
        heightReduce = findViewById(R.id.heightReduce);
        weightAdd = findViewById(R.id.weightAdd);
        weightReduce = findViewById(R.id.weightReduce);
        ageAdd = findViewById(R.id.ageAdd);
        ageReduce = findViewById(R.id.ageReduce);
        heightData = findViewById(R.id.heightData);
        weightData = findViewById(R.id.weightData);
        ageData = findViewById(R.id.ageData);
        heightSeekBar = findViewById(R.id.heightSeekBar);
        weightSeekBar = findViewById(R.id.weightSeekBar);
        ageSeekBar = findViewById(R.id.ageSeekBar);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        areaRadioGroup = findViewById(R.id.areaRadioGroup);
        countButton = findViewById(R.id.countButton);

        okHttpClient = new OkHttpClient.Builder().build();
        //计算按钮的监听
        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthyData();// 计算身体健康信息
            }
        });

        //将seekBar的数据、文本的数据、加减按钮三个进行统一管理
        sameThem();

    }

    //统一化三项控制按钮的数据
    private void sameThem() {
        //获取三个数据
        nowHeightData = Integer.parseInt(heightData.getText().toString());
        nowWeightData = Integer.parseInt(weightData.getText().toString());
        nowAgeData = Integer.parseInt(ageData.getText().toString());
        //将升高的进度条最高设为280，体重最高设为300,年龄最高设为120
        heightSeekBar.setMax(280);
        weightSeekBar.setMax(300);
        ageSeekBar.setMax(120);
        //将进度条统一为当前数据
        heightSeekBar.setProgress(nowHeightData);
        weightSeekBar.setProgress(nowWeightData);
        ageSeekBar.setProgress(nowAgeData);
        //进度条监听,进行拖动进度条的同时改变对应的显示数据
        heightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                heightData.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                weightData.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        ageSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ageData.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //加减号监听,点击加减号改变对应的显示数据
        heightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowHeightData = Integer.parseInt(heightData.getText().toString());
                heightData.setText(++nowHeightData+"");
            }
        });
        heightReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowHeightData = Integer.parseInt(heightData.getText().toString());
                heightData.setText(--nowHeightData+"");
            }
        });
        weightAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowWeightData = Integer.parseInt(weightData.getText().toString());
                weightData.setText(++nowWeightData+"");
            }
        });
        weightReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowWeightData = Integer.parseInt(weightData.getText().toString());
                weightData.setText(--nowWeightData+"");
            }
        });
        ageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowAgeData = Integer.parseInt(ageData.getText().toString());
                ageData.setText(++nowAgeData+"");
            }
        });
        ageReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nowAgeData = Integer.parseInt(ageData.getText().toString());
                ageData.setText(--nowAgeData+"");
            }
        });
    }

    //计算所有身体健康指数信息操作
    private void healthyData() {
        //获取五个数据
        nowHeightData = Integer.parseInt(heightData.getText().toString());
        nowWeightData = Integer.parseInt(weightData.getText().toString());
        nowAgeData = Integer.parseInt(ageData.getText().toString());
        Integer genderIndex = genderRadioGroup.getCheckedRadioButtonId();
        Integer areaIndex= areaRadioGroup.getCheckedRadioButtonId();
        if (genderIndex != -1){
            nowGenderData = genderIndex+1;
        }
        if (areaIndex != -1){
            nowAreaData = genderIndex+1;
        }
        /*
        * 计算身体健康指数信息
        * */
        //获取标准身高体重计算器url
        url1 = "http://apis.juhe.cn/fapig/calculator/weight?key=72c30aedd52e4a58e9da855a72756dcd&sex="
    +nowGenderData+"&role="+nowAreaData+"&height="+nowHeightData+"&weight="+nowWeightData;
        //获取基础健康指数计算器信息
        url2 = "http://apis.juhe.cn/fapig/healthy/bmr?key=5e9506017f42433059f81a6fa8c7e937&height="
                +nowHeightData+"&weight="+nowWeightData+"&age="+nowAgeData;
        new Thread(){
            @Override
            public void run() {
                Request request1 = new Request.Builder()
                        .url(url1)
                        .build();
                Request request2 = new Request.Builder()
                        .url(url2)
                        .build();
                Call call1 = okHttpClient.newCall(request1);
                Call call2 = okHttpClient.newCall(request2);
                try {
                    Response response = call1.execute();
                    String json1 = response.body().string();
                    response = call2.execute();
                    String json2 = response.body().string();
                    //使用Gson解析两个Json数据
                    HealthyOneAll healthyOneAll = new Gson().fromJson(json1,HealthyOneAll.class);
                    HealthyTwoAll healthyTwoAll = new Gson().fromJson(json2,HealthyTwoAll.class);
                    if (healthyOneAll != null && healthyTwoAll != null){
                        //跳转到BMI_Result_Activity
                        Intent intent = new Intent(healthyInformation_Activity.this, healthyInformation_Result_Activity.class);
                        intent.putExtra("idealWeight",healthyOneAll.getHealthyOne().getIdealWeight());
                        intent.putExtra("normalWeight",healthyOneAll.getHealthyOne().getNormalWeight());
                        intent.putExtra("level",healthyOneAll.getHealthyOne().getLevel());
                        intent.putExtra("levelMsg",healthyOneAll.getHealthyOne().getLevelMsg());
                        intent.putExtra("danger",healthyOneAll.getHealthyOne().getDanger());
                        intent.putExtra("bmi",healthyOneAll.getHealthyOne().getBmi());
                        intent.putExtra("normalBMI",healthyOneAll.getHealthyOne().getNormalBMI());
                        intent.putExtra("range",healthyTwoAll.getHealthyTwo().getRange());
                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
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