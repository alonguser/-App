package com.example.WordNotes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.WordNotes.R;
import com.example.WordNotes.dao.UserDao;

import java.util.List;


public class RegisterActivity extends AppCompatActivity {
    private EditText username,password,password2,phone;
    private RadioGroup radioGroup;
    private CheckBox checkBox;
    int rbid;
    boolean bb;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=findViewById(R.id.edit2_username);
        password=findViewById(R.id.edit2_password);
        password2=findViewById(R.id.edit2_password2);
        phone=findViewById(R.id.edit2_phone);
        radioGroup=findViewById(R.id.rdg);
        checkBox=findViewById(R.id.cb_register);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                rbid=id;
                if (rbid==R.id.rb_nan){
                    sex="男";
                }else {
                    sex="女";
                }
            }
        });
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                bb=b;
            }
        });
        findViewById(R.id.btn2_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ((password.getText().toString()).isEmpty()||(password2.getText().toString()).isEmpty()||(username.getText().toString()).isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
                }else  if (rbid!=R.id.rb_nan&&rbid!=R.id.rb_nv) {
                    Toast.makeText(RegisterActivity.this, "请选择您的性别！", Toast.LENGTH_SHORT).show();
                }else if (bb==false){
                    Toast.makeText(RegisterActivity.this, "请勾选同意条款！", Toast.LENGTH_SHORT).show();
                }else if ((password.getText().toString()).equals(password2.getText().toString())==false){
                    Toast.makeText(RegisterActivity.this, "两次密码不一致！", Toast.LENGTH_SHORT).show();
                }else if ((phone.getText().toString()).isEmpty()){
                    Toast.makeText(RegisterActivity.this, "手机号不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    new Thread() {
                        @Override
                        public void run() {
                            UserDao userDao = new UserDao();
                            List stulist=userDao.findUser(username.getText().toString());
                            if (stulist.size()==0) {
                                boolean register = userDao.register(username.getText().toString(),password.getText().toString(),phone.getText().toString(),sex);
                                if (register){
                                    sp=getSharedPreferences("user_login_info",MODE_PRIVATE);
                                    editor=sp.edit();
                                    editor.putString("username",username.getText().toString());
                                    editor.putString("password",password.getText().toString());
                                    editor.commit();
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    intent.putExtra("usr", username.getText().toString());
                                    intent.putExtra("pwd", password.getText().toString());
                                    startActivityForResult(intent, 1);
                                    finish();
                                }else {
                                    Looper.prepare();
                                    Toast.makeText(RegisterActivity.this, "注册失败！", Toast.LENGTH_SHORT).show();
                                    Looper.loop();
                                }
                            }else {
                                Looper.prepare();
                                Toast.makeText(RegisterActivity.this, "此用户名已存在！", Toast.LENGTH_SHORT).show();
                                Looper.loop();
                            }
                        }
                    }.start();
                }
            }
        });
    }
}