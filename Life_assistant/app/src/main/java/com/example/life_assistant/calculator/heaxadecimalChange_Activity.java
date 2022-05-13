package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.life_assistant.R;

//进制转换activity
public class heaxadecimalChange_Activity extends AppCompatActivity implements View.OnClickListener {
    private EditText edit_heaxadecimal;
    private TextView binary_textView,octal_textView,decimal_textView,hexadecimal_textView;
    private Button binary_button,octal_Button,decimal_button,hexadecimal_button;
    private Button number1_button,number2_button,number3_button,number4_button,number5_button,
    number6_button,number7_button,number8_button,number9_button,number0_button,numberA_button,
    numberB_button,numberC_button,numberD_button,numberE_button,numberF_button, numberCLR_button,numberDEL_button;
    private Integer whichScale;//识别当前进制状态的值
    private String inputNumber;//输入到edit_heaxadecimal的输入框前的数据中介


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_heaxadecimal_change_);
        init();

    }

    public void init(){
        edit_heaxadecimal =findViewById(R.id.edit_heaxadecimal);
        binary_textView = findViewById(R.id.binary_textView);
        binary_button = findViewById(R.id.binary_button);
        octal_Button = findViewById(R.id.octal_Button);
        octal_textView = findViewById(R.id.octal_textView);
        decimal_button = findViewById(R.id.decimal_button);
        decimal_textView =findViewById(R.id.decimal_textView);
        hexadecimal_button = findViewById(R.id.hexadecimal_button);
        hexadecimal_textView = findViewById(R.id.hexadecimal_textView);
        number1_button = findViewById(R.id.number1_button);
        number2_button = findViewById(R.id.number2_button);
        number3_button = findViewById(R.id.number3_button);
        number4_button = findViewById(R.id.number4_button);
        number5_button = findViewById(R.id.number5_button);
        number6_button = findViewById(R.id.number6_button);
        number7_button = findViewById(R.id.number7_button);
        number8_button = findViewById(R.id.number8_button);
        number9_button = findViewById(R.id.number9_button);
        number0_button = findViewById(R.id.number0_button);
        numberA_button = findViewById(R.id.numberA_button);
        numberB_button = findViewById(R.id.numberB_button);
        numberC_button = findViewById(R.id.numberC_button);
        numberD_button = findViewById(R.id.numberD_button);
        numberE_button = findViewById(R.id.numberE_button);
        numberF_button = findViewById(R.id.numberF_button);
        numberCLR_button = findViewById(R.id.numberCLR_button);
        numberDEL_button = findViewById(R.id.numberDEL_button);

        binary_button.setOnClickListener(this);
        octal_Button.setOnClickListener(this);
        decimal_button.setOnClickListener(this);
        hexadecimal_button.setOnClickListener(this);
        number1_button.setOnClickListener(this);
        number2_button.setOnClickListener(this);
        number3_button.setOnClickListener(this);
        number4_button.setOnClickListener(this);
        number5_button.setOnClickListener(this);
        number6_button.setOnClickListener(this);
        number7_button.setOnClickListener(this);
        number8_button.setOnClickListener(this);
        number9_button.setOnClickListener(this);
        number0_button.setOnClickListener(this);
        numberA_button.setOnClickListener(this);
        numberB_button.setOnClickListener(this);
        numberC_button.setOnClickListener(this);
        numberD_button.setOnClickListener(this);
        numberE_button.setOnClickListener(this);
        numberF_button.setOnClickListener(this);
        numberCLR_button.setOnClickListener(this);
        numberDEL_button.setOnClickListener(this);

        //禁止弹出键盘
        edit_heaxadecimal.setInputType(InputType.TYPE_NULL);
        inputNumber = "";
        edit_heaxadecimal.setText("0");
        initButtonenabled();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //进行点击二进制按钮后进入状态初始化操作
            case R.id.binary_button:
                inputAndOutInit();
                notBinaryClick();
                binary_button.setBackgroundColor(Color.parseColor("#E96BD371"));
                octal_Button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                decimal_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                hexadecimal_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                whichScale = 2;
                break;
            //进行点击八进制按钮后进入状态初始化操作
            case R.id.octal_Button:
                inputAndOutInit();
                notOctalClick();
                octal_Button.setBackgroundColor(Color.parseColor("#E96BD371"));
                binary_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                decimal_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                hexadecimal_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                whichScale = 8;
                break;
            //进行点击十进制按钮后进入状态初始化操作
            case R.id.decimal_button:
                inputAndOutInit();
                notDecimalClick();
                decimal_button.setBackgroundColor(Color.parseColor("#E96BD371"));
                binary_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                octal_Button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                hexadecimal_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                whichScale = 10;
                break;
            //进行点击十六进制按钮后进入状态初始化操作
            case R.id.hexadecimal_button:
                inputAndOutInit();
                notHexadecimalClick();
                hexadecimal_button.setBackgroundColor(Color.parseColor("#E96BD371"));
                binary_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                octal_Button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));
                decimal_button.setBackgroundColor(Color.parseColor("#9FD3D6D6"));

                whichScale = 16;
                break;
            case R.id.number0_button:
            case R.id.number1_button:
            case R.id.number2_button:
            case R.id.number3_button:
            case R.id.number4_button:
            case R.id.number5_button:
            case R.id.number6_button:
            case R.id.number7_button:
            case R.id.number8_button:
            case R.id.number9_button:
            case R.id.numberA_button:
            case R.id.numberB_button:
            case R.id.numberC_button:
            case R.id.numberD_button:
            case R.id.numberE_button:
            case R.id.numberF_button:
                inputNumber = inputNumber+((Button)v).getText();
                edit_heaxadecimal.setText(inputNumber);
                edit_heaxadecimal.setSelection(inputNumber.length());
                scaleBegin(inputNumber);
                break;
            case R.id.numberCLR_button:
                inputNumber = "0";
                edit_heaxadecimal.setText(inputNumber);
                scaleBegin(inputNumber);
                break;
            case R.id.numberDEL_button:
                inputNumber = inputNumber.substring(0,inputNumber.length()-1);
                edit_heaxadecimal.setText(inputNumber);
                scaleBegin(inputNumber);
                break;
        }
    }

    //设置进入界面状态下为点击对应进制状态时的对应按键的初始化操作
    public void initButtonenabled(){
        number0_button.setEnabled(false);;number1_button.setEnabled(false);
        number2_button.setEnabled(false);number3_button.setEnabled(false);
        number4_button.setEnabled(false);number5_button.setEnabled(false);
        number6_button.setEnabled(false);number7_button.setEnabled(false);
        number8_button.setEnabled(false);number9_button.setEnabled(false);
        numberA_button.setEnabled(false);numberB_button.setEnabled(false);
        numberC_button.setEnabled(false);numberD_button.setEnabled(false);
        numberE_button.setEnabled(false);numberF_button.setEnabled(false);
        numberDEL_button.setEnabled(false);numberCLR_button.setEnabled(false);
    }

    //设置为二进制状态下把所有对应按键的始化
    public void notBinaryClick(){
        number0_button.setEnabled(true);;number1_button.setEnabled(true);
        number2_button.setEnabled(false);number3_button.setEnabled(false);
        number4_button.setEnabled(false);number5_button.setEnabled(false);
        number6_button.setEnabled(false);number7_button.setEnabled(false);
        number8_button.setEnabled(false);number9_button.setEnabled(false);
        numberA_button.setEnabled(false);numberB_button.setEnabled(false);
        numberC_button.setEnabled(false);numberD_button.setEnabled(false);
        numberE_button.setEnabled(false);numberF_button.setEnabled(false);
        numberCLR_button.setEnabled(true);numberDEL_button.setEnabled(true);
    }

    //设置为八进制状态下把所有对应按键的初始化
    public void notOctalClick(){
        number0_button.setEnabled(true);;number1_button.setEnabled(true);
        number2_button.setEnabled(true);number3_button.setEnabled(true);
        number4_button.setEnabled(true);number5_button.setEnabled(true);
        number6_button.setEnabled(true);number7_button.setEnabled(true);
        number8_button.setEnabled(false);number9_button.setEnabled(false);
        numberA_button.setEnabled(false);numberB_button.setEnabled(false);
        numberC_button.setEnabled(false);numberD_button.setEnabled(false);
        numberE_button.setEnabled(false);numberF_button.setEnabled(false);
        numberCLR_button.setEnabled(true);numberDEL_button.setEnabled(true);
    }

    //设置为十进制状态下把所有对应按键的初始化
    public void notDecimalClick(){
        number0_button.setEnabled(true);;number1_button.setEnabled(true);
        number2_button.setEnabled(true);number3_button.setEnabled(true);
        number4_button.setEnabled(true);number5_button.setEnabled(true);
        number6_button.setEnabled(true);number7_button.setEnabled(true);
        number8_button.setEnabled(true);number9_button.setEnabled(true);
        numberA_button.setEnabled(false);numberB_button.setEnabled(false);
        numberC_button.setEnabled(false);numberD_button.setEnabled(false);
        numberE_button.setEnabled(false);numberF_button.setEnabled(false);
        numberCLR_button.setEnabled(true);numberDEL_button.setEnabled(true);
    }

    //设置为十六进制状态下把所有对应按键的初始化
    public void notHexadecimalClick(){
        number0_button.setEnabled(true);;number1_button.setEnabled(true);
        number2_button.setEnabled(true);number3_button.setEnabled(true);
        number4_button.setEnabled(true);number5_button.setEnabled(true);
        number6_button.setEnabled(true);number7_button.setEnabled(true);
        number8_button.setEnabled(true);number9_button.setEnabled(true);
        numberA_button.setEnabled(true);numberB_button.setEnabled(true);
        numberC_button.setEnabled(true);numberD_button.setEnabled(true);
        numberE_button.setEnabled(true);numberF_button.setEnabled(true);
        numberCLR_button.setEnabled(true);numberDEL_button.setEnabled(true);
    }

    //点击按钮进制进制转换
    public void scaleBegin(String inputNumber){
        if (whichScale == 2){
            //二进制本身放入二进制结果中
            binary_textView.setText(inputNumber);
            //获取二进制值转为十进制的Int值，方面后面进制八进制和十六进制的转换
            Integer decimal = Integer.parseInt(inputNumber,2);
            octal_textView.setText(""+decimal);
            decimal_textView.setText(Integer.toOctalString(decimal));
            hexadecimal_textView.setText(Integer.toHexString(decimal));
        }else if (whichScale == 8){
            //八进制本身放入八进制结果中
            octal_textView.setText(inputNumber);
            //获取八进制值转为十进制的Int值，方面后面进制二进制和十六进制的转换
            Integer decimal = Integer.parseInt(inputNumber,8);
            binary_textView.setText(Integer.toBinaryString(decimal));
            decimal_textView.setText(""+decimal);
            hexadecimal_textView.setText(Integer.toHexString(decimal));
        }else if (whichScale == 10){
            //十进制本身放入十进制结果中
            decimal_textView.setText(inputNumber);
            //把String类型转为INT，然后进行十进制转二、八、十六的方法调用
            Integer decimal = Integer.parseInt(inputNumber);
            binary_textView.setText(Integer.toBinaryString(decimal));
            octal_textView.setText(Integer.toOctalString(decimal));
            hexadecimal_textView.setText(Integer.toHexString(decimal));
        }else if (whichScale == 16){
            //十六进制本身放入十六进制结果中
            hexadecimal_textView.setText(inputNumber);
            //获取十六进制值转为十进制的Int值，方面后面进制二进制和十六进制的转换
            Integer decimal = Integer.parseInt(inputNumber,16);
            binary_textView.setText(Integer.toBinaryString(decimal));
            octal_textView.setText(Integer.toOctalString(decimal));
            decimal_textView.setText(""+decimal);
        }
    }

    //点击进制按钮后的输入输出初始化
    public void inputAndOutInit(){
        inputNumber = "";
        edit_heaxadecimal.setText("0");
        binary_textView.setText("0");
        octal_textView.setText("0");
        decimal_textView.setText("0");
        hexadecimal_textView.setText("0");
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