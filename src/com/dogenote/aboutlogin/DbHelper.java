package com.dogenote.aboutlogin;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
	private final static String NAME = "user.db";
	public static final String CREATE_TABLE = "create table user (" + "id integer primary key autoincrement,"
			+ "username varchar(64)," + "passwd varchar(64)," + "sex varchar(64)," + "age integer)";
	protected static SQLiteDatabase db;
	private final static int VERSION = 1;

	public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
		super(context, NAME, cursorFactory, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {

		db.execSQL(CREATE_TABLE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
