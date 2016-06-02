package com.dogenote.aboutlogin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper{
	 private final static String NAME = "user.db";
	    private final static int VERSION = 1;

	    public DbHelper (Context context) {
	        super(context, NAME, null, VERSION);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) {

	        String sql = "create table user (id integer primary key autoincrement,username varchar(64),passwd varchar(64),sex varchar(64),age integer)";
	        db.execSQL(sql);

	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	    }
	
	
}
