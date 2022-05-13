package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.life_assistant.R;

public class healthyInformation_Result_Activity extends AppCompatActivity {

    private Toolbar resultToolbar;//上方横条
    private TextView result_levelMsg,result_idealWeight,result_normalWeight,
            result_bmi,result_normalBMI,result_range,result_danger,resultEvaluation;//三个文本
    private Button rerturnButton;//返回按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_healthy_information_result_);

        init();//初始化
        resultShow();//结果展示
    }

    //初始化操作
    private void init() {
        resultToolbar = findViewById(R.id.resultToolbar);
        result_levelMsg = findViewById(R.id.result_levelMsg);//等级描述	等级, 0偏瘦 1正常 2偏胖 3肥胖 4重度肥胖 5极重度肥胖
        result_idealWeight = findViewById(R.id.result_idealWeight);//标准体重
        result_normalWeight = findViewById(R.id.result_normalWeight);//	正常体重范围
        result_bmi = findViewById(R.id.result_bmi);//	BMI指数
        result_normalBMI = findViewById(R.id.result_normalBMI);//	BMI指数
        result_range = findViewById(R.id.result_range);//基础代谢率（BMR）范围
        result_danger = findViewById(R.id.result_danger);//	相关疾病发病危险
        resultEvaluation = findViewById(R.id.resultEvaluation);
        rerturnButton = findViewById(R.id.returnButton);

        //返回按钮监听
        rerturnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回BMI_Activity
                startActivity(new Intent(healthyInformation_Result_Activity.this, healthyInformation_Activity.class));
            }
        });
    }

    //结果展示操作
    private void resultShow() {
        String idealWeight = getIntent().getStringExtra("idealWeight");//
        String normalWeight = getIntent().getStringExtra("normalWeight");
        String levelMsg = getIntent().getStringExtra("levelMsg");
        String danger = getIntent().getStringExtra("danger");
        String bmi = getIntent().getStringExtra("bmi");//
        String normalBMI = getIntent().getStringExtra("normalBMI");
        String range = getIntent().getStringExtra("range");

        //设置对应控件值
        result_levelMsg.setText(levelMsg);
        result_idealWeight.setText(idealWeight);
        result_normalWeight.setText(normalWeight);
        result_bmi.setText(bmi);
        result_normalBMI.setText(normalBMI);
        result_range.setText(range);
        result_danger.setText(danger);

        double bmiReplace = Double.parseDouble(bmi);
        if (bmiReplace < 18.5){
            changeFontColor("#F8C21F");
            resultEvaluation.setText("你的BMI指数偏低，属于偏瘦。建议你饮食方面不要偏食和挑食，可适量增加高蛋白食品（鸡蛋，牛奶，花生等）");
        }else if (bmiReplace < 25){
            changeFontColor("#DA43CD80");
            resultEvaluation.setText("你的BMI指数正常，是最好的身体状态。建议你还是要通过适当的健身来继续保持这种fit身材");
        }else if (bmiReplace < 30){
            changeFontColor("#EEF1653A");
            resultEvaluation.setText("你BMI指数超重了，就是说你稍微有点胖了。建议你实行“管的住嘴迈的开腿”的健身方法，少吃高热量食物，多吃蔬菜水果和膳食纤维及富含营养但是低热量的食品，同时开始做有氧运动");
        }else if (bmiReplace < 35){
            changeFontColor("#EEEA5426");
            resultEvaluation.setText("你BMI指数已经超标了，你已经达到肥胖一族的标准。建议不能吃垃圾食品和过于油腻的食物，多吃富含营养但是低热量的食品,例如大豆、绿色蔬菜、无脂或低脂的牛奶、没有添加食用香料和奶精的燕麦片等，不喝酒，不喝饮料，多喝水，每天坚持1个小时以上的中等或高等强度运动");
        }else if (bmiReplace <= 40){
            changeFontColor("#EEE84513");
            resultEvaluation.setText("你BMI指数已经超标了，你已经达到肥胖一族的标准。建议不能吃垃圾食品和过于油腻的食物，多吃富含营养但是低热量的食品,例如大豆、绿色蔬菜、无脂或低脂的牛奶、没有添加食用香料和奶精的燕麦片等，不喝酒，不喝饮料，多喝水，每天坚持1个小时以上的中等或高等强度运动");
        }else if (bmiReplace > 40){
            changeFontColor("#F0FF0015");
            result_levelMsg.setTextColor(Color.parseColor("#F0FF0015"));
            resultEvaluation.setText("你BMI指数已经超标了，你已经达到肥胖一族的标准。建议不能吃垃圾食品和过于油腻的食物，多吃富含营养但是低热量的食品,例如大豆、绿色蔬菜、无脂或低脂的牛奶、没有添加食用香料和奶精的燕麦片等，不喝酒，不喝饮料，多喝水，每天坚持1个小时以上的中等或高等强度运动");
        }
    }

    //控件颜色改变
    private void changeFontColor(String colorString){
        result_levelMsg.setTextColor(Color.parseColor(colorString));
        result_idealWeight.setTextColor(Color.parseColor(colorString));
        result_normalWeight.setTextColor(Color.parseColor(colorString));
        result_bmi.setTextColor(Color.parseColor(colorString));
        result_normalBMI.setTextColor(Color.parseColor(colorString));
        result_range.setTextColor(Color.parseColor(colorString));
        result_danger.setTextColor(Color.parseColor(colorString));
        resultToolbar.setBackgroundColor(Color.parseColor(colorString));
        rerturnButton.setBackgroundColor(Color.parseColor(colorString));
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