package com.example.life_assistant.index.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.life_assistant.R;
import com.example.life_assistant.login.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class userMyWalletActivity extends AppCompatActivity {

    private Toolbar myWallet_toolbar;
    private TextView myWallet_userBanalce;

    private SharedPreferences sharedPreferences;//数据持久化

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_user_my_wallet);

        init();
    }

    //初始化
    private void init() {
        myWallet_toolbar = findViewById(R.id.myWallet_toolbar);
        myWallet_userBanalce = findViewById(R.id.myWallet_userBanalce);

        //取到之前存储的Token，可以通过get方法取传入的token的key的值
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);

        inputDate();//插入个人余额数据

        //toolbar事件监听
        myWallet_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //插入个人余额数据方法
    private void inputDate() {
        //获取当前用户的ObjectId
        String objectId = sharedPreferences.getString("objectId",null);
        //使用Bmob云端查询
        BmobQuery<User> query = new BmobQuery<>();
        query.getObject(objectId, new QueryListener<User>() {
            @Override
            public void done(final User user, BmobException e) {
                if (e == null){
                    /*
                     * 将查询到的User对象数据中的余额放入控件中
                     * 因为在异步线程中查询的所以不能直接改变UI
                     * 使用控件的POST方法，里面的Run方法会在主线程运行
                     * */
                    myWallet_userBanalce.post(new Runnable() {
                        @Override
                        public void run() {
                            myWallet_userBanalce.setText(user.getUserBalance()+".00");
                        }
                    });
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