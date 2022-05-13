package com.example.life_assistant.index.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.index.gps.Sport;
import com.example.life_assistant.index.study.diary.Diary;
import com.example.life_assistant.index.study.matter.Matter;
import com.example.life_assistant.index.study.memorandum.Memorandum;
import com.example.life_assistant.index.study.target.Target;
import com.example.life_assistant.login.User;

import java.util.Arrays;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class  userSecurityCancelActivity extends AppCompatActivity {

    private Toolbar userSecurityCancel_toolbar;
    private EditText userSecurityCancel_password;
    private Button userSecurityCancel_cancelButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_user_security_cancel);

        init();
    }

    private void init() {
        userSecurityCancel_toolbar = findViewById(R.id.userSecurityCancel_toolbar);
        userSecurityCancel_password = findViewById(R.id.userSecurityCancel_password);
        userSecurityCancel_cancelButton = findViewById(R.id.userSecurityCancel_cancelButton);

        //取到之前存储的Token，可以通过get方法取传入的token的key的值
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);

        //按钮的注销操作
        userSecurityCancel_cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();//注销操作

            }
        });

    }

    //注销操作方法
    private void cancel() {
        final String password = userSecurityCancel_password.getText().toString();
        //判断密码是否符合规定的正则表达式
        if (password.matches("^\\w{6,12}$")){//符合
            //获取objectId
            String objectId = sharedPreferences.getString("objectId",null);

            //查询密码是否正确
            BmobQuery<User> query = new BmobQuery<>();
            query.getObject(objectId, new QueryListener<User>() {
                @Override
                public void done(final User user, BmobException e) {
                    if (e == null){
                        if (user.getUserPassword().equals(password)){//密码正确
                            //在Bmob云端中删除该账号所有信息
                            user.delete(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null){//删除成功
                                        //删除账号相关的Matter、Memorandum、Target、Diary和Sport表数据
                                       BmobQuery<Matter> matterBmobQuery = new BmobQuery<>();
                                       matterBmobQuery.addWhereEqualTo("user",user.getObjectId());
                                       matterBmobQuery.findObjects(new FindListener<Matter>() {
                                           @Override
                                           public void done(List<Matter> list, BmobException e) {
                                               if (e == null){
                                                   for (int i = 0 ; i<list.size();i++){
                                                       list.get(i).delete(new UpdateListener() {
                                                           @Override
                                                           public void done(BmobException e) {

                                                           }
                                                       });
                                                   }
                                               }
                                           }
                                       });
                                        //删除账号相关的Memorandum表数据
                                       BmobQuery<Memorandum> memorandumBmobQuery = new BmobQuery<>();
                                       memorandumBmobQuery.addWhereEqualTo("user",user.getObjectId());
                                       memorandumBmobQuery.findObjects(new FindListener<Memorandum>() {
                                           @Override
                                           public void done(List<Memorandum> list, BmobException e) {
                                               if (e == null){
                                                   for (int i = 0 ; i<list.size();i++){
                                                       list.get(i).delete(new UpdateListener() {
                                                           @Override
                                                           public void done(BmobException e) {

                                                           }
                                                       });
                                                   }
                                               }
                                           }
                                       });
                                        //删除账号相关的Target表数据
                                        BmobQuery<Target> targetBmobQuery = new BmobQuery<>();
                                        targetBmobQuery.addWhereEqualTo("user",user.getObjectId());
                                        targetBmobQuery.findObjects(new FindListener<Target>() {
                                            @Override
                                            public void done(List<Target> list, BmobException e) {
                                                if (e == null){
                                                    for (int i = 0 ; i<list.size();i++){
                                                        list.get(i).delete(new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });
                                        //删除账号相关的Diary表数据
                                        BmobQuery<Diary> diaryBmobQuery = new BmobQuery<>();
                                        diaryBmobQuery.addWhereEqualTo("user",user.getObjectId());
                                        diaryBmobQuery.findObjects(new FindListener<Diary>() {
                                            @Override
                                            public void done(List<Diary> list, BmobException e) {
                                                if (e == null){
                                                    for (int i = 0 ; i<list.size();i++){
                                                        list.get(i).delete(new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });
                                        //删除账号相关的Sport表数据
                                        BmobQuery<Sport> sportBmobObject = new BmobQuery<>();
                                        sportBmobObject.addWhereEqualTo("user",user.getObjectId());
                                        sportBmobObject.findObjects(new FindListener<Sport>() {
                                            @Override
                                            public void done(List<Sport> list, BmobException e) {
                                                if (e == null){
                                                    for (int i = 0 ; i<list.size();i++){
                                                        list.get(i).delete(new UpdateListener() {
                                                            @Override
                                                            public void done(BmobException e) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }
                                        });

                                        /*
                                        * 删除完成。开始清空持久化数据信息
                                        * 跳转至主界面
                                        * */
                                        SharedPreferences.Editor editor= sharedPreferences.edit();
                                        editor.clear();
                                        editor.apply();
                                        //挑战到主页面，然后清空之前activity栈中所有activity
                                        Intent intent = new Intent(userSecurityCancelActivity.this,
                                                com.example.life_assistant.index.indexActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }else {
                            userSecurityCancel_password.post(new Runnable() {
                                @Override
                                public void run() {
                                    userSecurityCancel_password.setText("");
                                    Toast.makeText(userSecurityCancelActivity.this,
                                            "输入的密码不正确，请重新输入密码",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }
            });
        }else {
            userSecurityCancel_password.setText("");
            Toast.makeText(this,"输入的密码不符合正则表达式，请重新输入密码",Toast.LENGTH_SHORT).show();
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