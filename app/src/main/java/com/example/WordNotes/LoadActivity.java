package com.example.WordNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.WordNotes.R;

public class LoadActivity extends AppCompatActivity {

    private ImageView iconid;
    private Button addid,beiid;
    public static LoadActivity load=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        iconid=findViewById(R.id.icon_id);
        addid=findViewById(R.id.add_id);
        beiid=findViewById(R.id.bei_id);
        load=this;
        iconid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        beiid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        addid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoadActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }
}