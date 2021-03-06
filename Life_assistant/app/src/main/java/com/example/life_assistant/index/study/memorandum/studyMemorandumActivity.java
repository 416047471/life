package com.example.life_assistant.index.study.memorandum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.matter.studyMatterActivity;
import com.example.life_assistant.login.User;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class studyMemorandumActivity extends AppCompatActivity {

    private RecyclerView study_memorandum_recyclerView;
    private ImageView study_memorandum_addImageView;

    private SharedPreferences sharedPreferences;
    private String objectId;

    private Dialog dialog_add,dialog_update,dialog_delete;

    private TextView study_memorandum_updateObjectId,study_memorandum_updateTitle;
    private EditText study_matter_addNewMemorandumTitle,study_matter_addNewMemorandumContent,
                     study_memorandum_updateContent;
    private Button study_matter_addNewMemorandumrButton,study_memorandumr_updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_study_memorandum);
        init();


    }

    private void init() {
        study_memorandum_recyclerView = findViewById(R.id.study_memorandum_recyclerView);
        study_memorandum_addImageView = findViewById(R.id.study_memorandum_addImageView);

        //?????????????????????????????????
        View view_add = LayoutInflater.from(this).inflate(R.layout.activity_study_memorandum_add,null);
        //?????????????????????AlertDialog?????????
        final AlertDialog.Builder alterDialog_add = new AlertDialog.Builder(this)
                .setView(view_add);
        dialog_add = alterDialog_add.create();
        //?????????????????????????????????
        View view_update = LayoutInflater.from(this).inflate(R.layout.activity_study_memorandum_update,null);
        final  AlertDialog.Builder alterDialog_update = new AlertDialog.Builder(this)
                .setView(view_update);
        dialog_update = alterDialog_update.create();

        //?????????????????????????????????????????????
        study_matter_addNewMemorandumTitle = view_add.findViewById(R.id.study_matter_addNewMemorandumTitle);
        study_matter_addNewMemorandumContent = view_add.findViewById(R.id.study_matter_addNewMemorandumContent);
        study_matter_addNewMemorandumrButton = view_add.findViewById(R.id.study_matter_addNewMemorandumrButton);
        //??????????????????????????????????????????
        study_memorandum_updateObjectId = view_update.findViewById(R.id.study_memorandum_updateObjectId);
        study_memorandum_updateTitle = view_update.findViewById(R.id.study_memorandum_updateTitle);
        study_memorandum_updateContent = view_update.findViewById(R.id.study_memorandum_updateContent);
        study_memorandumr_updateButton = view_update.findViewById(R.id.study_memorandumr_updateButton);


        //?????????????????????Token
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);
        objectId = sharedPreferences.getString("objectId",null);
        //??????RecyclerView?????????
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        study_memorandum_recyclerView.setLayoutManager(layoutManager);
        //??????RecyclerView????????????
        setRecyclerViewAdapter();

        //???????????????????????????????????????
        study_memorandum_addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????????????????????????????????????????
                dialog_add.show();
            }
        });
        //????????????????????????????????????????????????????????????
        study_matter_addNewMemorandumrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMemorandum();
            }
        });

        //????????????????????????????????????????????????????????????
        study_memorandumr_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewMemorandum();
            }
        });
    }

    //?????????????????????Memorandum?????????
    private void setRecyclerViewAdapter() {
        /*
         * bmob?????????????????????????????????????????????????????????????????????????????????????????????
         * ?????????????????????????????????????????????????????????????????????????????????????????????recycleView????????????
         * */
        if (sharedPreferences != null){
            User user = new User();
            user.setObjectId(objectId);
            BmobQuery<Memorandum> query = new BmobQuery<>();
            query.addWhereEqualTo("user",user);
            query.order("-updateAt");
            query.findObjects(new FindListener<Memorandum>() {
                @Override
                public void done(List<Memorandum> list, BmobException e) {
                    if (list != null){
                        studyMemorandumRecyclerViewAdapter studyMemorandumRecyclerViewAdapter =
                                new studyMemorandumRecyclerViewAdapter(studyMemorandumActivity.this,list);
                        study_memorandum_recyclerView.setAdapter(studyMemorandumRecyclerViewAdapter);

                        //??????RecyclerView?????????????????????
                        studyMemorandumRecyclerViewAdapter.setRecyclerItemClickListener(new studyMemorandumRecyclerViewAdapter.OnRecyclerItemClickListener() {
                            @Override
                            public void OnRecyclerItemClick(int position) {
                                View view = study_memorandum_recyclerView.getChildAt(position);
                                TextView study_memorandum_objectId = view.findViewById(R.id.study_memorandum_objectId);
                                TextView study_memorandum_title = view.findViewById(R.id.study_memorandum_title);
                                TextView study_memorandum_content = view.findViewById(R.id.study_memorandum_content);

                                study_memorandum_updateObjectId.setText(study_memorandum_objectId.getText().toString());
                                study_memorandum_updateTitle.setText(study_memorandum_title.getText().toString());
                                study_memorandum_updateContent.setText(study_memorandum_content.getText().toString());
                                dialog_update.show();
                            }
                        });
                        //??????RecyclerView????????????????????????
                        studyMemorandumRecyclerViewAdapter.setRecyclerItemLongclickListener(new studyMemorandumRecyclerViewAdapter.OnRecyclerItemLongClickListener() {
                            @Override
                            public void OnRecyclerItemLongClickListener(int position) {
                                AlertDialog.Builder alterDialog_delete = setDialog_update(position);
                                dialog_delete = alterDialog_delete.create();
                                dialog_delete.show();
                            }
                        });
                    }
                    else
                        Toast.makeText(studyMemorandumActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //???????????????????????????
    private void addNewMemorandum() {
        if (sharedPreferences != null){
            String study_matter_addNewMemorandumTitleNow = study_matter_addNewMemorandumTitle.getText().toString();
            String study_matter_addNewMemorandumContentNow = study_matter_addNewMemorandumContent.getText().toString();
            User user = new User();
            user.setObjectId(objectId);

            Memorandum memorandum = new Memorandum();
            memorandum.setMemorandumTitle(study_matter_addNewMemorandumTitleNow);
            memorandum.setMemorandumContent(study_matter_addNewMemorandumContentNow);
            memorandum.setUser(user);
            memorandum.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (s != null){
                        //????????????,??????????????????????????????
                        study_matter_addNewMemorandumTitle.setText("");
                        study_matter_addNewMemorandumContent.setText("");
                        dialog_add.cancel();
                        //??????recycleView
                        setRecyclerViewAdapter();
                        study_memorandum_recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });

        }
    }

    //???????????????????????????
    private void updateNewMemorandum() {
        String study_memorandum_updateContentNow = study_memorandum_updateContent.getText().toString();
        String study_memorandum_updateObjectIdNow = study_memorandum_updateObjectId.getText().toString();
        Memorandum memorandum = new Memorandum();
        memorandum.setMemorandumContent(study_memorandum_updateContentNow);
        memorandum.update(study_memorandum_updateObjectIdNow, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //?????????????????????????????????????????????????????????
                    dialog_update.cancel();
                    study_memorandum_updateContent.setText("");
                    //??????RecyclerView
                    setRecyclerViewAdapter();
                    study_memorandum_recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    //?????????????????????
    private void deleteMemorandum(final int position){
        //???????????????position??????recycleView????????????
        View view = study_memorandum_recyclerView.getChildAt(position);
        //???????????????????????????ObjectId???????????????????????????text
        TextView textView = view.findViewById(R.id.study_memorandum_objectId);
        String study_memorandum_deleteObjectIdNow = textView.getText().toString();
        //???????????????objectId??????????????????
        Memorandum memorandum = new Memorandum();
        memorandum.setObjectId(study_memorandum_deleteObjectIdNow);
        memorandum.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //??????RecyclerView
                    setRecyclerViewAdapter();
                }
            }
        });
    }
    //???????????????????????????
    private AlertDialog.Builder setDialog_update(final int position) {
        AlertDialog.Builder alertDialog_delete= new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("???????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMemorandum(position);
                    }
                })
                .setNegativeButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }) ;
        return alertDialog_delete;
    }

    //???oncreate?????????????????????
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