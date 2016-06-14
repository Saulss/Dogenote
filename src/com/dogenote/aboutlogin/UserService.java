package com.dogenote.aboutlogin;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dogenote.aboutlogin.DbHelper;
import com.dogenote.aboutlogin.User;

public class UserService {
	private DbHelper dbHelper;

	public UserService(Context context) {
		dbHelper = new DbHelper(context);
	}

	public boolean Login(String username, String passwd) {
		SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
		String sql = "select * from user where username = ? and passwd = ? ";
		Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username, passwd });
		if (rawQuery.moveToFirst() == true) {
			rawQuery.close();
			return true;
		}
		rawQuery.close();
		return false;
	}

	public boolean Register(User user) {
		SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
		String sql = "insert into user (username,passwd,sex,age) values (?,?,?,?)";
		Object obj[] = { user.getUsername(), user.getPasswd(), user.getSex(), user.getAge() };
		sqLiteDatabase.execSQL(sql, obj);
		sqLiteDatabase.close();
		return true;
	}

	public boolean changelogininfo(String username, String passwd) {

		User user = new User();
		SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
		String sql = "update user set info = 1 where username = ? and passwd = ?";
		sqLiteDatabase.execSQL(sql);
		sqLiteDatabase.close();
		return true;
	}
}
