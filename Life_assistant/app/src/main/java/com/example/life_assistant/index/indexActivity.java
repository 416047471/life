package com.example.life_assistant.index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.life_assistant.R;
import com.example.life_assistant.index.gps.gpsFragment;
import com.example.life_assistant.index.home.homeFragment;
import com.example.life_assistant.index.study.studyFragment;
import com.example.life_assistant.index.user.userSecurityCancelActivity;
import com.example.life_assistant.login.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

public class indexActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    private ViewPager2 viewPager2;
    private ImageButton button1;
    //获取NestedScrollView控件
    private NestedScrollView nestedScrollView;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private SharedPreferences sharedPreferences,sharedPreferencesStyle;

    @Override
    protected void onRestart() {
        super.onRestart();
        if (sharedPreferencesStyle.getBoolean("isChangeTheme",false)){
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferencesStyle = getSharedPreferences("styleToken",MODE_PRIVATE);
        //判断是否为第一次进入，因为第一次进入时，sharedPreferences中无theme数据
        if (sharedPreferencesStyle.getString("theme",null) == null){
            //第一次进入将主题色设置为默认白色
            SharedPreferences.Editor editor = sharedPreferencesStyle.edit();
            editor.putString("theme","white");
            editor.apply();
        }
        setStyle(sharedPreferencesStyle.getString("theme",null));

        setContentView(R.layout.activity_index);
        init();

    }
    //初始化
    public void init(){
        Bmob.resetDomain("https://open3.bmob.cn/8/");
        //默认初始化Bomb云端
        Bmob.initialize(this, "f412a2ea058faa888c5de5f80dc7a243");
        //控件实例化
        bottomNavigationView = findViewById(R.id.bottomNav);
        viewPager2 = findViewById(R.id.viewpager2);
        nestedScrollView = findViewById(R.id.nestedScrollview);
        toolbar = findViewById(R.id.Toobar);
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);

        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);
        setNavigationViewTextView();//设置navigationView的头部的文字和菜单文字

        //设置Toolbard的navigationIcon的监听来打开DrawerLayout的NavigationView
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //设置navigationView的Item的icon为本来的图片颜色
        navigationView.setItemIconTintList(null);
        //设置navigationView的菜单的点击事件
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_myUser:   //个人账户
                        //如果持久化数据为空，自动跳转至登录界面
                        if (sharedPreferences.getString("objectId",null) == null){
                            startActivity(new Intent(indexActivity.this,MainActivity.class));
                        }else{//不为空，则跳转个人账户界面
                            startActivity(new Intent(indexActivity.this,
                                    com.example.life_assistant.index.user.userMyUserActivity.class));
                        }
                        break;
                    case R.id.nav_myWallet: //个人钱包
                        //如果持久化数据为空，自动跳转至登录界面
                        if (sharedPreferences.getString("objectId",null) == null){
                            startActivity(new Intent(indexActivity.this,MainActivity.class));
                        }else{//不为空，则跳转个人钱包界面
                            startActivity(new Intent(indexActivity.this,
                                    com.example.life_assistant.index.user.userMyWalletActivity.class));
                        }
                        break;
                    case R.id.nav_style:    //主题风格
                        startActivity(new Intent(indexActivity.this,
                                com.example.life_assistant.index.user.userStyleActivity.class));
                        break;
                    case R.id.nav_securityCenter:   //个人安全中心
                        //如果持久化数据为空，自动跳转至登录界面
                        if (sharedPreferences.getString("objectId",null) == null){
                            startActivity(new Intent(indexActivity.this,MainActivity.class));
                        }else{//不为空，则跳转个人安全中心
                            startActivity(new Intent(indexActivity.this,
                                    com.example.life_assistant.index.user.userSecurityCenterActivity.class));
                        }
                        break;
                    case R.id.nav_logOut:  //退出按钮
                        //如果持久化数据不为空，就清空的token值
                        if (sharedPreferences.getString("objectId",null) != null){
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.clear();
                            editor.apply();
                            //重启界面
                            finish();
                            startActivity(getIntent());
                        }else {
                            startActivity(new Intent(indexActivity.this,MainActivity.class));
                        }
                }
                return false;
            }
        });

        //保留底部导航栏icon的原始图片颜色
        bottomNavigationView.setItemIconTintList(null);

        //创建一个fragment的list数组，将创建好的三个fragment加载进去
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(new studyFragment());
        fragments.add(new homeFragment());
        fragments.add(new gpsFragment());
        //设置预加载的Fragment页面数量
//        viewPager2.setOffscreenPageLimit(fragments.size()-1);
        //给Viewpager2设置适配器
        FragmentStateAdapter adapter = new FragmentStateAdapter(indexActivity.this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {
                return fragments.get(position);
            }

            @Override
            public int getItemCount() {
                return fragments.size();
            }

        };
        viewPager2.setAdapter(adapter);        //把适配器添加给viewpager2

        //设置viewpager2的滑动事件监听
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //将底部栏的图标checked状态设为true
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                //在每次切换item后将视图置顶(通过将NestedScrollView设置为原位置)
                nestedScrollView.scrollTo(0,0);
            }

        });



        //底部导航栏监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_0:
                        viewPager2.setCurrentItem(0);
                        break;
                    case R.id.bottom_1:
                        viewPager2.setCurrentItem(1);
                        break;
                    case R.id.bottom_2:
                        viewPager2.setCurrentItem(2);
                        break;
                }
                return false;
            }
        });

        /*
        * NestedScrollView的滑动监听
        * 重写setOnScrollChangeListener方法，有五个参数:
        * 第一个参数NestedScrollView v:是NestedScrollView的对象
        * 第二个参数:scrollX是目前的（滑动后）的X轴坐标
        * 第三个参数:ScrollY是目前的（滑动后）的Y轴坐标
        * 第四个参数:oldScrollX是之前的（滑动前）的X轴坐标
        * 第五个参数:oldScrollY是之前的（滑动前）的Y轴坐标*
        * */
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int scrollyDistance = scrollY - oldScrollY;//垂直滑动的距离
                //当下滑的时候底部的BottomNaviationView隐藏
                if (scrollyDistance > 5)
                    bottomNavigationView.setVisibility(View.GONE);
                else if (scrollyDistance < 5)
                    bottomNavigationView.setVisibility(View.VISIBLE);
            }

        });


    }
    /*
    * 设置navigationView的头部的布局中的textView为登录的用户名
    * 设置navigationView的菜单布局中对于游客或非游客登录的显示文字
    * */
    private void setNavigationViewTextView(){
        //获取NavigationView的头部布局
        View nav_View = navigationView.getHeaderView(0);
        //获取NavigationView头部布局的TextView
        TextView textView = nav_View.findViewById(R.id.nav_userName);
        //取到之前存储的Token，可以通过get方法取传入的token的key的值

        String userName = sharedPreferences.getString("userName",null);
        //将获取到的userName赋值到textView上
        if (userName != null){
            textView.setText(userName);
            navigationView.getMenu().getItem(4).setTitle("退出登录");
        }else {
            textView.setText("游客");
            navigationView.getMenu().getItem(4).setTitle("登录/注册");
        }
    }


    //px转为dp
    private int px2dp(float pxValue){
        /*
        * 根据手机的分辨率从dip的单位转为px（像素）
        * */
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);

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