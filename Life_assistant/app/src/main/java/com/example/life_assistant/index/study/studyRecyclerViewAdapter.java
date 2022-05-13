package com.example.life_assistant.index.study;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.matter.studyMatterRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Map;

public class studyRecyclerViewAdapter extends RecyclerView.Adapter<studyRecyclerViewAdapter.studyRecyclerViewHolder> {
    private Context context;
    private ArrayList<Map<String,Object>> arrayList;

    public studyRecyclerViewAdapter(Context context, ArrayList<Map<String, Object>> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    /*
    * 创建ViewHolder
    * */
    @NonNull
    @Override
    public studyRecyclerViewAdapter.studyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //渲染RecycleView的布局
//        View view = View.inflate(context,R.layout.fragment_study_recyclerview,null);
        //解决宽度无法占满的问题
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_study_recyclerview,parent,false);
        return new studyRecyclerViewHolder(view);
    }

    /*
    * 绑定数据
    * */
    @Override
    public void onBindViewHolder(@NonNull studyRecyclerViewAdapter.studyRecyclerViewHolder holder, int position) {
        //绑定渲染的布局的控件数据
        holder.study_imageView.setBackgroundResource((Integer) arrayList.get(position).get("study_imageView"));
        holder.study_textViewTitle.setText(arrayList.get(position).get("study_textViewTitle")+"");
        holder.study_textViewContent.setText(arrayList.get(position).get("study_textViewContent")+"");
    }

    /*
    * 返回条目数
    * */
    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    //创建一个ViewHolder对象，用于封装三个控件
    public class studyRecyclerViewHolder extends RecyclerView.ViewHolder{
        private ImageView study_imageView;
        private TextView study_textViewTitle,study_textViewContent;
        public studyRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            study_imageView = itemView.findViewById(R.id.study_imageView);
            study_textViewTitle = itemView.findViewById(R.id.study_textViewTitle);
            study_textViewContent = itemView.findViewById(R.id.study_textViewContent);

            //设置监听方法
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClickListener != null){
                        onRecyclerItemClickListener.OnRecyclerItemClick(getAdapterPosition());
                    }
                }
            });

        }
    }



    //创建一个接口对象
    private studyMatterRecyclerViewAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener;

    //设置点击事件的监听方法
    public void setRecyclerItemClickListener(studyMatterRecyclerViewAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }

    //创建一个item的点击事件接口
    public interface OnRecyclerItemClickListener{
        void OnRecyclerItemClickListener(int position);
    }
}
