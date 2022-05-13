package com.example.life_assistant.index.study.target;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.life_assistant.R;

import java.util.List;

public class studyTargetRecyclerViewAdapater extends RecyclerView.Adapter<studyTargetRecyclerViewAdapater.studyTargetRecyclerViewHolder> {
    private Context context;
    private List<Target> targets;

    public studyTargetRecyclerViewAdapater(){super();}

    public studyTargetRecyclerViewAdapater(Context context,List<Target> targets){
        this.context = context;
        this.targets = targets;
    }

    @NonNull
    @Override
    public studyTargetRecyclerViewAdapater.studyTargetRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //渲染RecycleView的布局
        View view = View.inflate(context, R.layout.activity_study_target_item,null);
        return new studyTargetRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull studyTargetRecyclerViewAdapater.studyTargetRecyclerViewHolder holder, int position) {
        //绑定渲染的布局控件数据
        holder.study_target_objectId.setText(targets.get(position).getObjectId());
        holder.study_target_title.setText(targets.get(position).getTargetTitle());
        holder.study_target_createdDate.setText(targets.get(position).getCreatedAt());
        holder.study_target_contentOne.setText(targets.get(position).getTargetContentOne());
        int stateOne = targets.get(position).getTargetStateOne();
        String stateOneNow = "";
        switch (stateOne){
            case 0:
                stateOneNow = "未开始";
                break;
            case 1:
                stateOneNow = "进行中";
                break;
            case 2:
                stateOneNow = "完成";
                break;
            case 3:
                stateOneNow = "终止";
                break;
        }
        holder.study_target_stateOne.setText(stateOneNow);
        holder.study_target_contentTwo.setText(targets.get(position).getTargetContentTwo());
        int stateTwo = targets.get(position).getTargetStateTwo();
        String stateTwoNow = "";
        switch (stateTwo){
            case 0:
                stateTwoNow = "未开始";
                break;
            case 1:
                stateTwoNow = "进行中";
                break;
            case 2:
                stateTwoNow = "完成";
                break;
            case 3:
                stateTwoNow = "终止";
                break;
        }
        holder.study_target_stateTwo.setText(stateTwoNow);
        holder.study_target_contentThree.setText(targets.get(position).getTargetContentThree());
        int stateThree = targets.get(position).getTargetStateThree();
        String stateThreeNow = "";
        switch (stateThree){
            case 0:
                stateThreeNow = "未开始";
                break;
            case 1:
                stateThreeNow = "进行中";
                break;
            case 2:
                stateThreeNow = "完成";
                break;
            case 3:
                stateThreeNow = "终止";
                break;
        }
        holder.study_target_stateThree.setText(stateThreeNow);
    }

    @Override
    public int getItemCount() {
        return targets == null ? 0 : targets.size();
    }

    //创建一个ViewHolder对象，用户封装四个控件
    public class studyTargetRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView study_target_objectId,study_target_title,study_target_createdDate,
                study_target_contentOne,study_target_stateOne,
                study_target_contentTwo,study_target_stateTwo,
                study_target_contentThree,study_target_stateThree;
        public studyTargetRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            study_target_objectId = itemView.findViewById(R.id.study_target_objectId);
            study_target_title = itemView.findViewById(R.id.study_target_title);
            study_target_createdDate = itemView.findViewById(R.id.study_target_createdDate);
            study_target_contentOne = itemView.findViewById(R.id.study_target_contentOne);
            study_target_stateOne = itemView.findViewById(R.id.study_target_stateOne);
            study_target_contentTwo = itemView.findViewById(R.id.study_target_contentTwo);
            study_target_stateTwo = itemView.findViewById(R.id.study_target_stateTwo);
            study_target_contentThree = itemView.findViewById(R.id.study_target_contentThree);
            study_target_stateThree = itemView.findViewById(R.id.study_target_stateThree);
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

    //设置点击事件监听方法
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
