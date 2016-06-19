package com.dogenote.aboutlogin;

import com.example.dogenote.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class InfoActivity extends Activity implements OnClickListener{
	private TextView Username;
	private TextView Sex;
	private TextView Age;
	private UserService Service;
	private User user;
	private Button logout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_account);
		
		Username = (TextView) findViewById(R.id.c_username);
		Sex = (TextView) findViewById(R.id.c_sex);
		Age = (TextView) findViewById(R.id.c_age);
		logout = (Button) findViewById(R.id.logout);
		
		logout.setOnClickListener(this);
		
		Service = new UserService(this);
		user = new User();
		Service.showinfo(user);
		Username.setText(user.getUsername());
		Sex.setText(user.getSex());
//		Age.setText(getString(user.getAge()));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.logout:
			Intent intent = new Intent();
			intent.setClass(this, LoginActivity.class);
			Service.DeleteAccount();
			this.startActivity(intent);
			this.finish();
			
			break;
		}
	}

}
