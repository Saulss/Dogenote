package com.dogenote.aboutlogin;

import com.dogenote.aboutlogin.DbHelper;
import com.dogenote.aboutlogin.User;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class showinfo {

	private DbHelper helper;

	public showinfo(Context context) {
		helper = new DbHelper(context);
	}
	
//	public String showusername(){
//		SQLiteDatabase db = helper.getReadableDatabase();
//		Cursor cursor = db.query("user",null,"info = 1", null,null,null,null);
//		if(cursor.getCount()>0){
//			if(cursor.moveToFirst()){
//				String username = cursor.getString(1);
//				return username;
//			}
//		}
//		return null;
//		
//	}
	
	public User showaccountinfo(String name){
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select * from user where username=?", new String[]{ name });
		User user = null;
		if(cursor.moveToNext()){
			int id = cursor.getInt(cursor.getColumnIndex("id"));
			String username = cursor.getString(cursor.getColumnIndex("username"));
			String passwd = cursor.getString(cursor.getColumnIndex("passwd"));
			String sex = cursor.getString(cursor.getColumnIndex("sex"));
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			
			user = new User(id,username,passwd,sex,age);
		}
		db.close();
		
		return user;
		
	}

	
}
