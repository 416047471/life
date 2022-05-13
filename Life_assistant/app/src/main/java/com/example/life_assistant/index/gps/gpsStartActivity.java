package com.example.life_assistant.index.gps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.track.TraceOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.life_assistant.R;

import java.util.ArrayList;
import java.util.List;

public class gpsStartActivity extends AppCompatActivity {

    private TextView gps_start_minute,gps_start_seconde,
            gps_start_pace,gps_start_distance,gps_start_heat;
    private Button gps_start_end,gps_start_stopOrContinue;
    private ImageView gps_start_mapButton,gps_start_backMapView;

    private boolean isStoop = false;
    private int minute=0,second=0;
    private int time=0;//设立的时间目标数据
    private int distance=0;//设立的路程目标数据
    private Handler handler;
    private Runnable runnable;

    private MapView gps_start_mapView;
    private BaiduMap baiduMap;

    //定位相关
    private LocationClient mLocationClient = null;
    private MyLocationListener myLocationListener = new MyLocationListener();

    private boolean isFirstLocation = true;//判断当前位置是否处于地图中心

    private double nowLatitude,nowLongitude;//当前定位的坐标经纬度
    private double totalDistance = 0;//总的距离
    private LatLng lastLatLng;//定义一个存储上一个定位点的经纬度类
    private List<LatLng> latLngs = new ArrayList<>();


