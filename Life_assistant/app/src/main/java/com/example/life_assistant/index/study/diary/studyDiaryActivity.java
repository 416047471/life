package com.example.life_assistant.index.study.diary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.memorandum.studyMemorandumActivity;
import com.example.life_assistant.login.User;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class studyDiaryActivity extends AppCompatActivity {

    private RecyclerView study_diary_recyclerView;
    private ImageView study_diary_addImageView;

    private SharedPreferences sharedPreferences;
    private String objectId;

    private Dialog dialog_add,dialog_update,dialog_delete;

    private TextView study_diary_updateObjectId,study_diary_updateTitle;
    private EditText study_diary_addNewDiaryTitle,study_diary_addNewDiaryContent,
                    study_diary_updateContent;
    private Button study_diary_addNewDiaryButton,study_diary_updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_study_diary);

        init();
    }

    private void init() {
        //默认初始化Bomb云端
        Bmob.initialize(this, "f412a2ea058faa888c5de5f80dc7a243");

        study_diary_recyclerView = findViewById(R.id.study_diary_recyclerView);
        study_diary_addImageView = findViewById(R.id.study_diary_addImageView);

        //增加新日记窗口创建
        View view_add = LayoutInflater.from(this).inflate(R.layout.activity_study_diary_add,null);
        //将该视图装载到AlertDialog弹窗中
        final AlertDialog.Builder alterDialog_add = new AlertDialog.Builder(this)
                .setView(view_add);
        dialog_add = alterDialog_add.create();
        //得到修改新事项的窗口
        View view_update = LayoutInflater.from(this).inflate(R.layout.activity_study_diary_update,null);
        final AlertDialog.Builder alterDialog_update = new AlertDialog.Builder(this)
                .setView(view_update);
        dialog_update = alterDialog_update.create();

        //将增加视图中的所有控件实例化
        study_diary_addNewDiaryTitle = view_add.findViewById(R.id.study_diary_addNewDiaryTitle);
        study_diary_addNewDiaryContent = view_add.findViewById(R.id.study_diary_addNewDiaryContent);
        study_diary_addNewDiaryButton = view_add.findViewById(R.id.study_diary_addNewDiaryButton);
        //将修改视图中的所有控件实例化
        study_diary_updateObjectId = view_update.findViewById(R.id.study_diary_updateObjectId);
        study_diary_updateTitle = view_update.findViewById(R.id.study_diary_updateTitle);
        study_diary_updateContent = view_update.findViewById(R.id.study_diary_updateContent);
        study_diary_updateButton = view_update.findViewById(R.id.study_diary_updateButton);

        //得到持久化数据Token
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);
        objectId = sharedPreferences.getString("objectId",null);
        //设置设置RecyclerView的布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        study_diary_recyclerView.setLayoutManager(layoutManager);
        //设置RecyclerView的适配器
        setRecyclerViewAdapter();

        //设置增加按钮的点击事件监听
        study_diary_addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建好的增加新事项窗口视图显示
                dialog_add.show();
            }
        });
        //设置新视图中的增加按钮的点击事件监听
        study_diary_addNewDiaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewDiary();
            }
        });
        //设置改变新视图中的修改按钮的点击事件监听
        study_diary_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewDiary();
            }
        });
    }

    //查询当前用户的Diary表数据
    private void setRecyclerViewAdapter() {
        /*
         * bmob的查询是在异步线程中进行的，所以不能把查询到的数据传到主线程中
         * 所以我改用直接将查到的数据封装到自定义的适配器中，然后直接设置recycleView的适配器
         * */
        if (sharedPreferences != null){
            User user = new User();
            user.setObjectId(objectId);
            BmobQuery<Diary> query = new BmobQuery<>();
            query.addWhereEqualTo("user",user);
            query.order("-updateAt");
            query.findObjects(new FindListener<Diary>() {
                @Override
                public void done(List<Diary> list, BmobException e) {
                    if (list != null){
                        studyDiaryRecyclerViewAdapter studyDiaryRecyclerViewAdapter =
                                new studyDiaryRecyclerViewAdapter(studyDiaryActivity.this,list);
                        study_diary_recyclerView.setAdapter(studyDiaryRecyclerViewAdapter);

                        //设置RecyclerView的点击事件监听
                        studyDiaryRecyclerViewAdapter.setRecyclerItemClickListener(new studyDiaryRecyclerViewAdapter.OnRecyclerItemClickListener() {
                            @Override
                            public void OnRecyclerItemClick(int positon) {
                                View view = study_diary_recyclerView.getChildAt(positon);
                                TextView study_diary_objectId = view.findViewById(R.id.study_diary_objectId);
                                TextView study_diary_title = view.findViewById(R.id.study_diary_title);
                                TextView study_diary_content = view.findViewById(R.id.study_diary_content);

                                study_diary_updateObjectId.setText(study_diary_objectId.getText().toString());
                                study_diary_updateTitle.setText(study_diary_title.getText().toString());
                                study_diary_updateContent.setText(study_diary_content.getText().toString());
                                dialog_update.show();
                            }
                        });
                        //设置RecyclerView的昌电机事件监听
                        studyDiaryRecyclerViewAdapter.setOnRecyclerItemLongClickListener(new studyDiaryRecyclerViewAdapter.OnRecyclerItemLongClickListener() {
                            @Override
                            public void OnRecyclerItemLongClickListener(int positon) {
                                AlertDialog.Builder alterDialog_delete = setDialog_update(positon);
                                dialog_delete = alterDialog_delete.create();
                                dialog_delete.show();
                            }
                        });
                    }
                    else
                        Toast.makeText(studyDiaryActivity.this,"查询出错",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //增加日记按钮操作
    private void addNewDiary(){
        if (sharedPreferences != null){
            final String study_diary_addNewDiaryTitleNow = study_diary_addNewDiaryTitle.getText().toString();
            String study_diary_addNewDiaryContentNow = study_diary_addNewDiaryContent.getText().toString();
            User user = new User();
            user.setObjectId(objectId);

            Diary diary = new Diary();
            diary.setDiaryTitle(study_diary_addNewDiaryTitleNow);
            diary.setDiaryContent(study_diary_addNewDiaryContentNow);
            diary.setUser(user);
            diary.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (s != null){
                        //关闭弹窗，并清空两个输入框数据
                        study_diary_addNewDiaryTitle.setText("");
                        study_diary_addNewDiaryContent.setText("");
                        dialog_add.cancel();
                        //刷新RecyclerView
                        setRecyclerViewAdapter();
                        study_diary_recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }
    private void updateNewDiary(){
        String study_diary_updateContentNow = study_diary_updateContent.getText().toString();
        String study_diary_updateObjectIdNow = study_diary_updateObjectId.getText().toString();
        Diary diary = new Diary();
        diary.setDiaryContent(study_diary_updateContentNow);
        diary.update(study_diary_updateObjectIdNow, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //修改成功并关闭修改窗口且修改页面内容清空
                    dialog_update.cancel();
                    study_diary_updateContent.setText("");
                    //刷新RecyclerView
                    setRecyclerViewAdapter();
                    study_diary_recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }
    private void deleteDiary(int positon) {
        //根据传入的positon获取RecyclerView的子布局
        View view = study_diary_recyclerView.getChildAt(positon);
        //获取该子布局的装有ObjectId的控件并获取text
        TextView textView = view.findViewById(R.id.study_diary_objectId);
        String study_diary_deleteObjectIdNow = textView.getText().toString();
        //使用得到的ObjectId进行删除操作
        Diary diary = new Diary();
        diary.setObjectId(study_diary_deleteObjectIdNow);
        diary.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //刷新RecyclerView
                    setRecyclerViewAdapter();
                }
            }
        });
    }

    //定义删除备忘录窗口
    private AlertDialog.Builder setDialog_update(final int positon){
        AlertDialog.Builder alertDialog_delete = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDiary(positon);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return alertDialog_delete;
    }
    //再oncreate前修改主题颜色
    private void setStyle(String theme) {
        switch (theme){
            case "green":
                setTheme(R.style.greenTheme);
                break;
            case "yellow":
                setTheme(R.style.yellowTheme);
                break;
            case "purple":
                setTheme(R.style.purpleTheme);
                break;
            case "blue":
                setTheme(R.style.blueTheme);
                break;
            case "pink":
                setTheme(R.style.pinkTheme);
                break;
            case "black":
                setTheme(R.style.blackTheme);
                break;
            case "white":
                setTheme(R.style.whiteTheme);
                break;
        }
    }

}