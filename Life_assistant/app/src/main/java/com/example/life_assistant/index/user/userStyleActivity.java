package com.example.life_assistant.index.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.life_assistant.R;

public class userStyleActivity extends AppCompatActivity {
    private RadioGroup user_style_radioGroup;
    private Toolbar user_style_toolbar;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("styleToken",MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme",null);
        setStyle(theme);

        setContentView(R.layout.activity_user_style);

        init();
    }

    private void init() {
        user_style_radioGroup = findViewById(R.id.user_style_radioGroup);
        user_style_toolbar = findViewById(R.id.user_style_toolbar);

        //raidioGroup的点击事件监听
        user_style_radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.user_style_green://绿色主题
                        updateStyle("green");
                        break;
                    case R.id.user_style_yellow://黄色主题
                        updateStyle("yellow");
                        break;
                    case R.id.user_style_purple://紫色主题
                        updateStyle("purple");
                        break;
                    case R.id.user_style_blue://蓝色主题
                        updateStyle("blue");
                        break;
                    case R.id.user_style_pink://粉色主题
                        updateStyle("pink");
                        break;
                    case R.id.user_style_black://黑色主题
                        updateStyle("black");
                        break;
                    case R.id.user_style_white://白色主题
                        updateStyle("white");
                        break;
                }
            }
        });

        //toolbar的navigationIcon点击事件监听
        user_style_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //为切换皮肤后返回主页面提供标识符
                editor.putBoolean("isChangeTheme",true);
                editor.apply();
                finish();
            }
        });
    }

    //将修改的主题颜色放入数据持久化中，并重启当前activity
    private void updateStyle(String themeColor) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //将颜色数据存储到Token中，方便后面使用
        editor.putString("theme",themeColor);
        editor.apply();
        //重启当前Activity
        finish();
        startActivity(getIntent());
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

    //按下back键返回时的监听


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //为切换皮肤后返回主页面提供标识符
        editor.putBoolean("isChangeTheme",true);
        editor.apply();
    }
}