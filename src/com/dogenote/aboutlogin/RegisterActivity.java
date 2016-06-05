package com.dogenote.aboutlogin;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.dogenote.aboutlogin.User;
import com.dogenote.aboutlogin.UserService;
import com.example.dogenote.*;

public class RegisterActivity extends Activity {
    
    private EditText mUsername;
    private EditText mPasswd;
    private EditText mSex;
    private EditText mAge;
    
    private Button mRegister;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
    
        mUsername = (EditText)findViewById(R.id.re_edituser);
        mPasswd = (EditText)findViewById(R.id.re_editpasswd);
        mSex = (EditText)findViewById(R.id.re_editsex);
        mAge = (EditText)findViewById(R.id.re_editage);
        
        mRegister = (Button)findViewById(R.id.re_btregister);
        
        mRegister.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View arg0) {

                String userName = mUsername.getText().toString().trim();
                String passWd = mPasswd.getText().toString().trim();
                String Sex = mSex.getText().toString().trim();
                int Age = Integer.parseInt(mAge.getText().toString().trim());
                
                Log.i("TAG", userName);
                Log.i("TAG", passWd);
                Log.i("TAG", Sex);
                
                User user = new User();
                user.setUsername(userName);
                user.setPasswd(passWd);
                user.setSex(Sex);
                user.setAge(Age);
                
                System.out.println(user.getUsername());
                System.out.println(user.getPasswd());
                System.out.println(user.getSex());
                System.out.println(user.getAge());
                
                UserService userService = new UserService(getBaseContext());
                boolean flag = userService.Register(user);
                if (flag) {
                    Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Register false", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        
    }
}