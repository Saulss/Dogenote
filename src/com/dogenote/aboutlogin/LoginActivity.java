package com.dogenote.aboutlogin;

import android.app.Activity;
import android.content.Intent;
//import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dogenote.R;
import com.dogenote.aboutlogin.UserService;
//import com.dogenote.aboutlogin.DbHelper;

public class LoginActivity extends Activity implements OnClickListener {
		  private EditText mUsername;
		  private EditText mPasswd;
		  private Button mLogin;
		  private Button mRegister;
		  
		  @Override
		  protected void onCreate(Bundle savedInstanceState) {
					super.onCreate(savedInstanceState);
					
					setContentView(R.layout.login);
					
					mUsername = (EditText) findViewById(R.id.editname);
					mPasswd = (EditText) findViewById(R.id.editpasswd);
					
					mLogin = (Button) findViewById(R.id.btlogin);
					mRegister = (Button) findViewById(R.id.btregister);
					
					mLogin.setOnClickListener(this);
					mRegister.setOnClickListener(this);
					
		  }
		  
		  @Override
		  public void onClick(View v) {
					// TODO Auto-generated method stub
					switch (v.getId()) {
							  case R.id.btlogin:
										String userName = mUsername.getText().toString().trim();
										String passWd = mPasswd.getText().toString().trim();
										
										UserService userService = new UserService(LoginActivity.this);
										
										boolean info = userService.showaccount(userName, passWd);
										boolean flag = userService.Login(userName, passWd);
										if (flag && info) {
												  Toast.makeText(LoginActivity.this, "Login success",
																	  Toast.LENGTH_SHORT).show();
										} else {
												  Toast.makeText(LoginActivity.this, "Login false", Toast.LENGTH_SHORT)
																	  .show();
										}
										
										boolean change_view = userService.Select(userName, passWd);
										Intent intentlogin = new Intent();
										if (change_view) {
												  intentlogin.setClass(LoginActivity.this, InfoActivity.class);
												  LoginActivity.this.startActivity(intentlogin);
												  LoginActivity.this.finish();
												  
										} else {
												  intentlogin.setClass(LoginActivity.this,
																	  com.dogenote.dogenote.MainActivity.class);
												  LoginActivity.this.startActivity(intentlogin);
												  LoginActivity.this.finish();
										}
										break;
							  
							  case R.id.btregister:
										Intent intent = new Intent();
										intent.setClass(LoginActivity.this, RegisterActivity.class);
										LoginActivity.this.startActivity(intent);
										LoginActivity.this.finish();
					}
					
		  }
}
