package com.example.life_assistant.index.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.login.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class userSecurityUpdatePasswordActivity extends AppCompatActivity {

    private Toolbar userSecurityUpdatePassword_toolbar;
    private EditText userSecurityUpdatePassword_oldPassword,userSecurityUpdatePassword_newPasswordOne,
            userSecurityUpdatePassword_newPasswordTwo;
    private Button userSecurityUpdatePassword_updateButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_user_security_update_password);

        init();
    }

    private void init() {
        userSecurityUpdatePassword_toolbar = findViewById(R.id.userSecurityUpdatePassword_toolbar);
        userSecurityUpdatePassword_oldPassword =findViewById(R.id.userSecurityUpdatePassword_oldPassword);
        userSecurityUpdatePassword_newPasswordOne = findViewById(R.id.userSecurityUpdatePassword_newPasswordOne);
        userSecurityUpdatePassword_newPasswordTwo = findViewById(R.id.userSecurityUpdatePassword_newPasswordTwo);
        userSecurityUpdatePassword_updateButton = findViewById(R.id.userSecurityUpdatePassword_updateButton);

        //取到之前存储的Token，可以通过get方法取传入的token的key的值
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);
        //修改密碼按钮监听事件
        userSecurityUpdatePassword_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();//修改密码
            }
        });
        //toolbar监听事件
        userSecurityUpdatePassword_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //修改密碼方法
    private void updatePassword() {
        final String oldPassword = userSecurityUpdatePassword_oldPassword.getText().toString();
        final String newPasswordOne = userSecurityUpdatePassword_newPasswordOne.getText().toString();
        String newPasswordTwo = userSecurityUpdatePassword_newPasswordTwo.getText().toString();
        //判断两次新密码是否相同
        if (newPasswordOne.equals(newPasswordTwo)){
            //判断密码是否符合规定的正则表达式
            if (oldPassword.matches("^\\w{6,12}$") && newPasswordOne.matches("^\\w{6,12}$")){
                //去数据库中查询原密码是否正确
                //获取objectId
                final String objectId = sharedPreferences.getString("objectId",null);
                BmobQuery<User> query = new BmobQuery<>();
                query.getObject(objectId, new QueryListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e == null){
                            if (user.getUserPassword().equals(oldPassword)){//密码正确
                                user.setUserPassword(newPasswordOne);
                                user.update(objectId, new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (e == null){
                                            //修改成功删除该activity（即跳转至安全中心）
                                            finish();
                                        }
                                    }
                                });
                            }
                        }else{
                            userSecurityUpdatePassword_newPasswordOne.setText("");
                            userSecurityUpdatePassword_newPasswordTwo.setText("");
                            userSecurityUpdatePassword_oldPassword.setText("");
                            Toast.makeText(userSecurityUpdatePasswordActivity.this,"原密码错误",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else {
                //密码不符合正则表达式,清空
                userSecurityUpdatePassword_newPasswordOne.setText("");
                userSecurityUpdatePassword_newPasswordTwo.setText("");
                userSecurityUpdatePassword_oldPassword.setText("");
                Toast.makeText(this,"输入的密码不符合正则表达式，请重新输入密码",Toast.LENGTH_SHORT).show();
            }
        }else {
            //两次密码不相同,清空
            userSecurityUpdatePassword_newPasswordOne.setText("");
            userSecurityUpdatePassword_newPasswordTwo.setText("");
            Toast.makeText(this,"两次密码不相同，请重新输入新密码",Toast.LENGTH_SHORT).show();
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