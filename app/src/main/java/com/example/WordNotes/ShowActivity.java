package com.example.WordNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.WordNotes.R;
import com.example.WordNotes.dao.WordDao;

public class ShowActivity extends AppCompatActivity implements View.OnClickListener {
    String title,content,wordgroup,note,listgroup,username;
    int star,star1= R.mipmap.star1,star00=R.mipmap.star0,row,wordid,i;
    private TextView title0,content0,wordgroup0,note0,listgroup0;
    private ImageView star0;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        title0=findViewById(R.id.title_id);
        content0=findViewById(R.id.content_id);
        wordgroup0=findViewById(R.id.wordgroup_id);
        note0=findViewById(R.id.note_id);
        star0=findViewById(R.id.star_id);
        listgroup0=findViewById(R.id.listgroup_id);
        Intent intent=getIntent();
//        i=intent.getIntExtra("i",0);
        wordid=intent.getIntExtra("wordid",0);
//        username=intent.getStringExtra("user");
        title=intent.getStringExtra("title");
        content=intent.getStringExtra("content");
        wordgroup=intent.getStringExtra("wordgroup");
        note=intent.getStringExtra("note");
        star=intent.getIntExtra("star",0);
        listgroup=intent.getStringExtra("listgroup");
        title0.setText(title);
        content0.setText(content);
        wordgroup0.setText(wordgroup);
        note0.setText(note);
        if (star==1){
            star0.setBackgroundResource(star1);
        }
        listgroup0.setText(listgroup);
        title0.setOnClickListener(this);
        content0.setOnClickListener(this);
        wordgroup0.setOnClickListener(this);
        note0.setOnClickListener(this);
        star0.setOnClickListener(this);
        listgroup0.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_id:
                intent = new Intent(ShowActivity.this, UpdateActivity.class);
                intent.putExtra("wordid", wordid);
                intent.putExtra("pdz", "title");
                intent.putExtra("updata", title);
//                intent.putExtra("i", i);
//                intent.putExtra("user", username);
                startActivityForResult(intent, 1);
                break;
            case R.id.content_id:
                intent = new Intent(ShowActivity.this, UpdateActivity.class);
                intent.putExtra("wordid", wordid);
                intent.putExtra("pdz", "content");
                intent.putExtra("updata", content);
//                intent.putExtra("i", i);
//                intent.putExtra("user", username);
                startActivityForResult(intent, 1);
                break;
            case R.id.wordgroup_id:
                intent = new Intent(ShowActivity.this, UpdateActivity.class);
                intent.putExtra("wordid", wordid);
                intent.putExtra("pdz", "wordgroup");
                intent.putExtra("updata", wordgroup);
//                intent.putExtra("i", i);
//                intent.putExtra("user", username);
                startActivityForResult(intent, 1);
                break;
            case R.id.note_id:
                intent = new Intent(ShowActivity.this, UpdateActivity.class);
                intent.putExtra("wordid", wordid);
                intent.putExtra("pdz", "note");
                intent.putExtra("updata", note);
//                intent.putExtra("i", i);
//                intent.putExtra("user", username);
                startActivityForResult(intent, 1);
                break;
            case R.id.star_id:
                Handler handler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        row = (int) msg.obj;
                        System.out.println("row"+row);

                    }
                };
                if (star==1){
                    new Thread() {
                        @Override
                        public void run() {
                            WordDao wordDao=new WordDao();
                            int updatewords = wordDao.updateStar(0,wordid);
                            Message message = handler.obtainMessage();
                            message.obj = updatewords;
                            handler.sendMessage(message);
                        }
                    }.start();
                    if (row!=0) {
                        star0.setBackgroundResource(star00);
                    }
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            WordDao wordDao=new WordDao();
                            int updatewords = wordDao.updateStar(1,wordid);
                            Message message = handler.obtainMessage();
                            message.obj = updatewords;
                            handler.sendMessage(message);
                        }
                    }.start();
                    if (row!=0) {
                        star0.setBackgroundResource(star1);
                    }
                }
                Intent intent = new Intent(ShowActivity.this, LoadActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
            case R.id.listgroup_id:
                intent = new Intent(ShowActivity.this, UpdateActivity.class);
                intent.putExtra("wordid", wordid);
                intent.putExtra("pdz", "listgroup");
                intent.putExtra("updata", listgroup);
//                intent.putExtra("i", i);
//                intent.putExtra("user", username);
                startActivityForResult(intent, 1);
                break;
        }
    }
}