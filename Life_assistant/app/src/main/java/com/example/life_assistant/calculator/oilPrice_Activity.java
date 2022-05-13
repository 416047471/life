package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.calculator.bean.OilAll;
import com.google.gson.Gson;
import com.lingber.mycontrol.datagridview.DataGridView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class oilPrice_Activity extends AppCompatActivity {
    private Toolbar oil_toolbar;
    private DataGridView oil_dataGridView;//开源表格控件

    private OkHttpClient okHttpClient;
    private String url = "http://apis.juhe.cn/gnyj/query?key=721eb0ee8efb6e33c15b6cab18d6770f";
    private OilAll oilAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_oil_price);

        init();
    }

    private void init() {
        oil_toolbar = findViewById(R.id.oil_toolbar);
        oil_dataGridView = findViewById(R.id.oil_dataGridView);

        okHttpClient = new OkHttpClient.Builder().build();

        /*
        * 设置dataGridView格式
        * */
        oil_dataGridView.setHeaderHeight(170);
        oil_dataGridView.setColunms(5);//列数
        oil_dataGridView.setHeaderContentByStringId(new int[]{R.string.oil_city,R.string.oil_92h,
                R.string.oil_95h,R.string.oil_98h,R.string.oil_0h});//表头内容
        oil_dataGridView.setFieldNames(new String[]{"city","ninety_two_liters","ninety_five_liters",
                "ninety_eight_liters","zero_liters"});//绑定字段
        oil_dataGridView.setColunmWeight(new float[]{1,1,1,1,1});//设置column占比
        oil_dataGridView.setCellContentView(new Class[]
                {TextView.class,TextView.class,TextView.class,TextView.class,TextView.class});//每个单元格包含控件
        oil_dataGridView.setSelectedMode(1);//设置选中模式
        /*oil_dataGridView.setFlipOverEnable(true,9,getFragmentManager());*///启动翻页
        getData();
    }

    private void getData() {
        new Thread(){
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Call call = okHttpClient.newCall(request);
                try {
                    Response response = call.execute();
                    String json = response.body().string();
                    //使用GSon解析Json数据
                    oilAll = new Gson().fromJson(json, OilAll.class);
                    if (oilAll != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                oil_dataGridView.setDataSource(oilAll.getOil());//设置表格数据源
                                oil_dataGridView.initDataGridView();//初始化表格
                            }
                        });
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