package com.example.WordNotes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.WordNotes.dao.WordDao;
import com.example.WordNotes.entity.WordBean;
import com.example.WordNotes.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private ListView mListView;
    private MyBaseAdapter myBaseAdapter;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    String username;
    private List<WordBean> wordlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mListView = (ListView) findViewById(R.id.lv_id);
        sp = getSharedPreferences("user_login_info", MODE_PRIVATE);
        username = sp.getString("username", null);
        Handler handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                wordlist = (List) msg.obj;
                myBaseAdapter = new MyBaseAdapter(MainActivity2.this, wordlist);
                mListView.setAdapter(myBaseAdapter);
            }
        };
        new Thread() {
            @Override
            public void run() {

                WordDao wordDao = new WordDao();
                List<WordBean> wordlist0 = new ArrayList<>();
                wordlist0 = wordDao.showAllWords(username);
                Message message = handler.obtainMessage();
                message.obj = wordlist0;
                handler.sendMessage(message);

            }
        }.start();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity2.this, ShowActivity.class);
//                intent.putExtra("i", i);
                intent.putExtra("wordid", wordlist.get(i).getWordid());
//                intent.putExtra("user", username);
                intent.putExtra("title", wordlist.get(i).getWordname());
                intent.putExtra("content", wordlist.get(i).getParaphrase());
                intent.putExtra("wordgroup", wordlist.get(i).getWordgroup());
                intent.putExtra("note", wordlist.get(i).getNote());
                intent.putExtra("star", wordlist.get(i).getStar());
                intent.putExtra("listgroup", wordlist.get(i).getListgroup());
                startActivityForResult(intent,1);
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long l) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);

                builder.setMessage("是否删除？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Handler handler=new Handler(){
                            @Override
                            public void handleMessage(Message msg) {
                                super.handleMessage(msg);
                                int deletewords = (int) msg.obj;
                                if (deletewords!=0){
                                    wordlist.remove(pos);
                                    myBaseAdapter.notifyDataSetChanged();
                                    Toast.makeText(MainActivity2.this, "已删除！", Toast.LENGTH_SHORT).show();
                                }else {
                                    Looper.prepare();
                                    Toast.makeText(MainActivity2.this, "删除失败！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        };
                        new Thread() {
                            @Override
                            public void run() {
                                WordDao wordDao=new WordDao();
                                int deletewords = wordDao.deleteWords(username,wordlist.get(pos).getWordname());
                                Message message = handler.obtainMessage();
                                message.obj = deletewords;
                                handler.sendMessage(message);
                            }
                        }.start();
                    }
                });
                builder.create().show();
                return true;
            }
        });
    }
}