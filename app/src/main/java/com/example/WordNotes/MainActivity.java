package com.example.WordNotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.WordNotes.R;
import com.example.WordNotes.dao.UserDao;

public class MainActivity extends AppCompatActivity {
    String rusername, rpassword;
    private EditText rusr, rpwd;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rusr = findViewById(R.id.edit1_username);
        rpwd = findViewById(R.id.edit1_password);
        findViewById(R.id.btn1_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Intent intent=getIntent();
        rusername=intent.getStringExtra("usr");
        rpassword=intent.getStringExtra("pwd");
        sp=getSharedPreferences("user_login_info",MODE_PRIVATE);
        String username=sp.getString("username",null);
        String password=sp.getString("password",null);
//        rusr.setText(username);
//        rpwd.setText(password);
        rusr.setText(rusername);
        rpwd.setText(rpassword);
        if (username!=null&&password!=null) {
            Intent intent0 = new Intent(MainActivity.this, LoadActivity.class);
            startActivity(intent0);
            finish();
        }else {
            findViewById(R.id.btn1_login).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if ((rusr.getText().toString()).isEmpty() || (rpwd.getText().toString()).isEmpty()) {
                        Toast.makeText(MainActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                    } else {
                        new Thread() {
                            @Override
                            public void run() {
                                UserDao userDao = new UserDao();
                                boolean login = userDao.login(rusr.getText().toString(), rpwd.getText().toString());
                                if (login) {
                                    sp=getSharedPreferences("user_login_info",MODE_PRIVATE);
                                    editor=sp.edit();
                                    editor.putString("username",rusr.getText().toString());
                                    editor.putString("password",rpwd.getText().toString());
                                    editor.commit();
                                    Intent intent = new Intent(MainActivity.this, LoadActivity.class);
                                    startActivity(intent);
                                    finish();
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                } else {
                                    Looper.prepare();
                                    Toast.makeText(MainActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }
                        }.start();
                    }
                }
            });
        }
    }
}

