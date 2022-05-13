package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.calculator.bean.Phone;
import com.example.life_assistant.calculator.bean.PhoneAll;
import com.example.life_assistant.login.MainActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class homeOfPhone_Activity extends AppCompatActivity {

    private EditText phone_phoneNumber;
    private Button phone_button;
    private TextView phone_phoneSegment,phone_provinceAndCity,phone_company,phone_areacode,phone_zip;

    //okhttp
    private OkHttpClient okHttpClient;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_home_of_phone);

        init();
    }

    private void init() {
        phone_phoneNumber = findViewById(R.id.phone_phoneNumber);
        phone_button = findViewById(R.id.phone_button);
        phone_phoneSegment = findViewById(R.id.phone_phoneSegment);
        phone_provinceAndCity = findViewById(R.id.phone_provinceAndCity);
        phone_company = findViewById(R.id.phone_company);
        phone_areacode = findViewById(R.id.phone_areacode);
        phone_zip = findViewById(R.id.phone_zip);

        okHttpClient =new OkHttpClient.Builder().build();

        //查询的点击事件
        phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    //通过url查询手机号码数据
    private void getData() {
        final String phoneNumber = phone_phoneNumber.getText().toString();
        if (phoneNumber.matches("^\\d{11}$")) {//先判断手机号码是否符合11位数
            url = "http://apis.juhe.cn/mobile/get?phone=" + phoneNumber
                    + "&key=1badc64c5f37b586ad17c04d857ba1f3";
            new Thread() {
                @Override
                public void run() {
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Call call =okHttpClient.newCall(request);
                    try {
                        Response response =call.execute();
                        String json =response.body().string();
                        //使用GSON的fromJson解析json数据
                        final PhoneAll phoneAll = new Gson().fromJson(json,PhoneAll.class);
                        if (phoneAll != null){
                            //进入主线程操作控件
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    phone_phoneSegment.setText(phoneNumber);
                                    phone_provinceAndCity.setText(phoneAll.getPhone().getProvince()+
                                            "  "+phoneAll.getPhone().getCity());
                                    phone_company.setText(phoneAll.getPhone().getCompany());
                                    phone_areacode.setText(phoneAll.getPhone().getAreacode());
                                    phone_zip.setText(phoneAll.getPhone().getZip());
                                }
                            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        } else {
            phone_phoneNumber.setText("");
            Toast.makeText(homeOfPhone_Activity.this,"请输入11位数字的手机号码",Toast.LENGTH_SHORT).show();
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