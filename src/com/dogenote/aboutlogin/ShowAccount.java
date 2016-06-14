package com.dogenote.aboutlogin;

import com.example.dogenote.R;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.dogenote.aboutlogin.showinfo;
import com.dogenote.aboutlogin.DbHelper;
import com.dogenote.aboutlogin.User;

public class ShowAccount extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_account);

		TextView username = (TextView) findViewById(R.id.c_username);
		TextView sex = (TextView) findViewById(R.id.c_sex);
		TextView age = (TextView) findViewById(R.id.c_age);

		showinfo showinfo = new showinfo(this);
//		username.setText((CharSequence) showinfo.showaccountinfo(showinfo).getUsername());
//		sex.setText((CharSequence) showinfo.showaccountinfo(showinfo.showusername()).getSex());
//		age.setText( showinfo.showaccountinfo(showinfo.showusername()).getAge());
//		username.setText(showinfo.showaccountinfo(LoginActivity.username));
	}

}
