package com.example.life_assistant.index.study.target;

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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life_assistant.R;
import com.example.life_assistant.index.study.matter.studyMatterActivity;
import com.example.life_assistant.login.User;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class studyTargetActivity extends AppCompatActivity {

    private RecyclerView study_target_recyclerView;
    private ImageView study_target_addImageView;

    private SharedPreferences sharedPreferences;
    private String objectId;

    private Dialog dialog_add,dialog_update,dialog_delete;
    private TextView study_target_updateObjectId;
    private EditText study_target_addNewTargetTitle,study_target_addNewTargetContentOne,
                    study_target_addNewTargetContentTwo,study_target_addNewTargetContentThree,
                    study_target_updateTitle,study_target_updateContentOne,
                    study_target_updateContentTwo,study_target_updateContentThree;
    private Button study_target_addNewTargetButton,study_target_updateButton;
    private RadioButton study_target_updateStateOne0,study_target_updateStateOne1,
            study_target_updateStateOne2,study_target_updateStateOne3,
            study_target_updateStateTwo0,study_target_updateStateTwo1,
            study_target_updateStateTwo2,study_target_updateStateTwo3,
            study_target_updateStateThree0,study_target_updateStateThree1,
            study_target_updateStateThree2,study_target_updateStateThree3;
    private RadioGroup study_target_updateStateOne,study_target_updateStateTwo,study_target_updateStateThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(getSharedPreferences("styleToken",MODE_PRIVATE).getString("theme",null));
        setContentView(R.layout.activity_study_target);
        init();
    }

    private void init() {
        study_target_recyclerView = findViewById(R.id.study_target_recyclerView);
        study_target_addImageView = findViewById(R.id.study_target_addImageView);

        //?????????????????????????????????
        View view_add = LayoutInflater.from(this).inflate(R.layout.activity_study_target_add,null);
        //?????????????????????AlertDialog?????????
        final AlertDialog.Builder alterDialog_add = new AlertDialog.Builder(this)
                .setView(view_add);
        dialog_add = alterDialog_add.create();
        //?????????????????????????????????
        View view_update = LayoutInflater.from(this).inflate(R.layout.activity_study_target_update,null);
        final  AlertDialog.Builder alterDialog_update = new AlertDialog.Builder(this)
                .setView(view_update);
        dialog_update = alterDialog_update.create();

        //?????????????????????????????????????????????
        study_target_addNewTargetTitle = view_add.findViewById(R.id.study_target_addNewTargetTitle);
        study_target_addNewTargetContentOne = view_add.findViewById(R.id.study_target_addNewTargetContentOne);
        study_target_addNewTargetContentTwo = view_add.findViewById(R.id.study_target_addNewTargetContentTwo);
        study_target_addNewTargetContentThree = view_add.findViewById(R.id.study_target_addNewTargetContentThree);
        study_target_addNewTargetButton = view_add.findViewById(R.id.study_target_addNewTargetButton);
        //??????????????????????????????????????????
        study_target_updateObjectId = view_update.findViewById(R.id.study_target_updateObjectId);
        study_target_updateTitle = view_update.findViewById(R.id.study_target_updateTitle);
        study_target_updateContentOne = view_update.findViewById(R.id.study_target_updateContentOne);
        study_target_updateContentTwo = view_update.findViewById(R.id.study_target_updateContentTwo);
        study_target_updateContentThree = view_update.findViewById(R.id.study_target_updateContentThree);
        study_target_updateStateOne0 = view_update.findViewById(R.id.study_target_updateStateOne0);
        study_target_updateStateOne1 = view_update.findViewById(R.id.study_target_updateStateOne1);
        study_target_updateStateOne2 = view_update.findViewById(R.id.study_target_updateStateOne2);
        study_target_updateStateOne3 = view_update.findViewById(R.id.study_target_updateStateOne3);
        study_target_updateStateTwo0 = view_update.findViewById(R.id.study_target_updateStateTwo0);
        study_target_updateStateTwo1 = view_update.findViewById(R.id.study_target_updateStateTwo1);
        study_target_updateStateTwo2 = view_update.findViewById(R.id.study_target_updateStateTwo2);
        study_target_updateStateTwo3 = view_update.findViewById(R.id.study_target_updateStateTwo3);
        study_target_updateStateThree0 = view_update.findViewById(R.id.study_target_updateStateThree0);
        study_target_updateStateThree1 = view_update.findViewById(R.id.study_target_updateStateThree1);
        study_target_updateStateThree2 = view_update.findViewById(R.id.study_target_updateStateThree2);
        study_target_updateStateThree3 = view_update.findViewById(R.id.study_target_updateStateThree3);
        study_target_updateButton = view_update.findViewById(R.id.study_target_updateButton);
        study_target_updateStateOne = view_update.findViewById(R.id.study_target_updateStateOne);
        study_target_updateStateTwo = view_update.findViewById(R.id.study_target_updateStateTwo);
        study_target_updateStateThree = view_update.findViewById(R.id.study_target_updateStateThree);


        //?????????????????????Token
        sharedPreferences = getSharedPreferences("Token",MODE_PRIVATE);
        objectId = sharedPreferences.getString("objectId",null);
        //??????RecyclerView?????????
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        study_target_recyclerView.setLayoutManager(layoutManager);
        //??????RecyclerView????????????
        setRecyclerViewAdapter();

        //???????????????????????????????????????
        study_target_addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //????????????????????????????????????????????????
                dialog_add.show();
            }
        });

        //????????????????????????????????????????????????????????????
        study_target_addNewTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewMatter();
            }
        });

        //????????????????????????????????????????????????????????????
        study_target_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNewMatter();
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
            BmobQuery<Target> query = new BmobQuery<>();
            query.addWhereEqualTo("user",user);
            query.order("-updateAt");
            query.findObjects(new FindListener<Target>() {
                @Override
                public void done(List<Target> list, BmobException e) {
                    if (list != null){
                        studyTargetRecyclerViewAdapater studyTargetRecyclerViewAdapater =
                                new studyTargetRecyclerViewAdapater(studyTargetActivity.this,list);
                        study_target_recyclerView.setAdapter(studyTargetRecyclerViewAdapater);

                        //??????RecyclerView?????????????????????
                        studyTargetRecyclerViewAdapater.setRecyclerItemClickListener(new studyTargetRecyclerViewAdapater.OnRecyclerItemClickListener() {
                            @Override
                            public void OnRecyclerItemClick(int position) {
                                View view = study_target_recyclerView.getChildAt(position);
                                TextView study_target_objectId = view.findViewById(R.id.study_target_objectId);
                                TextView study_target_title = view.findViewById(R.id.study_target_title);
                                TextView study_target_contentOne =view.findViewById(R.id.study_target_contentOne);
                                TextView study_target_contentTwo =view.findViewById(R.id.study_target_contentTwo);
                                TextView study_target_contentThree =view.findViewById(R.id.study_target_contentThree);
                                TextView study_target_stateOne = view.findViewById(R.id.study_target_stateOne);
                                TextView study_target_stateTwo = view.findViewById(R.id.study_target_stateTwo);
                                TextView study_target_stateThree = view.findViewById(R.id.study_target_stateThree);
                                switch (study_target_stateOne.getText().toString()){
                                    case "?????????":
                                        study_target_updateStateOne0.setChecked(true);
                                        break;
                                    case "?????????":
                                        study_target_updateStateOne1.setChecked(true);
                                        break;
                                    case "??????":
                                        study_target_updateStateOne2.setChecked(true);
                                        break;
                                    case "??????":
                                        study_target_updateStateOne3.setChecked(true);
                                        break;
                                }
                                switch (study_target_stateTwo.getText().toString()){
                                    case "?????????":
                                        study_target_updateStateTwo0.setChecked(true);
                                        break;
                                    case "?????????":
                                        study_target_updateStateTwo1.setChecked(true);
                                        break;
                                    case "??????":
                                        study_target_updateStateTwo2.setChecked(true);
                                        break;
                                    case "??????":
                                        study_target_updateStateTwo3.setChecked(true);
                                        break;
                                }
                                switch (study_target_stateThree.getText().toString()){
                                    case "?????????":
                                        study_target_updateStateThree0.setChecked(true);
                                        break;
                                    case "?????????":
                                        study_target_updateStateThree1.setChecked(true);
                                        break;
                                    case "??????":
                                        study_target_updateStateThree2.setChecked(true);
                                        break;
                                    case "??????":
                                        study_target_updateStateThree3.setChecked(true);
                                        break;
                                }
                                study_target_updateObjectId.setText(study_target_objectId.getText().toString());
                                study_target_updateTitle.setText(study_target_title.getText().toString());
                                study_target_updateContentOne.setText(study_target_contentOne.getText().toString());
                                study_target_updateContentTwo.setText(study_target_contentTwo.getText().toString());
                                study_target_updateContentThree.setText(study_target_contentThree.getText().toString());
                                dialog_update.show();
                            }
                        });
                        //??????RecyclerView?????????????????????
                        studyTargetRecyclerViewAdapater.setRecyclerItemLongclickListener(new studyTargetRecyclerViewAdapater.OnRecyclerItemLongClickListener() {
                            @Override
                            public void OnRecyclerItemLongClickListener(int position) {
                                AlertDialog.Builder alterDialog_delete = setDialog_update(position);
                                dialog_delete = alterDialog_delete.create();
                                dialog_delete.show();
                            }
                        });
                    }
                    else
                        Toast.makeText(studyTargetActivity.this,"????????????",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //????????????????????????
    private void addNewMatter() {
        if (sharedPreferences != null){
            String study_target_addNewTargetTitleNow = study_target_addNewTargetTitle.getText().toString();
            String study_target_addNewTargetContentOneNow = study_target_addNewTargetContentOne.getText().toString();
            String study_target_addNewTargetContentTwoNow = study_target_addNewTargetContentTwo.getText().toString();
            String study_target_addNewTargetContentThreeNow = study_target_addNewTargetContentThree.getText().toString();
            User user = new User();
            user.setObjectId(objectId);

            Target target = new Target();
            target.setTargetTitle(study_target_addNewTargetTitleNow);
            target.setTargetContentOne(study_target_addNewTargetContentOneNow);
            target.setTargetStateOne(0);
            target.setTargetContentTwo(study_target_addNewTargetContentTwoNow);
            target.setTargetStateTwo(0);
            target.setTargetContentThree(study_target_addNewTargetContentThreeNow);
            target.setTargetStateThree(0);
            target.setUser(user);
            target.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (s != null){
                        //????????????,????????????????????????
                        study_target_addNewTargetTitle.setText("");
                        study_target_addNewTargetContentOne.setText("");
                        study_target_addNewTargetContentTwo.setText("");
                        study_target_addNewTargetContentThree.setText("");
                        dialog_add.cancel();
                        //??????recycleView
                        setRecyclerViewAdapter();
                        study_target_recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }
            });
        }
    }

    //????????????????????????
    private void updateNewMatter() {
        String study_target_updateObjectIdNow = study_target_updateObjectId.getText().toString();
        String study_target_updateTitleNow = study_target_updateTitle.getText().toString();
        String study_target_updateContentOneNow = study_target_updateContentOne.getText().toString();
        String study_target_updateContentTwoNow = study_target_updateContentTwo.getText().toString();
        String study_target_updateContentThreeNow = study_target_updateContentThree.getText().toString();
        Integer targetStateOne = 0;
        Integer targetStateTwo = 0;
        Integer targetStateThree = 0;
        switch (study_target_updateStateOne.getCheckedRadioButtonId()){
            case R.id.study_target_updateStateOne0:
                targetStateOne = 0;
                break;
            case R.id.study_target_updateStateOne1:
                targetStateOne = 1;
                break;
            case R.id.study_target_updateStateOne2:
                targetStateOne = 2;
                break;
            case R.id.study_target_updateStateOne3:
                targetStateOne = 3;
                break;
        }
        switch (study_target_updateStateTwo.getCheckedRadioButtonId()){
            case R.id.study_target_updateStateTwo0:
                targetStateTwo = 0;
                break;
            case R.id.study_target_updateStateTwo1:
                targetStateTwo = 1;
                break;
            case R.id.study_target_updateStateTwo2:
                targetStateTwo = 2;
                break;
            case R.id.study_target_updateStateTwo3:
                targetStateTwo = 3;
                break;
        }
        switch (study_target_updateStateThree.getCheckedRadioButtonId()){
            case R.id.study_target_updateStateThree0:
                targetStateThree = 0;
                break;
            case R.id.study_target_updateStateThree1:
                targetStateThree = 1;
                break;
            case R.id.study_target_updateStateThree2:
                targetStateThree = 2;
                break;
            case R.id.study_target_updateStateThree3:
                targetStateThree = 3;
                break;
        }
        Target target = new Target();
        target.setTargetTitle(study_target_updateTitleNow);
        target.setTargetContentOne(study_target_updateContentOneNow);
        target.setTargetContentTwo(study_target_updateContentTwoNow);
        target.setTargetContentThree(study_target_updateContentThreeNow);
        target.setTargetStateOne(targetStateOne);
        target.setTargetStateTwo(targetStateTwo);
        target.setTargetStateThree(targetStateThree);
        target.update(study_target_updateObjectIdNow, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //??????????????????????????????????????????????????????
                    dialog_update.cancel();
                    study_target_updateContentOne.setText("");
                    study_target_updateContentTwo.setText("");
                    study_target_updateContentThree.setText("");
                    //??????RecyclerView
                    setRecyclerViewAdapter();
                    study_target_recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });
    }

    //??????????????????
    private void deleteMatter(final int position){
        //???????????????position??????recycleView????????????
        View view = study_target_recyclerView.getChildAt(position);
        //???????????????????????????ObjectId???????????????????????????text
        TextView textView = view.findViewById(R.id.study_target_objectId);
        String study_target_deleteObjectIdNow = textView.getText().toString();
        //???????????????objectId??????????????????
        Target target = new Target();
        target.setObjectId(study_target_deleteObjectIdNow);
        target.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    //??????RecyclerView
                    setRecyclerViewAdapter();
                }
            }
        });
    }


    private AlertDialog.Builder setDialog_update(final int position) {
        AlertDialog.Builder alertDialog_delete= new AlertDialog.Builder(this)
                .setTitle("??????")
                .setMessage("???????????????")
                .setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteMatter(position);
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