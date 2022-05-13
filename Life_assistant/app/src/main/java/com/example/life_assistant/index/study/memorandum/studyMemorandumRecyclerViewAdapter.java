package com.example.life_assistant.index.study.memorandum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.life_assistant.R;

import java.util.List;

import cn.bmob.v3.http.I;

public class studyMemorandumRecyclerViewAdapter extends RecyclerView.Adapter<studyMemorandumRecyclerViewAdapter.studyMemorandumRecyclerViewHolder> {
    private Context context;
    private List<Memorandum> memorandums;

    public studyMemorandumRecyclerViewAdapter(){super();}

    public studyMemorandumRecyclerViewAdapter(Context context,List<Memorandum> memorandums) {
        this.context = context;
        this.memorandums = memorandums;
    }


    @NonNull
    @Override
    public studyMemorandumRecyclerViewAdapter.studyMemorandumRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //渲染RecycleView的布局
        View view = View.inflate(context,R.layout.activity_study_memorandum_item,null);
        return new studyMemorandumRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull studyMemorandumRecyclerViewAdapter.studyMemorandumRecyclerViewHolder holder, int position) {
        //绑定渲染的布局控件数据
        holder.study_memorandum_createDate.setText(memorandums.get(position).getCreatedAt()+"");
        holder.study_memorandum_title.setText(memorandums.get(position).getMemorandumTitle()+"");
        holder.study_memorandum_content.setText(memorandums.get(position).getMemorandumContent()+"");
        holder.study_memorandum_objectId.setText(memorandums.get(position).getObjectId()+"");
    }

    @Override
    public int getItemCount() {
        return memorandums == null ? 0 : memorandums.size();
    }

    //创建一个ViewHolder对象，用户封装四个控件
    public class studyMemorandumRecyclerViewHolder extends RecyclerView.ViewHolder{
        private TextView study_memorandum_createDate,study_memorandum_title,
                study_memorandum_content,study_memorandum_objectId;
        public studyMemorandumRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            study_memorandum_createDate = itemView.findViewById(R.id.study_memorandum_createDate);
            study_memorandum_title = itemView.findViewById(R.id.study_memorandum_title);
            study_memorandum_content = itemView.findViewById(R.id.study_memorandum_content);
            study_memorandum_objectId = itemView.findViewById(R.id.study_memorandum_objectId);
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
    public void setRecyclerItemLongclickListener(OnRecyclerItemLongClickListener onRecyclerItemLongClickListener){
        this.onRecyclerItemLongClickListener = onRecyclerItemLongClickListener;
    }

    //创建一个item的点击事件接口
    public interface OnRecyclerItemClickListener{
        void OnRecyclerItemClick(int position);
    }
    //创建一个item的长按点击事件接口
    public interface OnRecyclerItemLongClickListener{
        void OnRecyclerItemLongClickListener(int position);
    }
}
