package com.example.life_assistant.index.study.matter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.studyRecyclerViewAdapter;
import com.example.life_assistant.login.User;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class studyMatterActivity extends AppCompatActivity {

    private RecyclerView study_matter_recyclerView;
    private ImageView study_matter_addImageView;

    private SharedPreferences sharedPreferences;
    private String objectId;//获取Token中的objectId

    private AlertDialog dialog_add,dialog_update,dialog_delete;
    private TextView study_matter_updateObjectId,study_matter_updateTitle;
    private EditText study_matter_addNewMatterTitle,study_matter_addNewMatterContent,
            study_matter_updateContent;
    private Button study_matter_addNewMatterButton,study_matter_updateButton;
    private RadioGroup study_matter_updateRadioGroup;
    private RadioButton study_matter_updateState0,study_matter_updateState1,
            study_matter_updateState2,study_matter_updateState3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_study_matter);

        init();//初始化
    }

    private void init() {
        //默认初始化Bomb云端
        Bmob.initialize(this, "f412a2ea058faa888c5de5f80dc7a243");

        study_matter_recyclerView = findViewById(R.id.study_matter_recyclerView);
        study_matter_addImageView = findViewById(R.id.study_matter_addImageView);

        //得到增加新事项的窗口
        View view_add = LayoutInflater.from(this).inflate(R.layout.activity_study_matter_add,null);
        //将该视图装载到AlertDialog弹窗中
        final AlertDialog.Builder alterDialog_add = new AlertDialog.Builder(this)
                .setView(view_add);
        dialog_add = alterDialog_add.create();
        //得到修改新事项的窗口
        View view_update = LayoutInflater.from(this).inflate(R.layout.activity_study_matter_update,null);
        //将该视图装载到AlertDialog弹窗中
        final AlertDialog.Builder alterDialog_update = new AlertDialog.Builder(this)
                .setView(view_update);
        dialog_update = alterDialog_update.create();

        //将该增加视图中的所有空间实例化
        study_matter_addNewMatterTitle = view_add.findViewById(R.id.study_matter_addNewMatterTitle);
        study_matter_addNewMatterContent = view_add.findViewById(R.id.study_matter_addNewMatterContent);
        study_matter_addNewMatterButton = view_add.findViewById(R.id.study_matter_addNewMatterButton);
        //将该修改视图中的所有空间实例化
        study_matter_updateTitle = view_update.findViewById(R.id.study_matter_updateTitle);
        study_matter_updateContent = view_update.findViewById(R.id.study_matter_updateContent);
        study_matter_updateButton = view_update.findViewById(R.id.study_matter_updateButton);
        study_matter_updateObjectId = view_update.findViewById(R.id.study_matter_updateObjectId);
        study_matter_updateRadioGroup = view_update.findViewById(R.id.study_matter_updateRadioGroup);
        study_matter_updateState0 = view_update.findViewById(R.id.study_matter_updateState0);
        study_matter_updateState1 = view_update.findViewById(R.id.study_matter_updateState1);
        study_matter_updateState2 = view_update.findViewById(R.id.study_matter_updateState2);
        study_matter_updateState3 = view_update.findViewById(R.id.study_matter_updateState3);


        //得到持久化数据的Token
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);
        objectId = sharedPreferences.getString("objectId",null);
        //为RecyclerView设置布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        study_matter_recyclerView.setLayoutManager(layoutManager);
        //设置RecyclerView的适配器
        setRecyclerViewAdapter();

        //设置增加按钮的点击事件监听
        study_matter_addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //将创建好的增加新事项窗口视图显示
                dialog_add.show();
            }
        });
        //设置增加新视图中的增加按钮的点击事件监听
        study_matter_addNewMatterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMatter();
            }
        });

        //设置改变新视图中的修改按钮的点击事件监听
        study_matter_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewMatter();
            }
        });


    }



    //查询当前用户的Matter表数据
    private void setRecyclerViewAdapter(){
        /*
        * bmob的查询是在异步线程中进行的，所以不能把查询到的数据传到主线程中
        * 所以我改用直接将查到的数据封装到自定义的适配器中，然后直接设置recycleView的适配器
        * */
        if (sharedPreferences != null){
            User user = new User();
            user.setObjectId(objectId);
            BmobQuery<Matter> query = new BmobQuery<Matter>();
            query.addWhereEqualTo("user",user);
            query.order("-updatedAt");
            query.findObjects(new FindListener<Matter>() {
                @Override
                public void done(final List<Matter> list, BmobException e) {
                    if (list != null){
                        studyMatterRecyclerViewAdapter studyMatterRecyclerViewAdapter =
                                new studyMatterRecyclerViewAdapter(studyMatterActivity.this,list);
                        study_matter_recyclerView.setAdapter(studyMatterRecyclerViewAdapter);

                        //设置RecyclerView的点击事件监听
                        studyMatterRecyclerViewAdapter.setRecyclerItemClickListener(new studyRecyclerViewAdapter.OnRecyclerItemClickListener() {
                            @Override
                            public void OnRecyclerItemClickListener(int position) {
                                View view = study_matter_recyclerView.getChildAt(position);
                                TextView radioButton = view.findViewById(R.id.study_matter_objectId);
                                TextView study_matter_title = view.findViewById(R.id.study_matter_title);
                                TextView study_matter_state = view.findViewById(R.id.study_matter_state);
                                TextView study_matter_content = view.findViewById(R.id.study_matter_content);
                                switch (study_matter_state.getText().toString()){
                                    case "未开始":
                                        study_matter_updateState0.setChecked(true);
                                        break;
                                    case "进行中":
                                        study_matter_updateState1.setChecked(true);
                                        break;
                                    case "完成":
                                        study_matter_updateState2.setChecked(true);
                                        break;
                                    case "终止":
                                        study_matter_updateState3.setChecked(true);
                                        break;
                                }
                                study_matter_updateObjectId.setText(radioButton.getText().toString());
                                study_matter_updateTitle.setText(study_matter_title.getText().toString());
                                study_matter_updateContent.setText(study_matter_content.getText().toString());
                                dialog_update.show();
                            }
                        });
                        //设置RecyclerView的长点击事件监听
                        studyMatterRecyclerViewAdapter.setRecyclerItemLongclickListener(new studyMatterRecyclerViewAdapter.OnRecyclerItemLongClickListener() {
                            @Override
                            public void OnRecyclerItemLongClickListener(int position) {
                                AlertDialog.Builder alterDialog_delete = setDialog_update(position);
                                dialog_delete = alterDialog_delete.create();
                                dialog_delete.show();
                            }
                        });
                    }
                    else
                        Toast.makeText(studyMatterActivity.this,"查询出错",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //增加事项按钮的操作
    private void addNewMatter() {
        if (sharedPreferences != null){
            String study_matter_addNewMatterTitleNow = study_matter_addNewMatterTitle.getText().toString();
            String study_matter_addNewMatterContentNow = study_matter_addNewMatterContent.getText().toString();
            User user = new User();
            user.setObjectId(objectId);

            Matter matter = new Matter();
            matter.setMatterTitle(study_matter_addNewMatterTitleNow);
            matter.setMatterContent(study_matter_addNewMatterContentNow);
            matter.setMatterState(0);
            matter.setUser(user);
            matter.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (s != null){
                        //关闭弹窗,并清空两个输入框数据
                        study_matter_addNewMatterTitle.setText("");
                        study_matter_addNewMatterContent.setText("");
                        dialog_add.cancel();
                        //刷新recycleView
                        setRecyclerViewAdapter();
                        study_matter_recyclerView.getAdapter().notifyDataSetChanged();
                        //study_matter_recyclerView.getChildCount()-1
                    }
                }
            });
        }
    }

    //修改事项按钮操作
    private void updateNewMatter() {
        String study_matter_updateContentNow = study_matter_updateContent.getText().toString();
        String study_matter_updateObjectIdNow = study_matter_updateObjectId.getText().toString();
        Integer matterState = 0;
        switch (study_matter_updateRadioGroup.getCheckedRadioButtonId()){
            case R.id.study_matter_updateState0:
                matterState = 0;
                break;
            case R.id.study_matter_updateState1:
                matterState = 1;
                break;
            case R.id.study_matter_updateState2:
                matterState = 2;
                break;
            case R.id.study_matter_updateState3:
                matterState = 3;
                break;
        }
        Matter matter = new Matter();
        matter.setMatterContent(study_matter_updateContentNow);
        matter.setMatterState(matterState);
        matter.update(study_matter_updateObjectIdNow, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //修改成功关闭修改窗口并且将修改内容清空
                    dialog_update.cancel();
                    study_matter_updateContent.setText("");
                    //刷新recycleView
                    setRecyclerViewAdapter();
//                    study_matter_recyclerView.getAdapter().notifyDataSetChanged();

                }
            }
        });

    }

    //删除事项操作
    private void deleteMatter(final int position) {
        //根据传入的position获取recycleView的子布局
        View view = study_matter_recyclerView.getChildAt(position);
        //获取该子布局的装有ObjectId的控件并得到控件的text
        TextView textView = view.findViewById(R.id.study_matter_objectId);
        String study_matter_deleteObjectIdNow =textView.getText().toString();
        //利用得到的objectId进行删除操作
        Matter matter = new Matter();
        matter.setObjectId(study_matter_deleteObjectIdNow);
        matter.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //刷新RecyclerView
                    setRecyclerViewAdapter();
                }
            }
        });
    }

    //定义删除事项的窗口
    private AlertDialog.Builder setDialog_update(final int position){
         AlertDialog.Builder alertDialog_delete= new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("确定删除？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMatter(position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }) ;
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