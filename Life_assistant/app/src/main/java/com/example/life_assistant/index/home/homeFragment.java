package com.example.life_assistant.index.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.life_assistant.R;
import com.example.life_assistant.calculator.healthyInformation_Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
* 主题Fragment
* */
public class homeFragment extends Fragment {

    private ExpandableListView expandableListView;//加载下拉框的控件
    private homeExpandableListViewAdapter homeExpandableListViewAdapter;//下拉框的适配器



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        init(view);//初始化操作

        return view;
    }

    //初始化控件
    private void init(View view) {
        //获取expandableListView的控件
        expandableListView = view.findViewById(R.id.expandable_ListView);
        expandableListView.setGroupIndicator(null);
        // 去掉默认箭头
        expandableListView.setGroupIndicator(null);

        //实例化自定义的ExpandableListView的适配器
        homeExpandableListViewAdapter = new homeExpandableListViewAdapter(view.getContext(),getGroupListData(),getItemListData());
        //将expandableListView设置好已经实例化的适配器
        expandableListView.setAdapter(homeExpandableListViewAdapter);

        //设置expandableListView的总高度
        setExpandableListViewHeight();

        /*
        * ExpandableListView监听组监听
        * 当组被点击时
        */
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        /*
        * ExpandableListView监听成员（Item）
        * 当成员（item）被点击时
        * */
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (groupPosition){
                    case 0:
                        switch (childPosition){
                            case 0:
                                startActivity(new Intent(getActivity(),com.example.life_assistant.calculator.heaxadecimalChange_Activity.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(),com.example.life_assistant.calculator.unitConversion_Activity.class));
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), healthyInformation_Activity.class));
                                break;
                        }
                    break;
                    case 1:
                        switch (childPosition){
                            case 0:
                                startActivity(new Intent(getActivity(),com.example.life_assistant.calculator.homeOfPhone_Activity.class));
                                break;
                            case 1:
                                startActivity(new Intent(getActivity(),com.example.life_assistant.calculator.homeOfIp_Activity.class));
                                break;
                            case 2:
                                startActivity(new Intent(getActivity(), com.example.life_assistant.calculator.fontChange_Activity.class));
                                break;
                            case 3:
                                startActivity(new Intent(getActivity(),com.example.life_assistant.calculator.oilPrice_Activity.class));
                        }
                    break;
                }
                return false;
            }
        });
    }

    //得到组布局的控件数据
    private ArrayList<Map<String,Object>> getGroupListData(){
        //创建组和子的数据
        ArrayList<Map<String,Object>> groupList = new ArrayList<Map<String,Object>>();
        //实例化一个HashMap
        Map<String,Object> map = new HashMap<String,Object>();
        //将一个一个的map对象赋值并添加到groupList中
        map.put("imageView",R.drawable.seleced_calculator);
        map.put("textView","计算应用");
        groupList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.drawable.seleced_home_image);
        map.put("textView","Api");
        groupList.add(map);

        /*map = new HashMap<String, Object>();
        map.put("imageView",R.drawable.seleced_home_image);
        map.put("textView","测试3");
        groupList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.drawable.seleced_home_image);
        map.put("textView","测试4");
        groupList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.drawable.seleced_home_image);
        map.put("textView","测试5");
        groupList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.drawable.seleced_home_image);
        map.put("textView","测试6");
        groupList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.drawable.seleced_home_image);
        map.put("textView","测试7");
        groupList.add(map);*/

        return groupList;
    }

    //得到子布局的控件数据
    private ArrayList<ArrayList<Map<String,Object>>> getItemListData(){
        ArrayList<ArrayList<Map<String,Object>>> itemLists = new ArrayList<ArrayList<Map<String, Object>>>();
        ArrayList<Map<String,Object>> itemList = new ArrayList<Map<String,Object>>();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.heaxadecimal_change);
        map.put("textViewTitle","进制转换");
        map.put("textViewContent","进行二进制、八进制、十进制、十六进制的转换");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.unit_conversion);
        map.put("textViewTitle","单位换算");
        map.put("textViewContent","对各种类型的单位（如质量、速度、时间）等进行换算");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.bmi);
        map.put("textViewTitle","身体健康信息查询");
        map.put("textViewContent","测量人体健康数据以及推荐方案");
        itemList.add(map);

        itemLists.add(itemList);

        itemList = new ArrayList<Map<String, Object>>();

        map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.homeofphone);
        map.put("textViewTitle","手机号码归属地");
        map.put("textViewContent","查询手机号码归属地信息");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.homeofip);
        map.put("textViewTitle","IP地址查询");
        map.put("textViewContent","查询该IP所属的区域");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.fontchange);
        map.put("textViewTitle","简/繁/火星字体转换");
        map.put("textViewContent","实现简体、繁体、火星文之间的转换");
        itemList.add(map);

        map = new HashMap<String, Object>();
        map.put("imageView",R.mipmap.oilprice);
        map.put("textViewTitle","今日国内油价查询");
        map.put("textViewContent","今日汽油价格查询");
        itemList.add(map);

        itemLists.add(itemList);


        return itemLists;
    }

    //计算ExpandableListView的高度
    private void setExpandableListViewHeight(){
        //获取ExpandableListView对应的Adapter
        ExpandableListAdapter adapter =expandableListView.getExpandableListAdapter();
        if (adapter == null)
            return;

        //定义一个总高度参数
        Integer totalHeight = 0;
        //一个循环计算group的高度和每个group的item的总高度
        for (int i = 0 ; i < adapter.getGroupCount() ; i++){
            //获取该适配器的组的View
            View groupView = adapter.getGroupView(i,true,null,expandableListView);
            groupView.measure(0,0);
            totalHeight += groupView.getMeasuredHeight();
            Log.d("test",i+"");
            for (int j = 0 ; j < adapter.getChildrenCount(i) ; j++){
                //获取该适配器的子的view
                View childView = adapter.getChildView(i,j,false,null,expandableListView);
                childView.measure(0,0);
                totalHeight += childView.getMeasuredHeight();
            }
        }
        //使用getLayoutParams（）获取到expandableListView控件的布局
        ViewGroup.LayoutParams params = expandableListView.getLayoutParams();
        //将之前计算到的总高度和当前控件的分割线的高低相加
        totalHeight += expandableListView.getDividerHeight()*(adapter.getGroupCount()-1);
        //设置获取到params的高度设置为totalHeight
        params.height = totalHeight;
        //重新设置expandableListView的属性
        expandableListView.setLayoutParams(params);
    }

}
