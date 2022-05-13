package com.example.life_assistant.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.luozm.captcha.Captcha;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegistActivity extends AppCompatActivity{

    private Toolbar user_registReturn;
    private EditText user_registPhone,user_registPassword,user_registPasswordAgain;
    private Button user_regist;
    private Captcha captcha;
    private CheckBox protocol_checkBox;

    private static boolean state_verification = false;//验证是否成功的标志

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        initView();
    }

    private void initView(){
        user_registReturn = findViewById(R.id.user_registReturn);
        user_registPhone = findViewById(R.id.user_registPhone);
        user_registPassword = findViewById(R.id.user_registPassword);
        user_registPasswordAgain = findViewById(R.id.user_registPasswordAgain);
        user_regist = findViewById(R.id.user_regist);
        protocol_checkBox = findViewById(R.id.protocol_checkBox);
        captcha = findViewById(R.id.captcha);

        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);//数据持久化

        //toolbar的navigation监听
        user_registReturn.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //验证码监听
        captcha.setCaptchaListener(new Captcha.CaptchaListener() {
            @Override
            public String onAccess(long time) {
                state_verification = true;
                return null;
            }

            @Override
            public String onFailed(int failCount) {
                //失败后就刷新
                captcha.reset(true);
                return null;
            }

            @Override
            public String onMaxFailed() {
                return null;
            }
        });


        user_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (protocol_checkBox.isChecked()){
                    if (state_verification){
                        String mobilePhoneNumber = user_registPhone.getText().toString().trim();
                        if (!mobilePhoneNumber.matches("^\\d{11}$")){
                            Toast.makeText(RegistActivity.this,"手机号码不符合规范",Toast.LENGTH_SHORT).show();
                            user_registPhone.setText("");
                            errorRegist();
                            return;
                        }
                        String userPassword1 = user_registPassword.getText().toString().trim();
                        String userPassword2 = user_registPasswordAgain.getText().toString().trim();
                        if (!userPassword1.matches("^\\w{6,12}$") || !userPassword2.matches("^\\w{6,12}$")){
                            Toast.makeText(RegistActivity.this,"密码不符合规范",Toast.LENGTH_SHORT).show();
                            errorRegist();
                            return;
                        }

                        if (userPassword1.equals(userPassword2)){
                            User user = new User();
                            user.setMobilePhoneNumber(mobilePhoneNumber);
                            user.setUserPassword(mobilePhoneNumber);
                            user.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (s != null){
                                        //数据持久化，将新创建的用户存入Token
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("objectId",s);
                                        editor.putString("userName","newUser");
                                        editor.apply();
                                        //直接跳转到主页面
                                        startActivity(new Intent(RegistActivity.this,com.example.life_assistant.index.indexActivity.class));
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(RegistActivity.this,"注册失败，账户可能已存在",Toast.LENGTH_SHORT).show();
                                        errorRegist();
                                        user_registPhone.setText("");
                                    }
                                }
                            });
                        }
                        else
                            Toast.makeText(RegistActivity.this,"请进行验证",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(RegistActivity.this,"请勾选《用户注册服务协议》",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //输入错误后的统一操作
    private void errorRegist(){
        user_registPassword.setText("");
        user_registPasswordAgain.setText("");
    }



}