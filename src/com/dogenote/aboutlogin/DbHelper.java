package com.dogenote.aboutlogin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private final static String NAME = "user.db";
//	public static final String CREATE_TABLE = "CREATE TABLE user (id integer primary key autoincrement ,username varchar(64) not null, passwd varchar(64) not null, sex varchar(64), age integer)";
	protected static SQLiteDatabase db;
	private final static int VERSION = 1;

	public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
		super(context, NAME, cursorFactory, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "create table user(id integer primary key autoincrement,username varchar(20),passwd varchar(20),sex varchar(64),age integer)";
		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

	}

}
