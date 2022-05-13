package com.example.life_assistant.index.gps;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.life_assistant.R;

/**
 * 个体Fragment
 */

public class gpsFragment extends Fragment {

    private Button gps_targetButton,gps_startButton;

    private AlertDialog dialog_target;
    private Spinner gps_target_time,gps_target_distance;
    private Button gps_target_timeButton,gps_target_distanceButton;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gps,container,false);

        init(view);

        return view;
    }

    private void init(final View view) {
        gps_startButton = view.findViewById(R.id.gps_startButton);
        gps_targetButton = view.findViewById(R.id.gps_targetButton);

        //得到目标设置窗口
        final View view_target = LayoutInflater.from(view.getContext()).inflate(R.layout.activity_gps_target,null);
        //将该视图装载到AlertDialog弹窗中
        final AlertDialog.Builder alterDialog_target = new AlertDialog.Builder(view.getContext())
                .setView(view_target);
        dialog_target = alterDialog_target.create();
        //将该视图中的所有控件实例化
        gps_target_time = view_target.findViewById(R.id.gps_target_time);
        gps_target_timeButton = view_target.findViewById(R.id.gps_target_timeButton);
        gps_target_distance = view_target.findViewById(R.id.gps_target_distance);
        gps_target_distanceButton = view_target.findViewById(R.id.gps_target_distanceButton);

        //取到之前存储的Token，可以通过get方法取传入的token的key的值
        sharedPreferences = getActivity().getSharedPreferences("Token", Activity.MODE_PRIVATE);


        //目标的点击事件
        gps_targetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("objectId",null) != null){
                    //将创建好的目标窗口视图显示
                    dialog_target.show();
                }else {
                    //未登录则显示提示需要登录
                    Toast.makeText(getContext(),"请先登录账户",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //开始运动的点击事件
        gps_startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getString("objectId",null) != null){
                    //跳转运动的activity中
                    startActivity(new Intent(getActivity(),gpsStartActivity.class));
                }else {
                    //未登录则显示提示需要登录
                    Toast.makeText(getContext(),"请先登录账户",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //按时间开始的点击事件
        gps_target_timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //时间为目标开始
                targetTime(view);
            }
        });

        //按路程开始的点击事件
        gps_target_distanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //路程为目标开始
                targetDistance(view);
            }
        });
    }

    //设置时间为目标的方法
    private void targetTime(View view) {
        //获取当前选择的时间栏
        String time = gps_target_time.getSelectedItem().toString();
        //跳转到新的开始运动的Activity中，并携带参数
        Intent intent = new Intent(view.getContext(),gpsStartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("gps_target_time",time);
        bundle.putString("gps_target_distance",null);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //设置路程为目标的方法
    private void targetDistance(View view){
        //获取当前选择的路程栏
        String distance = gps_target_distance.getSelectedItem().toString();
        //跳转到新的开始运动的Activity中，并携带参数
        Intent intent = new Intent(view.getContext(),gpsStartActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("gps_target_time",null);
        bundle.putString("gps_target_distance",distance);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
