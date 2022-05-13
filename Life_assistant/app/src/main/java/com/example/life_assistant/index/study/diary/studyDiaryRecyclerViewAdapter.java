package com.example.life_assistant.index.study.diary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.life_assistant.R;

import java.util.List;

public class studyDiaryRecyclerViewAdapter extends RecyclerView.Adapter<studyDiaryRecyclerViewAdapter.studyDiaryRecyclerViewHolder> {
    private Context context;
    private List<Diary> diaries;

    public studyDiaryRecyclerViewAdapter(){super();}
    public studyDiaryRecyclerViewAdapter(Context context,List<Diary> diaries){
        this.context = context;
        this.diaries = diaries;
    }
    @NonNull
    @Override
    public studyDiaryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //渲染RecyclerView的布局
        View view = View.inflate(context, R.layout.activity_study_diary_item,null);
        return new studyDiaryRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull studyDiaryRecyclerViewAdapter.studyDiaryRecyclerViewHolder holder, int position) {
        //绑定渲染的布局控件数据
        holder.study_diary_createData.setText(diaries.get(position).getCreatedAt()+"");
        holder.study_diary_title.setText(diaries.get(position).getDiaryTitle()+"");
        holder.study_diary_content.setText(diaries.get(position).getDiaryContent()+"");
        holder.study_diary_objectId.setText(diaries.get(position).getObjectId()+"");
    }

    @Override
    public int getItemCount() {
        return diaries == null ? 0 : diaries.size();
    }


    //创建一个ViewHolder对象，用户封装四个控件
    public class studyDiaryRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView study_diary_createData,study_diary_title,
                study_diary_content,study_diary_objectId;
        public studyDiaryRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            study_diary_createData = itemView.findViewById(R.id.study_diary_createDate);
            study_diary_title = itemView.findViewById(R.id.study_diary_title);
            study_diary_content = itemView.findViewById(R.id.study_diary_content);
            study_diary_objectId = itemView.findViewById(R.id.study_diary_objectId);
            //设置监听方法
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClickListener != null){
                        onRecyclerItemClickListener.OnRecyclerItemClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onRecyclerItemLongClickListener != null){
                        onRecyclerItemLongClickListener.OnRecyclerItemLongClickListener(getAdapterPosition());
                    }
                    return false;
                }
            });
        }
    }
    //创建一个接口对象
    private OnRecyclerItemClickListener onRecyclerItemClickListener;
    private OnRecyclerItemLongClickListener onRecyclerItemLongClickListener;

    //设置点击事件的监听方法
    public void setRecyclerItemClickListener(OnRecyclerItemClickListener onRecyclerItemClickListener){
        this.onRecyclerItemClickListener = onRecyclerItemClickListener;
    }
    public void setOnRecyclerItemLongClickListener(OnRecyclerItemLongClickListener onRecyclerItemLongClickListener){
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    //创建一个item的点击事件接口
    public interface OnRecyclerItemClickListener{
        void OnRecyclerItemClick(int positon);
    }
    //创建一个item的长按点击事件接口
    public interface OnRecyclerItemLongClickListener{
        void OnRecyclerItemLongClickListener(int positon);
    }
}
