package com.example.life_assistant.index.study;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.diary.studyDiaryActivity;
import com.example.life_assistant.index.study.matter.studyMatterActivity;
import com.example.life_assistant.index.study.matter.studyMatterRecyclerViewAdapter;
import com.example.life_assistant.index.study.memorandum.studyMemorandumActivity;
import com.example.life_assistant.index.study.model.studyModelActivity;
import com.example.life_assistant.index.study.target.studyTargetActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
* 学习Fragment
* */

public class studyFragment extends Fragment {

    private RecyclerView study_recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_study,container,false);

        init(view);//初始化

        return view;
    }

    private void init(final View view) {
        study_recyclerView = view.findViewById(R.id.study_recyclerView);

        //为RecyclerView设置布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        study_recyclerView.setLayoutManager(layoutManager);
        //设置RecyclerView的适配器
        studyRecyclerViewAdapter studyRecyclerViewAdapter = new studyRecyclerViewAdapter(view.getContext(),getRecyclerViewData());
        study_recyclerView.setAdapter(studyRecyclerViewAdapter);

        //调用RecyclerView适配器的自定义监听方法
        studyRecyclerViewAdapter.setRecyclerItemClickListener(new studyMatterRecyclerViewAdapter.OnRecyclerItemClickListener() {
            @Override
            public void OnRecyclerItemClick(int position) {
                switch (position){
                    case 0://事项
                        startActivity(new Intent(view.getContext(), studyMatterActivity.class));
                        break;
                    case 1://备忘录
                        startActivity(new Intent(view.getContext(), studyMemorandumActivity.class));
                        break;
                    case 2://目标
                        startActivity(new Intent(view.getContext(), studyTargetActivity.class));
                        break;
                    case 3://学习模式
                        startActivity(new Intent(view.getContext(), studyModelActivity.class));
                        break;
                    case 4://日记
                        startActivity(new Intent(view.getContext(), studyDiaryActivity.class));
                        break;
                    case 5://读书笔记
                        break;

                }
            }
        });
    }

    //得到布局文件的数据
    private ArrayList<Map<String,Object>> getRecyclerViewData(){

         ArrayList<Map<String,Object>> arrayList = new ArrayList<Map<String, Object>>();

         Map<String,Object> map = new HashMap<>();
         map.put("study_imageView",R.drawable.matter);
         map.put("study_textViewTitle","事项");
         map.put("study_textViewContent","记录日程清单，不错过每一件事。");
         arrayList.add(map);

         map = new HashMap<>();
         map.put("study_imageView",R.drawable.memorandum);
         map.put("study_textViewTitle","备忘录");
         map.put("study_textViewContent","记录有关活动或事务,起揭示或提醒作用,以免忘却的一种记事性文书。");
         arrayList.add(map);

         map = new HashMap<>();
         map.put("study_imageView",R.drawable.target);
         map.put("study_textViewTitle","目标");
         map.put("study_textViewContent","将目标拆分为三个等级，分级攻破");
         arrayList.add(map);

         map = new HashMap<>();
         map.put("study_imageView",R.drawable.model);
         map.put("study_textViewTitle","学习模式");
         map.put("study_textViewContent","使用倒计时或正计时的方式进行任务，远离干扰，保持专注。");
         arrayList.add(map);

         map = new HashMap<>();
         map.put("study_imageView",R.drawable.diary);
         map.put("study_textViewTitle","日记");
         map.put("study_textViewContent","记录每一天。");
         arrayList.add(map);

         map = new HashMap<>();
         map.put("study_imageView",R.drawable.notes);
         map.put("study_textViewTitle","读书笔记");
         map.put("study_textViewContent","学习过程中的知识点记录。");
         arrayList.add(map);

        map = new HashMap<>();
        map.put("study_imageView",R.drawable.notes);
        map.put("study_textViewTitle","待开发");
        map.put("study_textViewContent","学习过程中的知识点记录。");
        arrayList.add(map);

        map = new HashMap<>();
        map.put("study_imageView",R.drawable.notes);
        map.put("study_textViewTitle","待开发");
        map.put("study_textViewContent","学习过程中的知识点记录。");
        arrayList.add(map);

        map = new HashMap<>();
        map.put("study_imageView",R.drawable.notes);
        map.put("study_textViewTitle","待开发");
        map.put("study_textViewContent","学习过程中的知识点记录。");
        arrayList.add(map);

        map = new HashMap<>();
        map.put("study_imageView",R.drawable.notes);
        map.put("study_textViewTitle","待开发");
        map.put("study_textViewContent","学习过程中的知识点记录。");
        arrayList.add(map);

        map = new HashMap<>();
        map.put("study_imageView",R.drawable.notes);
        map.put("study_textViewTitle","待开发");
        map.put("study_textViewContent","学习过程中的知识点记录。");
        arrayList.add(map);
         return arrayList;
    }
}
