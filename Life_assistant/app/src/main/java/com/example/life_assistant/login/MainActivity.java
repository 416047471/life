package com.example.life_assistant.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.index.indexActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MainActivity extends AppCompatActivity{

    private EditText mobilePhoneNumber,userPassword;
    private Button user_login,verification_button;
    private TextView regist;
    private ImageView verification_image;
    private CheckBox protocol_checkBox;
    private CustomVideoView videoView;

    private AlertDialog dialog;

    private SharedPreferences sharedPreferences;//数据持久化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){

        mobilePhoneNumber = findViewById(R.id.mobilePhoneNumber);
        userPassword = findViewById(R.id.userPassword);
        user_login = findViewById(R.id.user_login);
        regist = findViewById(R.id.regist);
        protocol_checkBox = findViewById(R.id.protocol_checkBox);
        videoView = findViewById(R.id.videoView);
        videoView.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.login));
        videoView.start();

        //数据持久化
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });



        //登录按钮监听
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //注册按钮监听
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegistActivity.class));
                finish();
            }
        });

    }

    private void login() {
        if (protocol_checkBox.isChecked()){
            String mobilePhone = mobilePhoneNumber.getText().toString().trim();
            if (!mobilePhone.matches("^\\d{11}$")){
                Toast.makeText(MainActivity.this,"输入的手机号码不规范",Toast.LENGTH_SHORT).show();
                mobilePhoneNumber.setText("");
                userPassword.setText("");
                return;
            }
            String nowUserPassword = userPassword.getText().toString().trim();
            if (!nowUserPassword.matches("^\\w{6,12}$")){
                Toast.makeText(MainActivity.this,"密码不规范（由字母、数字、下划线组成的6-12位数）",Toast.LENGTH_SHORT).show();
                userPassword.setText("");
                return;
            }
            BmobQuery<User> query= queryUserData("mobilePhoneNumber",mobilePhone,"userPassword",nowUserPassword);
            query.findObjects(new FindListener<User>() {
                @Override
                public void done(List<User> list, BmobException e) {
                    if (list.size() > 0){
                        //将objectId和userName存储到Token中，方便后面使用
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("objectId",list.get(0).getObjectId());
                        editor.putString("userName",list.get(0).getUserName());
                        editor.apply();

                        startActivity(new Intent(MainActivity.this,indexActivity.class));
                        finish();
                    }else {
                        Toast.makeText(MainActivity.this,"不存在该账户",Toast.LENGTH_SHORT).show();
                        mobilePhoneNumber.setText("");
                        userPassword.setText("");
                    }
                }
            });
        }
        else
            Toast.makeText(this,"请勾选《用户注册服务协议》",Toast.LENGTH_SHORT).show();
    }

    //装填User表的两个列的某个数据
    public BmobQuery<User> queryUserData(String columnTitle1,String columnContent1,String columnTitle2,String columnContent2){
        BmobQuery<User> userBmobQuery1 = new BmobQuery<User>();
        userBmobQuery1.addWhereEqualTo(columnTitle1,columnContent1);
        BmobQuery<User> userBmobQuery2 = new BmobQuery<User>();
        userBmobQuery2.addWhereEqualTo(columnTitle2,columnContent2);
        List<BmobQuery<User>> andQuerys = new ArrayList<BmobQuery<User>>();
        andQuerys.add(userBmobQuery1);
        andQuerys.add(userBmobQuery2);
        BmobQuery<User> query = new BmobQuery<User>();
        query.and(andQuerys);
        return query;
    }

}