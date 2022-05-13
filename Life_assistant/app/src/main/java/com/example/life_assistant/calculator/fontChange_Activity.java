package com.example.life_assistant.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.life_assistant.R;
import com.example.life_assistant.calculator.bean.Font;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class fontChange_Activity extends AppCompatActivity{
    private EditText font_inputText;
    private Button font_simplifiedButton,font_unsimplifiedButton,font_martianButton;
    private TextView font_resultText;

    //okHttp
    private OkHttpClient okHttpClient;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_font_change);

        init();
    }

    private void init() {
        font_inputText = findViewById(R.id.font_inputText);
        font_simplifiedButton = findViewById(R.id.font_simplifiedButton);
        font_unsimplifiedButton = findViewById(R.id.font_unsimplifiedButton);
        font_martianButton = findViewById(R.id.font_martianButton);
        font_resultText = findViewById(R.id.font_resultText);

        okHttpClient = new OkHttpClient.Builder().build();


        font_simplifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = font_inputText.getText().toString();
                url = "http://japi.juhe.cn/charconvert/change.from?text="+inputText
                        +"&type=0&key=4fa98448b735d6e4a566f5043350e9ae";
                getData();
            }
        });
        font_unsimplifiedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = font_inputText.getText().toString();
                url = "http://japi.juhe.cn/charconvert/change.from?text="+inputText
                        +"&type=1&key=4fa98448b735d6e4a566f5043350e9ae";
                getData();
            }
        });
        font_martianButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = font_inputText.getText().toString();
                url = "http://japi.juhe.cn/charconvert/change.from?text="+inputText
                        +"&type=2&key=4fa98448b735d6e4a566f5043350e9ae";
                getData();
            }
        });
    }


    //子线程进行异步查询
    private void getData(){
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
                    final Font font = new Gson().fromJson(json,Font.class);
                    if (font != null){
                        //进入主线程操作控件
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                font_resultText.setText(font.getOutstr());
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