package com.example.WordNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.WordNotes.R;
import com.example.WordNotes.dao.WordDao;
import com.example.WordNotes.entity.WordBean;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    String updata0,pd,username;
    int id,i;
    private EditText updateet;
    private TextView updatetv;
    private Button updatebtn;
    private List<WordBean> wordlist = new ArrayList<>();
    private void setUpdata0(String pd,String updata1,int id){
        new Thread() {
            @Override
            public void run() {
                WordDao wordDao=new WordDao();
                System.out.println(id);
                int updatewords = wordDao.updateWords(pd,updata1,id);

                if (updatewords!=0) {
                    Looper.prepare();
                    Toast.makeText(UpdateActivity.this, "保存修改成功！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }else {
                    Looper.prepare();
                    Toast.makeText(UpdateActivity.this, "保存修改失败！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        }.start();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_main);
        updateet=findViewById(R.id.updateet_id);
        updatetv=findViewById(R.id.updatetv_id);
        updatebtn=findViewById(R.id.updatebtn_id);
        Intent intent=getIntent();
//        i=intent.getIntExtra("i",0);
        pd=intent.getStringExtra("pdz");
//        username=intent.getStringExtra("user");
        id=intent.getIntExtra("wordid",0);
        updata0=intent.getStringExtra("updata");
        updateet.setText(updata0);
        switch (pd){
            case "title":
                updatetv.setText("单词:");
                break;
            case "content":
                updatetv.setText("释义:");
                break;
            case "wordgroup":
                updatetv.setText("单词短语:");
                break;
            case "note":
                updatetv.setText("笔记:");
                break;
            case "listgroup":
                updatetv.setText("分组名:");
                break;
        }
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUpdata0(pd,updateet.getText().toString(),id);
//                Handler handler=new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        wordlist = (List) msg.obj;
//                    }
//                };
//                new Thread() {
//                    @Override
//                    public void run() {
//                        WordDao wordDao = new WordDao();
//                        List<WordBean> wordlist0 = new ArrayList<>();
//                        wordlist0 = wordDao.showAllWords(username);
//                        Message message = handler.obtainMessage();
//                        message.obj = wordlist0;
//                        handler.sendMessage(message);
//
//                    }
//                }.start();
                Intent intent = new Intent(UpdateActivity.this, LoadActivity.class);
//                intent.putExtra("i", i);
////                intent.putExtra("wordid", wordlist.get(i).getWordid());
//                intent.putExtra("user", username);
//                intent.putExtra("title", wordlist.get(i).getWordname());
//                intent.putExtra("content", wordlist.get(i).getParaphrase());
//                intent.putExtra("wordgroup", wordlist.get(i).getWordgroup());
//                intent.putExtra("note", wordlist.get(i).getNote());
//                intent.putExtra("star", wordlist.get(i).getStar());
//                intent.putExtra("listgroup", wordlist.get(i).getListgroup());
//                startActivityForResult(intent, 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }
}