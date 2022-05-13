package com.example.life_assistant.index.home;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.life_assistant.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
* 下拉框的适配器
* */
public class homeExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;
    //需要组和子的数据
    private ArrayList<Map<String,Object>> groupList;
    private ArrayList<ArrayList<Map<String,Object>>> itemList;

    public homeExpandableListViewAdapter(Context context, ArrayList<Map<String, Object>> groupList, ArrayList<ArrayList<Map<String, Object>>> itemList) {
        this.context = context;
        this.groupList = groupList;
        this.itemList = itemList;
    }

    //获取组的个数
    @Override
    public int getGroupCount() {
        return groupList == null ? 0 : groupList.size();
    }

    //获取指定的组的子元素的个数
    @Override
    public int getChildrenCount(int groupPosition) {
        return itemList == null ? 0 : itemList.get(groupPosition).size();
    }

    //获取指定组中的数据
    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    //获取指定组中的指定子元素数据
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return itemList.get(groupPosition).get(childPosition);
    }

    //获取指定组的ID
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //获取指定组中指定子元素的ID
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     *
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded 该组是展开状态还是伸缩状态
     * @param convertView 重用已有的视图对象
     * @param parent 返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        //判断convertView是否为空，为空再赋予新数据，不为空直接将之前载入的数据赋值给groupHolder
        if (convertView == null){
            //new一个groupHolder并且使用LayoutInflater.inflate()来载入获取group的layout.xml布局文件
            groupHolder = new GroupHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_expendlist_group,null);
            //将获取的convertView中的控件赋给groupHolder
            groupHolder.imageView = (ImageView) convertView.findViewById(R.id.home_group_imageView);
            groupHolder.textView = (TextView) convertView.findViewById(R.id.home_group_textView);
            groupHolder.relativeLayout = (RelativeLayout) convertView.findViewById(R.id.home_group_relative);
            //将holder对象作为标签添加到View上
            convertView.setTag(groupHolder);
        }else {
            //不为空的时候直接重新使用convertView从而减少了很多不必要的View的创建
            groupHolder = (GroupHolder) convertView.getTag();
        }
        //将group组的imageview先赋值
        groupHolder.imageView.setBackgroundResource((Integer) groupList.get(groupPosition).get("imageView"));
        //对控件是否展开状态进行相应处理
        if (isExpanded){
            //当展开的时候背景色和图片设置为被选中状态
            groupHolder.imageView.setSelected(true);
            groupHolder.relativeLayout.setSelected(true);
            //文字颜色改为白色
            groupHolder.textView.setTextColor(Color.WHITE);
        }else {
            //当不展开的时候将背景色和图片设置为未被选中的状态
            groupHolder.imageView.setSelected(false);
            groupHolder.relativeLayout.setSelected(false);
            //文字颜色改灰色
            groupHolder.textView.setTextColor(Color.BLACK);
        }
        //然后再对文字赋值
        groupHolder.textView.setText(groupList.get(groupPosition).get("textView")+"");
        return convertView;
    }

    /**
     *
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild 子元素是否处于组中的最后一个
     * @param convertView 重用已有的视图(View)对象
     * @param parent 返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, android.view.View,
     *      android.view.ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        //判断convertView是否为空，为空再赋予新数据，不为空直接将之前载入的数据赋值给groupHolder
        if (convertView == null){
            //new一个groupHolder并且使用LayoutInflater.inflate()来载入获取group的layout.xml布局文件
            itemHolder = new ItemHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_expendlist_item,null);
            //将获取的convertView中的控件赋给itemHolder
            itemHolder.imageView = (ImageView) convertView.findViewById(R.id.home_item_imageView);
            itemHolder.textView1 = (TextView)convertView.findViewById(R.id.home_item_textViewTitle);
            itemHolder.textView2 = (TextView) convertView.findViewById(R.id.home_item_textViewContent);
            //将holder对象作为标签添加到View上
            convertView.setTag(itemHolder);
        }else {
            //不为空的时候直接重新使用convertView从而减少了很多不必要的View的创建
            itemHolder = (ItemHolder) convertView.getTag();
        }
        //将itemHolder的控件赋值
        itemHolder.imageView.setBackgroundResource((Integer) itemList.get(groupPosition).get(childPosition).get("imageView"));
        itemHolder.textView1.setText(itemList.get(groupPosition).get(childPosition).get("textViewTitle")+"");
        itemHolder.textView2.setText(itemList.get(groupPosition).get(childPosition).get("textViewContent")+"");
        return convertView;
    }

    //是否选中指定位置上的子元素
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //创建装载数据的组布局控件的封装类
    private class GroupHolder{
        private RelativeLayout relativeLayout;
        private ImageView imageView;
        private TextView textView;
    }
    //创建装载数据的子布局控件的封装类
    private class ItemHolder{
        private ImageView imageView;
        private TextView textView1;
        private TextView textView2;
    }
}
