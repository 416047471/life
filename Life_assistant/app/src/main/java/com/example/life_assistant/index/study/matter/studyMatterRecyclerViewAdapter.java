package com.example.life_assistant.index.study.matter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.studyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class studyMatterRecyclerViewAdapter extends RecyclerView.Adapter<studyMatterRecyclerViewAdapter.studyMatterRecyclerViewHolder> {

    private Context context;
    private List<Matter> matters;

    public studyMatterRecyclerViewAdapter(Context context, List<Matter> matters) {
        this.context = context;
        this.matters = matters;
    }

    @NonNull
    @Override
    public studyMatterRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //渲染RecycleView的布局
        View view = View.inflate(context, R.layout.activity_study_matter_item,null);
        return new studyMatterRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull studyMatterRecyclerViewHolder holder, int position) {
        //绑定渲染的布局的控件数据
        holder.study_matter_title.setText(matters.get(position).getMatterTitle()+"");
        int state = matters.get(position).getMatterState();
        String stateNow = "";
        switch (state){
            case 0:
                stateNow = "未开始";
                break;
            case 1:
                stateNow = "进行中";
                break;
            case 2:
                stateNow = "完成";
                break;
            case 3:
                stateNow = "终止";
                break;
        }
        holder.study_matter_state.setText(stateNow);
        holder.study_matter_createdDate.setText(matters.get(position).getCreatedAt()+"");
        holder.study_matter_content.setText(matters.get(position).getMatterContent()+"");
        holder.study_matter_objectId.setText(matters.get(position).getObjectId());
    }

    @Override
    public int getItemCount() {
        return matters == null ? 0 : matters.size();
    }

    //创建一个ViewHolder对象，用户封装四个控件
    public class studyMatterRecyclerViewHolder extends RecyclerView.ViewHolder {
        private TextView study_matter_title,study_matter_state,
                study_matter_createdDate,study_matter_content,study_matter_objectId;
        public studyMatterRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            study_matter_title = itemView.findViewById(R.id.study_matter_title);
            study_matter_state = itemView.findViewById(R.id.study_matter_state);
            study_matter_createdDate = itemView.findViewById(R.id.study_matter_createdDate);
            study_matter_content = itemView.findViewById(R.id.study_matter_content);
            study_matter_objectId = itemView.findViewById(R.id.study_matter_objectId);

            //设置监听方法
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRecyclerItemClickListener != null){
                        onRecyclerItemClickListener.OnRecyclerItemClickListener(getAdapterPosition());
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
    private studyRecyclerViewAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener;
    private OnRecyclerItemLongClickListener onRecyclerItemLongClickListener;

    //设置点击事件的监听的方法
    public void setRecyclerItemClickListener(studyRecyclerViewAdapter.OnRecyclerItemClickListener onRecyclerItemClickListener){
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
