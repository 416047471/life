package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;

//单位换算activity
public class unitConversion_Activity extends AppCompatActivity {

    private Spinner unitAll;//总度量类型下拉框
    private Spinner leftSpinner,rightSpinner;//左右两边的下拉框
    private ImageView changeIt;//交换按钮
    private EditText inputData;//输入的数据控件
    private TextView resultData;//结果值控件

    private Integer leftPosition,rightPosition;//左右两个下拉框的当前下标
    private double nowInputData;//当前输入框的内容

    //初始化一个二维数组，用来装载不同单位类型的数据
    private String[][] content= new String[][]{
            {"千米(km)","米(m)","分米(dm)","厘米(cm)","毫米(mm)","微米(μm)", "纳米(nm)",
                    "皮米(pm)","光年(ly)","天文单位(AU)","码","英尺(ft)","英寸(in)", "海里(nmi)",
                    "英里(mi)","弗隆(fur)","英寻(fm)","密耳(mil)","里","丈","尺","寸","分","厘","毫"},
            {"平方千米","公顷","公亩","平方米","平方分米","平方厘米","平方毫米","英亩","平方英里","平方码",
                    "平方英尺","平方英寸","平方竿","顷","亩","分","平方尺","平方寸"},
            {"立方千米","立方米","立方分米","立方厘米","立方毫米","升","分升","毫升","厘升","公石","微升",
                    "立方英尺","立方英寸","立方码","亩英尺","英制加仑","美制加仑","英制液体盎司","美制液体盎司"},
            {"千克","克","毫克","微克","吨","公吨","公担","克拉","分","磅","盎司","格令","长吨","短吨",
                    "英担","美担","英石","打兰","担","斤","两","钱"},
            {"摄氏度","华氏度","开氏度","兰氏度","列氏度"},
            {"瓦","千瓦","英制马力","米制马力","公斤·米/秒","千卡/秒","英热单位/秒","英尺·磅/秒","焦耳/秒",
                    "牛顿·米/秒"},
            {"焦耳","公斤·米","米制马力·时","英制马力·时","千瓦·时","度","卡","千卡","英热单位","英尺·磅",
                    "千焦"},
            {"千克/立方厘米","千克/立方分米","千克/立方米","克/立方里面","克/立方分米","克/立方米"},
            {"牛","千牛","千克力","克力","公吨力","磅力","千磅力","达因"},
            {"年","周","天","时","分","秒","毫秒","微妙","纳秒"},
            {"米/秒","千米/秒","千米/时","光速","马赫","英里/时","英寸/秒"},
            {"比特","字节","千字节","兆字节","千兆字节","太字节","拍字节","艾字节"},
            {"元","角","分"}
    };
    private Integer nowUnitType=0;//用来判定当前数据类型的下标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_unit_conversion_);

