package com.example.life_assistant.index.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.login.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class userMyUserActivity extends AppCompatActivity {

    private Toolbar myUser_toolbar;
    private EditText myUser_userName,myUser_userAge,myUser_userGender,myUser_email;
    private Button myUser_modifyButton;

    private boolean isModify = true;//按钮文字是否为修改状态
    private SharedPreferences sharedPreferences;//数据持久化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_user_my_user);

        init();

    }

    //初始化
    public void init() {
        myUser_toolbar = findViewById(R.id.myUser_toolbar);
        myUser_userName = findViewById(R.id.myUser_userName);
        myUser_userAge = findViewById(R.id.myUser_userAge);
        myUser_userGender = findViewById(R.id.myUser_userGender);
        myUser_email = findViewById(R.id.myUser_email);
        myUser_modifyButton = findViewById(R.id.myUser_modifyButton);


        //取到之前存储的Token，可以通过get方法取传入的token的key的值
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);

        inputData();//插入个人资料数据
        //设置几个EditText控件为不可编辑状态
        setEditTextEnable(false);

        //修改按钮点击事件
        myUser_modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据按钮状态进行操作
                if (isModify){  //为true即修改时
                    //设置几个EditText控件为可编辑状态
                    setEditTextEnable(true);
                    //更改修改按钮文字为保存,并更改状态
                    myUser_modifyButton.setText("保存");
                    isModify = false;
                }else { //为false即保存时
                    updateUser();//修改个人资料
                }
            }
        });

        //设置Toolbar按钮监听
        myUser_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后销毁当前Activity
                finish();
            }
        });
    }

    //插入个人资料数据
    public void inputData(){
        //获取用户objectId
        String objectyId = sharedPreferences.getString("objectId",null);
        //查询Bmob云端数据库
        BmobQuery<User> query = new BmobQuery<User>();
        query.getObject(objectyId, new QueryListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if (e == null){
                    /*
                    * 将查询到的User对象数据放入四个EditText控件中
                    * 因为在异步线程中查询的所以不能直接改变UI
                    * 使用控件的POST方法，里面的Run方法会在主线程运行
                    * */
                    myUser_userName.post(new Runnable() {
                        @Override
                        public void run() {
                            myUser_userName.setText(user.getUserName());

                        }
                    });
                    myUser_userAge.post(new Runnable() {
                        @Override
                        public void run() {
                            myUser_userAge.setText(user.getUserAge()+"");
                        }
                    });
                    myUser_userGender.post(new Runnable() {
                        @Override
                        public void run() {
                            myUser_userGender.setText(user.getUserGender());

                        }
                    });
                    myUser_email.post(new Runnable() {
                        @Override
                        public void run() {
                            myUser_email.setText(user.getEmail());

                        }
                    });
                }
            }
        });
    }

    //四个EditText控件设置可否编辑状态
    public void setEditTextEnable(boolean state){
        myUser_userName.setEnabled(state);
        myUser_userAge.setEnabled(state);
        myUser_userGender.setEnabled(state);
        myUser_email.setEnabled(state);
    }

    //修改数据操作
    public void updateUser(){
        //获取四个控件文字信息
        String userName = myUser_userName.getText().toString();
        Integer userAge = Integer.parseInt(myUser_userAge.getText().toString());
        String userGender = myUser_userGender.getText().toString();
        String userEmail = myUser_email.getText().toString();
        //获取传入的token的objectId值
        String objectId = sharedPreferences.getString("objectId",null);
        User user = new User();
        user.setUserName(userName);
        user.setUserAge(userAge);
        user.setUserGender(userGender);
        user.setEmail(userEmail);
        user.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //修改成功后将控件设置为不可编辑状态
                    setEditTextEnable(false);
                    //更改修改按钮文字为修改，并更改状态
                    myUser_modifyButton.setText("修改");
                    isModify = true;
                }
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