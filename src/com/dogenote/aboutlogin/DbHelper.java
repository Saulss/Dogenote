package com.dogenote.aboutlogin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private final static String NAME = "user.db";
	protected static SQLiteDatabase db;
	private final static int VERSION = 1;

	public DbHelper(Context context) {
		super(context, NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_TABLE = "create table user(id integer primary key autoincrement,username varchar(20) not null UNIQUE,passwd varchar(20) not null,sex varchar(64) not null,age integer not null)";
		String CREATE_ACTABLE = "create table account(id integer primary key autoincrement,username varchar(20) not null UNIQUE,passwd varchar(20) not null,sex varchar(64) not null,age integer not null)";
		db.execSQL(CREATE_TABLE);
		db.execSQL(CREATE_ACTABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

	}

}
