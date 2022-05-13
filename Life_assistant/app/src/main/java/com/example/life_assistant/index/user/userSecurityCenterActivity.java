package com.example.life_assistant.index.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.life_assistant.R;

public class userSecurityCenterActivity extends AppCompatActivity {

    private Toolbar securityCenter_toolbar;
    private Button securityCenter_updatePassword,securityCenter_cancelPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_user_security_center);

        init();
    }

    private void init() {
        securityCenter_toolbar = findViewById(R.id.securityCenter_toolbar);
        securityCenter_updatePassword = findViewById(R.id.securityCenter_updatePassword);
        securityCenter_cancelPassword = findViewById(R.id.securityCenter_cancelPassword);

        //设置按钮跳转监听
        securityCenter_updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userSecurityCenterActivity.this,userSecurityUpdatePasswordActivity.class));
            }
        });
        securityCenter_cancelPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(userSecurityCenterActivity.this, userSecurityCancelActivity.class));
            }
        });
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