package com.example.WordNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.example.WordNotes.R;
import com.example.WordNotes.dao.WordDao;
import com.example.WordNotes.entity.WordBean;


public class AddActivity extends AppCompatActivity {

    private EditText title1,content1,wordgrup1,note1,listgroup1;
    private CheckBox star1;
    private Button addbtn;
    boolean sc;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title1=findViewById(R.id.title1_id);
        content1=findViewById(R.id.content1_id);
        wordgrup1=findViewById(R.id.wordgroup1_id);
        note1=findViewById(R.id.note1_id);
        listgroup1=findViewById(R.id.listgroup1_id);
        star1=findViewById(R.id.star1_id);
        addbtn=findViewById(R.id.addbtn_id);
        star1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                sc=b;
            }
        });

        sp = getSharedPreferences("user_login_info", MODE_PRIVATE);
        username = sp.getString("username", null);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        WordBean wordBean=new WordBean();
                        wordBean.setWordname(title1.getText().toString());
                        wordBean.setParaphrase(content1.getText().toString());
                        wordBean.setWordgroup(wordgrup1.getText().toString());
                        wordBean.setNote(note1.getText().toString());
                        wordBean.setListgroup(listgroup1.getText().toString());
                        if (sc) {
                            wordBean.setStar(1);
                        }else {
                            wordBean.setStar(0);
                        }
                        WordDao wordDao = new WordDao();
                        int row = wordDao.addWords(wordBean,username);
                        if (row!=0){
                            Looper.prepare();
                            Toast.makeText(AddActivity.this, "添加单词成功！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }else {
                            Looper.prepare();
                            Toast.makeText(AddActivity.this, "添加单词失败！", Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }
                }.start();
                Intent intent = new Intent(AddActivity.this, LoadActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}