        //初始化
        init();
    }

    //初始化方法
    private void init() {
        unitAll = findViewById(R.id.unitAll);
        leftSpinner = findViewById(R.id.leftSprinner);
        rightSpinner = findViewById(R.id.rightSprinner);
        changeIt = findViewById(R.id.changeIt);
        inputData = findViewById(R.id.inputData);
        resultData = findViewById(R.id.resultData);

        //初始化左右下拉框的内容
        setLeftAndRightContent(0);

        //总Spinner的监听
        unitAll.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //根据当前选项的下标该表左右下拉框内容
                setLeftAndRightContent(position);
                nowUnitType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //设置初始下标
        leftPosition = 0;
        rightPosition = 0;
        //两个spinner的监听
        //当前选择的下标值更新
        leftSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leftPosition = position;
                unitConversion();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
        rightSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rightPosition = position;
                unitConversion();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        //设置EditText的监听事件
        inputData.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //获取当前的数据
                nowInputData =  Integer.parseInt(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //单位换算
                unitConversion();
            }
        });

        //交换按钮监听事件
        changeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //叫唤两边的positon值
                Integer changePositon = 0;
                changePositon = leftPosition;
                leftPosition = rightPosition;
                rightPosition = changePositon;
                //改变两边下拉框的当前值
                leftSpinner.setSelection(leftPosition);
                rightSpinner.setSelection(rightPosition);

                unitConversion();
            }
        });
    }

    //设置左右下拉框内容
    private void setLeftAndRightContent(Integer position){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,content[position]);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        leftSpinner.setAdapter(adapter);
        rightSpinner.setAdapter(adapter);

    }

    //单位换算操作
    private void unitConversion(){
        double nowResultData = nowInputData;
        switch (nowUnitType){
            case 0:
                //总下标为0，进行长度单位换算
                nowResultData = lengthUnitConversion(nowResultData);
                break;
            case 1:
                //总下标为1，进行面积单位换算
                nowResultData = areaUnitConversion(nowResultData);
                break;
            case 2:
                //总下标为2，进行体积单位换算
                nowResultData = volumeUnitConversion(nowResultData);
                break;
            case 3:
                //总下标为3，进行质量单位换算
                nowResultData = qualityUnitConversion(nowResultData);
                break;
            case 4:
                //总下标为4，进行温度单位换算
                nowResultData = temperatureUnitConversion(nowResultData);
                break;
            case 5:
                //总下标为5，进行功率单位换算
                nowResultData = powerUnitConversion(nowResultData);
                break;
            case 6:
                //总下标为6.进行功/能/热单位换算
                nowResultData = functionalHeatUnitConversion(nowResultData);
                break;
            case 7:
                //总下标为7，进行密度单位换算
                nowResultData = densityUnitConversion(nowResultData);
                break;
            case 8:
                //总下标为8，进行力单位换算
                nowResultData = newtonUnitConversion(nowResultData);
                break;
            case 9:
                //总下标为9.进行时间单位换算
                nowResultData = timeUnitConversion(nowResultData);
                break;
            case 10:
                //总下标为10，进行速度单位换算
                nowResultData = speedUnitConversion(nowResultData);
                break;
            case 11:
                //总下标为11，进行存储单位换算
                nowResultData = saveUnitConversion(nowResultData);
                break;
            case 12:
                //总下标为12，进行人民币单位换算
                nowResultData = rmbUnitConversion(nowResultData);
                break;
        }
        resultData.setText(""+nowResultData);
    }

    //长度单位换算操作
    private double lengthUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边的单位与米进行换算
            switch (leftPosition){
                case 0:
                    nowResultData *= Math.pow(10,3);
                    break;
                case 1:
                    break;
                case 2:
                    nowResultData *= Math.pow(10,-1);
                    break;
                case 3:
                    nowResultData *= Math.pow(10,-2);
                    break;
                case 4:
                    nowResultData *= Math.pow(10,-3);
                    break;
                case 5:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 6:
                    nowResultData *= Math.pow(10,-9);
                    break;
                case 7:
                    nowResultData *= Math.pow(10,-12);
                    break;
                case 8:
                    nowResultData *= 9.4607*Math.pow(10,15);
                    break;
                case 9:
                    nowResultData *= 1.496*Math.pow(10,11);
                    break;
                case 10:
                    if (rightPosition<10 || rightPosition>18)
                        nowResultData *= 0.9144;
                    break;
                case 11:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 0.3333333;
                    else
                        nowResultData *= 0.3048;
                    break;
                case 12:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 0.0277778;
                    else
                        nowResultData *= 0.0254;
                    break;
                case 13:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 1/0.0004937;
                    else
                        nowResultData *= 1852;
                    break;
                case 14:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 1760;
                    else
                        nowResultData *= 1609.344;
                    break;
                case 15:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 220;
                    else
                        nowResultData *= 201.168;
                    break;
                case 16:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 2;
                    else
                        nowResultData *= 1.8288;
                    break;
                case 17:
                    if (rightPosition>=10 && rightPosition<=18)
                        nowResultData *= 1.0 /36000;
                    else
                        nowResultData *= 0.0000254;
                    break;
                case 18:
                    nowResultData *= 500;
                    break;
                case 19:
                    if (rightPosition<19)
                        nowResultData *= 1/0.3;
                    break;
                case 20:
                    if (rightPosition>=19)
                        nowResultData *= 0.1;
                    else
                        nowResultData *= 1.0 /3;
                    break;
                case 21:
                    if (rightPosition>=19)
                        nowResultData *= 0.01;
                    else
                        nowResultData *= 1.0 /30;
                    break;
                case 22:
                    if (rightPosition>=19)
                        nowResultData *= 0.001;
                    else
                        nowResultData *= 1.0 /300;
                    break;
                case 23:
                    if (rightPosition>=19)
                        nowResultData *= 0.0001;
                    else
                        nowResultData *= 1.0 /3000;
                    break;
                case 24:
                    if (rightPosition>=19)
                        nowResultData *= 0.00001;
                    else
                        nowResultData *= 1.0 /30000;
                    break;

            }
            //把米对右边的单位进行换算
            switch (rightPosition){
                case 0:
                    nowResultData *= 0.001;
                    break;
                case 1:
                    break;
                case 2:
                    nowResultData *= 10;
                    break;
                case 3:
                    nowResultData *= 100;
                    break;
                case 4:
                    nowResultData *= 1000;
                    break;
                case 5:
                    nowResultData *= Math.pow(10,6);
                    break;
                case 6:
                    nowResultData *= Math.pow(10,9);
                    break;
                case 7:
                    nowResultData *= Math.pow(10,12);
                    break;
                case 8:
                    nowResultData *= 1/(9.4607*Math.pow(10,15));
                    break;
                case 9:
                    nowResultData *= 1/(1.496*Math.pow(10,11));
                    break;
                case 10:
                    if (leftPosition<10 || leftPosition>18)
                        nowResultData *= 1/0.9144;
                    break;
                case 11:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 3;
                    else
                        nowResultData *= 1/0.3048;
                    break;
                case 12:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 36;
                    else
                        nowResultData *= 1/0.0254;
                    break;
                case 13:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 0.0004937;
                    else
                        nowResultData *= 0.00054;
                    break;
                case 14:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 1.0 /1760;
                    else
                        nowResultData *= 1/1609.344;
                    break;
                case 15:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 1.0 /220;
                    else
                        nowResultData *= 1/201.168;
                    break;
                case 16:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 0.5;
                    else
                        nowResultData *= 1/1.8288;
                    break;
                case 17:
                    if (leftPosition>=10 && leftPosition<=18)
                        nowResultData *= 36000;
                    else
                        nowResultData *= 1/0.0000254;
                    break;
                case 18:
                    nowResultData *= 0.002;
                    break;
                case 19:
                    if (leftPosition<19)
                        nowResultData *= 0.3;
                    break;
                case 20:
                    if (leftPosition>=19)
                        nowResultData *= 10;
                    else
                        nowResultData *= 3;
                    break;
                case 21:
                    if (leftPosition>=19)
                        nowResultData *= 100;
                    else
                        nowResultData *= 30;
                    break;
                case 22:
                    if (leftPosition>=19)
                        nowResultData *= 1000;
                    else
                        nowResultData *= 300;
                    break;
                case 23:
                    if (leftPosition>=19)
                        nowResultData *= 10000;
                    else
                        nowResultData *= 3000;
                    break;
                case 24:
                    if (leftPosition>=19)
                        nowResultData *= 100000;
                    else
                        nowResultData *= 30000;
                    break;

            }
        }
        return nowResultData;
    }

    //面积单位换算操作
    private double areaUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边大部分与公亩进行换算
            switch (leftPosition){
                case 0:
                    nowResultData *= 10000;
                    break;
                case 1:
                    nowResultData *= 100;
                    break;
                case 2:
                    break;
                case 3:
                    nowResultData *= 0.01;
                    break;
                case 4:
                    nowResultData *= 0.0001;
                    break;
                case 5:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 6:
                    nowResultData *= Math.pow(10,-8);
                    break;
                case 7:
                    if (rightPosition<7 || rightPosition>12)
                        nowResultData *= 40.4685642;
                    break;
                case 8:
                    if (rightPosition>=7 && rightPosition<=12)
                        nowResultData *= 640;
                    else
                        nowResultData *= 25899.8811034;
                    break;
                case 9:
                    if (rightPosition>=7 && rightPosition<=12)
                        nowResultData *= 1.0 /4840;
                    else
                        nowResultData *= 0.0083613;
                    break;
                case 10:
                    if (rightPosition>=7 && rightPosition<=12)
                        nowResultData *= 1.0 /43560;
                    else
                        nowResultData *= 0.000929;
                    break;
                case 11:
                    if (rightPosition>=7 && rightPosition<=12)
                        nowResultData *= 1.0 /6272640;
                    else
                        nowResultData *= 6.4516*Math.pow(10,-6);
                    break;
                case 12:
                    if (rightPosition>=7 && rightPosition<=12)
                        nowResultData *= 1.0 /160;
                    else
                        nowResultData *= 0.2529285;
                    break;
                case 13:
                    if (rightPosition <13)
                        nowResultData *= 1/0.0015;
                    break;
                case 14:
                    if (rightPosition>=13)
                        nowResultData *= 0.01;
                    else
                        nowResultData *= 1/0.15;
                    break;
                case 15:
                    if (rightPosition>=13)
                        nowResultData *= 0.001;
                    else
                        nowResultData *= 1/1.5;
                    break;
                case 16:
                    if (rightPosition>=13)
                        nowResultData *= 1.0 /600000;
                    else
                        nowResultData *= 1.0 /900;
                    break;
                case 17:
                    if (rightPosition>=13)
                        nowResultData *= 1.0 /60000000;
                    else
                        nowResultData *= 1.0 /90000;
                    break;

            }
            //右边用公亩与其进行大部分换算
            switch (rightPosition){
                case 0:
                    nowResultData *= 0.0001;
                    break;
                case 1:
                    nowResultData *= 0.01;
                    break;
                case 2:
                    break;
                case 3:
                    nowResultData *= 100;
                    break;
                case 4:
                    nowResultData *= 10000;
                    break;
                case 5:
                    nowResultData *= 1000000;
                    break;
                case 6:
                    nowResultData *= Math.pow(10,8);
                    break;
                case 7:
                    if (leftPosition<7 || leftPosition>12)
                        nowResultData *= 0.0247105;
                    break;
                case 8:
                    if (leftPosition>=7 && leftPosition<=12)
                        nowResultData *= 1.0 /640;
                    else
                        nowResultData *= 0.0000386;
                    break;
                case 9:
                    if (leftPosition>=7 && leftPosition<=12)
                        nowResultData *= 4840;
                    else
                        nowResultData *= 119.5990046;
                    break;
                case 10:
                    if (leftPosition>=7 && leftPosition<=12)
                        nowResultData *= 43560;
                    else
                        nowResultData *= 1076.3910417;
                    break;
                case 11:
                    if (leftPosition>=7 && leftPosition<=12)
                        nowResultData *= 6272640;
                    else
                        nowResultData *= 155000.3100006;
                    break;
                case 12:
                    if (leftPosition>=7 && leftPosition<=12)
                        nowResultData *= 160;
                    else
                        nowResultData *= 3.9536861;
                    break;
                case 13:
                    if (leftPosition<13)
                        nowResultData *= 0.0015;
                    break;
                case 14:
                    if (leftPosition>=13)
                        nowResultData *= 100;
                    else
                        nowResultData *= 0.15;
                    break;
                case 15:
                    if (leftPosition>=13)
                        nowResultData *= 1000;
                    else
                        nowResultData *= 1.5;
                    break;
                case 16:
                    if (leftPosition>=13)
                        nowResultData *= 600000;
                    else
                        nowResultData *= 900;
                    break;
                case 17:
                    if (leftPosition>=13)
                        nowResultData *= 6*Math.pow(10,7);
                    else
                        nowResultData *= 90000;
                    break;
            }
        }
        return  nowResultData;
    }

    //体积单位换算操作
    private double volumeUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边进行换算为立方米为单位
            switch (leftPosition){
                case 0:
                    nowResultData *= Math.pow(10,9);
                    break;
                case 1:
                    break;
                case 2:
                    nowResultData *= 0.001;
                    break;
                case 3:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 4:
                    nowResultData *= Math.pow(10,-9);
                    break;
                case 5:
                    nowResultData *= 0.001;
                    break;
                case 6:
                    nowResultData *= Math.pow(10,-4);
                    break;
                case 7:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 8:
                    nowResultData *= Math.pow(10,-5);
                    break;
                case 9:
                    nowResultData *= 0.1;
                    break;
                case 10:
                    nowResultData *= Math.pow(10,-9);
                    break;
                case 11:
                    if (rightPosition <11 | rightPosition>13)
                        nowResultData *= 0.0283168;
                    break;
                case 12:
                    if (rightPosition>=11 && rightPosition<=13)
                        nowResultData *= 1.0 /1728;
                    else
                        nowResultData *= 0.0000164;
                    break;
                case 13:
                    if (rightPosition>=11 && rightPosition<=13)
                        nowResultData *= 27;
                    else
                        nowResultData *= 0.7645536;
                    break;
                case 14:
                    if (rightPosition<14)
                        nowResultData *= 1233.4818375;
                    break;
                case 15:
                    if (rightPosition>=14)
                        nowResultData *= 3.6856*Math.pow(10,-6);
                    else
                        nowResultData *= 0.0045461;
                    break;
                case 16:
                    if (rightPosition>=14)
                        nowResultData *= 3.0689*Math.pow(10,-6);
                    else
                        nowResultData *= 0.0037854;
                    break;
                case 17:
                    if (rightPosition>=14)
                        nowResultData *= 2.3032*Math.pow(10,-8);
                    else
                        nowResultData *= 0.0000284;
                    break;
                case 18:
                    if (rightPosition>=14)
                        nowResultData *= 2.3973*Math.pow(10,-8);
                    else
                        nowResultData *= 0.0000296;
                    break;

            }
            //用立方米换算为右边的单位
            switch (rightPosition){
                case 0:
                    nowResultData *= Math.pow(10,-9);
                    break;
                case 1:
                    break;
                case 2:
                    nowResultData *= 1000;
                    break;
                case 3:
                    nowResultData *= Math.pow(10,6);
                    break;
                case 4:
                    nowResultData *= Math.pow(10,9);
                    break;
                case 5:
                    nowResultData *= 1000;
                    break;
                case 6:
                    nowResultData *= 10000;
                    break;
                case 7:
                    nowResultData *= Math.pow(10,6);
                    break;
                case 8:
                    nowResultData *= Math.pow(10,5);
                    break;
                case 9:
                    nowResultData *= 10;
                    break;
                case 10:
                    nowResultData *= Math.pow(10,9);
                    break;
                case 11:
                    if (leftPosition <11 || leftPosition>13)
                        nowResultData *= 35.3147248;
                    break;
                case 12:
                    if (leftPosition>=11 && leftPosition>=13)
                        nowResultData *= 1728;
                    else
                        nowResultData *= 61023.8445022;
                    break;
                case 13:
                    if (leftPosition>=11 && leftPosition>=13)
                        nowResultData *= 1.0 /27;
                    else
                        nowResultData *= 1.3079528;
                    break;
                case 14:
                    if (leftPosition<14)
                        nowResultData *= 0.0008107;
                    break;
                case 15:
                    if (leftPosition>=14)
                        nowResultData *= 1/(3.6856*Math.pow(10,-6));
                    else
                        nowResultData *= 219.9691573;
                    break;
                case 16:
                    if (leftPosition>=14)
                        nowResultData *= 1/(3.0689*Math.pow(10,-6));
                    else
                        nowResultData *= 264.1720524;
                    break;
                case 17:
                    if (leftPosition>=14)
                        nowResultData *= 1/(2.3032*Math.pow(10,-8));
                    else
                        nowResultData *= 35198.873636;
                    break;
                case 18:
                    if (leftPosition>=14)
                        nowResultData *= 1/(2.3973*Math.pow(10,-8));
                    else
                        nowResultData *= 33818.0588434;
                    break;
            }
        }
        return nowResultData;
    }

    //质量单位换算操作
    private double qualityUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //把左边换算为千克
            switch (leftPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 0.001;
                    break;
                case 2:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 3:
                    nowResultData *= Math.pow(10,-9);
                    break;
                case 4:
                    nowResultData *= 1000;
                    break;
                case 5:
                    nowResultData *= 100;
                    break;
                case 6:
                    nowResultData *= 0.0002;
                    break;
                case 7:
                    nowResultData *= 2*Math.pow(10,-6);
                    break;
                case 8:
                    if (rightPosition<8 || rightPosition>16)
                        nowResultData *= 0.4535924;
                    break;
                case 9:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 0.0625;
                    else
                        nowResultData *= 0.0283495;
                    break;
                case 10:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 1.0 /7000;
                    else
                        nowResultData *= 0.0000648;
                    break;
                case 11:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 2240;
                    else
                        nowResultData *= 1016.0469088;
                    break;
                case 12:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 2000;
                    else
                        nowResultData *= 907.18474;
                    break;
                case 13:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 112;
                    else
                        nowResultData *= 50.8023454;
                    break;
                case 14:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 100;
                    else
                        nowResultData *= 45.359237;
                    break;
                case 15:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 14;
                    else
                        nowResultData *= 6.3502932;
                    break;
                case 16:
                    if (rightPosition>=8 && rightPosition<=16)
                        nowResultData *= 1.0 /256;
                    else
                        nowResultData *= 0.0017718;
                    break;
                case 17:
                    nowResultData *= 50;
                    break;
                case 18:
                    nowResultData *= 0.5;
                    break;
                case 19:
                    nowResultData *= 0.05;
                    break;
                case 20:
                    nowResultData *= 0.005;
                    break;
            }
            //将千克换算为右边单位
            switch (rightPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 1000;
                    break;
                case 2:
                    nowResultData *= Math.pow(10,6);
                    break;
                case 3:
                    nowResultData *= Math.pow(10,9);
                    break;
                case 4:
                    nowResultData *= 0.001;
                    break;
                case 5:
                    nowResultData *= 0.01;
                    break;
                case 6:
                    nowResultData *= 5000;
                    break;
                case 7:
                    nowResultData *= 500000;
                    break;
                case 8:
                    if (leftPosition<8 || leftPosition>16)
                        nowResultData *= 2.2046226;
                    break;
                case 9:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 16;
                    else
                        nowResultData *= 35.2739619;
                    break;
                case 10:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 7000;
                    else
                        nowResultData *= 15432.3583529;
                    break;
                case 11:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 1.0 /2240;
                    else
                        nowResultData *= 0.0009842;
                    break;
                case 12:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 0.0005;
                    else
                        nowResultData *= 0.0011023;
                    break;
                case 13:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 1.0 /112;
                    else
                        nowResultData *= 0.0196841;
                    break;
                case 14:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 0.01;
                    else
                        nowResultData *= 0.0220462;
                    break;
                case 15:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 1.0 /14;
                    else
                        nowResultData *= 0.157473;
                    break;
                case 16:
                    if (leftPosition>=8 && leftPosition<=16)
                        nowResultData *= 256;
                    else
                        nowResultData *= 564.3833912;
                    break;
                case 17:
                    nowResultData *= 0.02;
                    break;
                case 18:
                    nowResultData *= 2;
                    break;
                case 19:
                    nowResultData *= 20;
                    break;
                case 20:
                    nowResultData *= 200;
                    break;
            }
        }
        return nowResultData;
    }

    //温度单位换算操作
    private double temperatureUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位统一换算为摄氏度
            switch (leftPosition){
                case 0:
                    break;
                case 1:
                    nowResultData = (nowResultData-32)/1.8;
                    break;
                case 2:
                    nowResultData -= 273.15;
                    break;
                case 3:
                    nowResultData = nowResultData*(9/5)-273.15;
                    break;
                case 4:
                    nowResultData /= 1.25;
                    break;
            }
            //摄氏度换算为右边单位
            switch (rightPosition){
                case 0:
                    break;
                case 1:
                    nowResultData = nowResultData*1.8+32;
                    break;
                case 2:
                    nowResultData += 273.15;
                    break;
                case 3:
                    nowResultData = (nowResultData+273.15)*(5/9);
                    break;
                case 4:
                    nowResultData *= 1.25;
            }
        }
        return nowResultData;
    }

    //功率单位换算操作
    private double powerUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位换位为瓦
            switch (leftPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 1000;
                    break;
                case 2:
                    nowResultData *= 745.699872;
                    break;
                case 3:
                    nowResultData *= 735.49875;
                    break;
                case 4:
                    nowResultData *= 9.80665;
                    break;
                case 5:
                    nowResultData *= 4184.1004;
                    break;
                case 6:
                    nowResultData *= 1055.05585;
                    break;
                case 7:
                    nowResultData *= 1.3558179;
                    break;
                case 8:
                    nowResultData *= 1;
                    break;
                case 9:
                    nowResultData *= 1;
                    break;
            }
            //瓦换算为右边单位
            switch (rightPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 0.001;
                    break;
                case 2:
                    nowResultData *= 1/745.699872;
                    break;
                case 3:
                    nowResultData *= 1/735.49875;
                    break;
                case 4:
                    nowResultData *= 1/9.80665;
                    break;
                case 5:
                    nowResultData *= 1/4184.1004;
                    break;
                case 6:
                    nowResultData *= 1/1055.05585;
                    break;
                case 7:
                    nowResultData *= 0.7375621;
                    break;
                case 8:
                    nowResultData *= 1;
                    break;
                case 9:
                    nowResultData *= 1;
                    break;
            }
        }
        return nowResultData;
    }

    //功/能/热单位换算操作
    private double functionalHeatUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位换算为焦耳
            switch (leftPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 1/0.102;
                    break;
                case 2:
                    nowResultData *= 1/(3.7767*Math.pow(10,-7));
                    break;
                case 3:
                    nowResultData *= 1/(3.7251*Math.pow(10,-7));
                    break;
                case 4:
                    nowResultData *= 3600000;
                    break;
                case 5:
                    nowResultData *= 3600000;
                    break;
                case 6:
                    nowResultData *= 1/0.2389;
                    break;
                case 7:
                    nowResultData *= 1/0.0002389;
                    break;
                case 8:
                    nowResultData *= 1055.0558526;
                    break;
                case 9:
                    nowResultData *= 1/0.7376;
                    break;
                case 10:
                    nowResultData *= 1000;
                    break;
            }
            //焦耳换算为右边单位
            switch (rightPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 0.102;
                    break;
                case 2:
                    nowResultData *= 3.7767*Math.pow(10,-7);
                    break;
                case 3:
                    nowResultData *= 3.7251*Math.pow(10,-7);
                    break;
                case 4:
                    nowResultData *= 1/3600000;
                    break;
                case 5:
                    nowResultData *= 1/3600000;
                    break;
                case 6:
                    nowResultData *= 0.2389;
                    break;
                case 7:
                    nowResultData *= 0.0002389;
                    break;
                case 8:
                    nowResultData *= 0.0009478;
                    break;
                case 9:
                    nowResultData *= 0.7376;
                    break;
                case 10:
                    nowResultData *= 0.001;
                    break;
            }
        }
        return nowResultData;
    }

    //密度单位换算操作
    private double densityUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位换算为千克/立方米
            switch (leftPosition){
                case 0:
                    nowResultData *= 1000000;
                    break;
                case 1:
                    nowResultData *= 1000;
                    break;
                case 2:
                    break;
                case 3:
                    nowResultData *= 1000;
                    break;
                case 4:
                    nowResultData *= 1;
                    break;
                case 5:
                    nowResultData *= 0.001;
                    break;
            }
            //千克/立方米换算为右边单位
            switch (rightPosition){
                case 0:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 1:
                    nowResultData *= 0.001;
                    break;
                case 2:
                    break;
                case 3:
                    nowResultData *= 0.001;
                    break;
                case 4:
                    nowResultData *= 1;
                    break;
                case 5:
                    nowResultData *= 0.001;
                    break;
            }
        }
        return nowResultData;
    }

    //力单位换算操作
    private double newtonUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位换算为牛
            switch (leftPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 1000;
                    break;
                case 2:
                    nowResultData *= 9.80665;
                    break;
                case 3:
                    nowResultData *= 0.0098067;
                    break;
                case 4:
                    nowResultData *= 9806.65;
                    break;
                case 5:
                    nowResultData *= 4.448222;
                    break;
                case 6:
                    nowResultData *= 4448.221615;
                    break;
                case 7:
                    nowResultData *= 0.00001;
                    break;
            }
            //牛换算为右边单位
            switch (rightPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 0.001;
                    break;
                case 2:
                    nowResultData *= 1/9.80665;
                    break;
                case 3:
                    nowResultData *= 1/0.0098067;
                    break;
                case 4:
                    nowResultData *= 1/9806.65;
                    break;
                case 5:
                    nowResultData *= 1/4.448222;
                    break;
                case 6:
                    nowResultData *= 1/4448.221615;
                    break;
                case 7:
                    nowResultData *= 100000;
                    break;
            }
        }
        return nowResultData;
    }

    //时间单位换算操作
    private double timeUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位统一为秒
            switch (leftPosition){
                case 0:
                    nowResultData *= 31536000;
                    break;
                case 1:
                    nowResultData *= 604800;
                    break;
                case 2:
                    nowResultData *= 86400;
                    break;
                case 3:
                    nowResultData *= 3600;
                    break;
                case 4:
                    nowResultData *= 60;
                    break;
                case 5:
                    break;
                case 6:
                    nowResultData *= 0.001;
                    break;
                case 7:
                    nowResultData *= Math.pow(10,-6);
                    break;
                case 8:
                    nowResultData *= Math.pow(10,-9);
                    break;
            }
            //秒统一为右边单位
            switch (rightPosition){
                case 0:
                    nowResultData *= 1.0 /31536000;
                    break;
                case 1:
                    nowResultData *= 1.0 /604800;
                    break;
                case 2:
                    nowResultData *= 1.0 /86400;
                    break;
                case 3:
                    nowResultData *= 1.0 /3600;
                    break;
                case 4:
                    nowResultData *= 1.0 /60;
                    break;
                case 5:
                    break;
                case 6:
                    nowResultData *= 1000;
                    break;
                case 7:
                    nowResultData *= Math.pow(10,6);
                    break;
                case 8:
                    nowResultData *= Math.pow(10,9);
                    break;
            }
        }
        return nowResultData;
    }

    //速度单位换算操作
    private double speedUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位换算为米/秒
            switch (leftPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 1000;
                    break;
                case 2:
                    nowResultData *= 1/3.6;
                    break;
                case 3:
                    nowResultData *= 1/(3.3356*Math.pow(10,-9));
                    break;
                case 4:
                    nowResultData *= 340.3;
                    break;
                case 5:
                    nowResultData *= 0.44704;
                    break;
                case 6:
                    nowResultData *= 0.0254;
                    break;
            }
            //米/秒换算为右边单位
            switch (rightPosition){
                case 0:
                    break;
                case 1:
                    nowResultData *= 0.001;
                    break;
                case 2:
                    nowResultData *= 3.6;
                    break;
                case 3:
                    nowResultData *= 3.3356*Math.pow(10,-9);
                    break;
                case 4:
                    nowResultData *= 1/340.3;
                    break;
                case 5:
                    nowResultData *= 1/0.44704;
                    break;
                case 6:
                    nowResultData *= 1/0.0254;
                    break;
            }
        }
        return nowResultData;
    }

    //存储单位换算操作
    private double saveUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            //左边单位换算为字节
            switch (leftPosition){
                case 0:
                    nowResultData *= 0.125;
                    break;
                case 1:
                    break;
                case 2:
                    nowResultData *= 1024;
                    break;
                case 3:
                    nowResultData *= 1048576;
                    break;
                case 4:
                    nowResultData *= 1.073741824*Math.pow(10,9);
                    break;
                case 5:
                    nowResultData *= 1.099511627776*Math.pow(10,12);
                    break;
                case 6:
                    nowResultData *= 1.1259*Math.pow(10,15);
                    break;
                case 7:
                    nowResultData *= 1.1529*Math.pow(10,18);
                    break;
            }
            //字节换算为右边单位
            switch (rightPosition){
                case 0:
                    nowResultData *= 8;
                    break;
                case 1:
                    break;
                case 2:
                    nowResultData *= 1.0 /1024;
                    break;
                case 3:
                    nowResultData *= 1.0 /1048576;
                    break;
                case 4:
                    nowResultData *= 1/(1.073741824*Math.pow(10,9));
                    break;
                case 5:
                    nowResultData *= 1/(1.099511627776*Math.pow(10,12));
                    break;
                case 6:
                    nowResultData *= 1/(1.1259*Math.pow(10,15));
                    break;
                case 7:
                    nowResultData *= 1/(1.1529*Math.pow(10,18));
                    break;
            }
        }
        return nowResultData;
    }

    //人民币单位换算擦欧洲哦
    private double rmbUnitConversion(double nowResultData){
        if (leftPosition != rightPosition){
            switch (leftPosition){
                case 0:
                    if (rightPosition == 1)
                        nowResultData *= 10;
                    else if (rightPosition == 2)
                        nowResultData *= 100;
                    break;
                case 1:
                    if (rightPosition == 0)
                        nowResultData *= 0.1;
                    else if (rightPosition ==2)
                        nowResultData *= 10;
                    break;
                case 2:
                    if (rightPosition == 0)
                        nowResultData *= 0.01;
                    else if (rightPosition == 1)
                        nowResultData *= 0.1;
            }
        }
        return nowResultData;
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