    /*@Override
    protected void onStart() {
        super.onStart();
        //开启定位
        baiduMap.setMyLocationEnabled(true);
        if(!mLocationClient.isStarted()){//如果定位client没有开启，开启定位
            mLocationClient.start();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps_start);
        init();//初始化
        setPermission();//设置权限然后进行定位初始化
//        initDynamicTrajectory();//初始化动态轨迹
    }

    private void initDynamicTrajectory() {
        TraceOptions options = new TraceOptions();
        options.animationTime(3000);
        options.animate(true);
        options.animationType(TraceOptions.TraceAnimateType.TraceOverlayAnimationEasingCurveLinear);
        options.color(0xAAFF0000);
        options.width(10);
        options.points(latLngs);

    }


    private void init() {//初始化操作
        gps_start_minute = findViewById(R.id.gps_start_minute);
        gps_start_seconde = findViewById(R.id.gps_start_seconde);
        gps_start_pace = findViewById(R.id.gps_start_pace);
        gps_start_distance = findViewById(R.id.gps_start_distance);
        gps_start_heat = findViewById(R.id.gps_start_heat);
        gps_start_end = findViewById(R.id.gps_start_end);
        gps_start_stopOrContinue = findViewById(R.id.gps_start_stopOrContinue);
        gps_start_mapButton = findViewById(R.id.gps_start_mapButton);
        gps_start_backMapView = findViewById(R.id.gps_start_backMapView);

        //获取Intent传来的数据
        Intent intent = getIntent();
        if (intent.getStringExtra("gps_target_time")!=null){
            //将传来的时间数据进行解析
            int index = intent.getStringExtra("gps_target_time").indexOf(":");
            time = Integer.parseInt(intent.getStringExtra("gps_target_time").substring(0,index));
        }
        if (intent.getStringExtra("gps_target_distance")!=null){
            //将传来的路程数据进行解析
            int index = intent.getStringExtra("gps_target_distance").indexOf("(");
            distance = Integer.parseInt(intent.getStringExtra("gps_target_distance").substring(0,index));
        }
        setTime();//开启计时器
        //继续和暂停的监听器
        gps_start_stopOrContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoop){
                    /*
                    * 现在是暂停状态，所以点击后开始运行handler的线程
                    * 然后将当前为继续的按钮设为暂停
                    * 将isStoop设为false（即不是暂停）
                    * */
                    handler.postDelayed(runnable,1000);
                    gps_start_stopOrContinue.setText("暂停");
                    isStoop = false;
                }
                else {
                    /*
                     * 现在是运行状态，所以点击后开始关闭handler的线程
                     * 然后将当前为暂停的按钮设为继续
                     * 将isStoop设为true（即不是继续）
                     * */
                    handler.removeCallbacks(runnable);
                    gps_start_stopOrContinue.setText("继续");
                    isStoop = true;
                }
            }
        });
        //结束按钮监听器
        gps_start_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                * 点击后结束当前进程
                * 传递当期数据并进入结束界面
                * */
                Intent intent1 = new Intent(gpsStartActivity.this,gpsEndActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("totalDistanceKm",gps_start_distance.getText().toString()+"");
                bundle.putString("pace",gps_start_pace.getText().toString()+"");
                bundle.putString("time",gps_start_minute.getText().toString()+":"+gps_start_seconde.getText().toString());
                bundle.putString("heat",gps_start_heat.getText().toString());
                intent1.putExtras(bundle);
                startActivity(intent1);
            }
        });
        //打开地图按钮的监听器
        gps_start_mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置可见
                gps_start_mapView.setVisibility(View.VISIBLE);
                gps_start_backMapView.setVisibility(View.VISIBLE);
            }
        });
        //退出地图显示按钮的监听器
        gps_start_backMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置不可见
                gps_start_mapView.setVisibility(View.INVISIBLE);
                gps_start_backMapView.setVisibility(View.INVISIBLE);
            }
        });


        gps_start_mapView = findViewById(R.id.gps_start_mapView);

        //获得百度地图的控制器
        baiduMap = gps_start_mapView.getMap();
        //设置地图的缩放等级
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(19.0f);//设为19级
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        //开启交通图
        baiduMap.setTrafficEnabled(true);
        baiduMap.setMyLocationEnabled(true);/*开启设备位置显示功能*/

    }

    //计时器开始
    private void setTime() {
        handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    second++;
                    if (second > 59){
                        minute++;
                        second = 0;
                        gps_start_minute.setText(minute > 9 ? minute+"" : "0"+minute);
                    }
                    gps_start_seconde.setText(second > 9 ? second+"" : "0"+second);
                    double totalDistanceKm = totalDistance/1000;//单位为千米
                    gps_start_distance.setText(String.format("%.2f",totalDistanceKm)+"");
                    double pace = ((double)minute+(double)second/60)/(totalDistanceKm);//单位为分钟/千米
                    gps_start_pace.setText(String.format("%.2f",pace)+"");
                    if (time != 0){
                        if (second == 0 && second == time){
                            //传输数据并进入结算画面
                            Intent intent = new Intent(gpsStartActivity.this,gpsEndActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("totalDistanceKm",totalDistanceKm+"");
                            bundle.putString("pace",pace+"");
                            bundle.putString("time",minute+":"+second);
                            bundle.putString("heat",gps_start_heat.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                    if (distance != 0){
                        if (totalDistanceKm == distance){
                            //传输数据并进入结算画面
                            Intent intent = new Intent(gpsStartActivity.this,gpsEndActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("totalDistanceKm",totalDistanceKm+"");
                            bundle.putString("pace",pace+"");
                            bundle.putString("time",minute+":"+second);
                            bundle.putString("heat",gps_start_heat.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                    //启动线程，一秒执行一次
                    handler.postDelayed(this,1000);
                }
            };
            runnable.run();
    }

    //权限设置并进行等位初始化
    private void setPermission() {
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CHANGE_WIFI_STATE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permission = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permission,1);
        }else {
            initLocation();
        }
    }

    //初始化定位
    private void initLocation(){
        //声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        //注册监听函数
        mLocationClient.registerLocationListener(myLocationListener);

        LocationClientOption option = new LocationClientOption();
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标
        option.setCoorType("bd09ll");
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(3000);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.setOpenGps(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false
        option.setLocationNotify(true);
        //可选，设置是否需要最新版本的地址信息。默认需要，即参数为true
        option.setNeedNewVersionRgc(true);
        //mLocationClient为第二步初始化过的LocationClient对象
        //需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.setLocOption(option);
        //mLocationClient为第二步初始化过的LocationClient对象
        //调用LocationClient的start()方法，便可发起定位请求
        mLocationClient.start();
    }

    //实现BDAbstractLocationListener接口
    public class MyLocationListener extends BDAbstractLocationListener{

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            nowLatitude= bdLocation.getLatitude();    //获取当前纬度信息
            nowLongitude= bdLocation.getLongitude();    //获取当前经度信息
            /*float radius = bdLocation.getRadius();    //获取定位精度，默认值为0.0f*/
            LatLng latLng = new LatLng(nowLatitude,nowLongitude);//当前坐标信息
            latLngs.add(latLng);//将当前坐标加入到存储的坐标组中，方便后面数据使用

            /*计算总距离*/
            if (lastLatLng == null){
                lastLatLng = latLng;
            }else {
                //使用diatanceUtil工具计算当前的坐标离上一个坐标的距离
                DistanceUtil distanceUtil = new DistanceUtil();
                double distance = distanceUtil.getDistance(latLng, lastLatLng);
                //将计算的得来的数据加到总距离上
                totalDistance += distance;
                //将当前定位的坐标赋值给上一个定位的坐标
                lastLatLng = latLng;
            }




            /*MyLocationData.Builder myLocationData = new MyLocationData.Builder()
                    .accuracy(bdLocation.getRadius())
                    .direction(bdLocation.getDirection())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude());
            baiduMap.setMyLocationData(myLocationData.build());*/

            if (isFirstLocation){
                /*LatLng latLng = new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());*/
                MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(latLng);
                baiduMap.animateMapStatus(mapStatusUpdate);
//              mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);/*设置缩放级别*/
//              baiduMap.animateMapStatus(mapStatusUpdate);
                isFirstLocation = false;


                /*将 “我” 显示在地图上*/
                MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
                locationBuilder.latitude(bdLocation.getLatitude());
                locationBuilder.longitude(bdLocation.getLongitude());
                MyLocationData locationData = locationBuilder.build();
                baiduMap.setMyLocationData(locationData);
            }
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        gps_start_mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        gps_start_mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        gps_start_mapView.onDestroy();
        gps_start_mapView = null;
        super.onDestroy();

    }
}