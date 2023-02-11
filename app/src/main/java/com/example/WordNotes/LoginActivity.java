package com.example.WordNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.WordNotes.R;

public class LoginActivity extends AppCompatActivity {
    String uuser;
    private TextView textView,removeuser;
    private Button button;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        textView=findViewById(R.id.tv_login);
//        button=findViewById(R.id.btn_login);
        removeuser=findViewById(R.id.remove_user);

        sp=getSharedPreferences("user_login_info",MODE_PRIVATE);
        uuser=sp.getString("username",null);
        textView.setText(uuser);
//        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        removeuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sp = getSharedPreferences("user_login_info",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoadActivity.load.finish();
                startActivity(intent);
                finish();
            }
        });
    }
}