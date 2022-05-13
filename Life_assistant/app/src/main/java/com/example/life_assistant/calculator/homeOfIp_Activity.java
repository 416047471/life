package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.life_assistant.R;
import com.example.life_assistant.calculator.bean.IpAll;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class homeOfIp_Activity extends AppCompatActivity {
    private EditText ip_ipNumber;
    private Button ip_button;
    private TextView ip_ip,ip_country,ip_province,ip_city,ip_isp;

    //okhttp
    private OkHttpClient okHttpClient;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_home_of_ip);

        init();
    }

    private void init() {
        ip_ipNumber = findViewById(R.id.ip_ipNumber);
        ip_button = findViewById(R.id.ip_button);
        ip_ip = findViewById(R.id.ip_ip);
        ip_country = findViewById(R.id.ip_country);
        ip_province = findViewById(R.id.ip_province);
        ip_city = findViewById(R.id.ip_city);
        ip_isp = findViewById(R.id.ip_isp);

        okHttpClient = new OkHttpClient.Builder().build();

        //查询的点击事件
        ip_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    //通过url查询ip数据
    private void getData() {
        final String ipNumber = ip_ipNumber.getText().toString();
        url = "http://apis.juhe.cn/ip/ipNewV3?ip="+ipNumber+
                "&key=b00148032f8e13e13da8b908c755933e";
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
                    //使用Gson解析JSon数据
                    final IpAll ipAll = new Gson().fromJson(json,IpAll.class);
                    if (ipAll != null){
                        //进入主线程操作控件
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ip_ip.setText(ipNumber);
                                ip_country.setText(ipAll.getIp().getCountry());
                                ip_province.setText(ipAll.getIp().getProvince());
                                ip_city.setText(ipAll.getIp().getCity());
                                ip_isp.setText(ipAll.getIp().getIsp());
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