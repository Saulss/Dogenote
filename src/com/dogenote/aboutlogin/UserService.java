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
					String sql_select = "select * from user where username = ?";
					Object obj[] = { user.getUsername(), user.getPasswd(), user.getSex(), user.getAge() };
					Cursor rawQuery = sqLiteDatabase.rawQuery(sql_select, new String[] { user.getUsername() });
					
					if (rawQuery.moveToFirst() == true) {
							  sqLiteDatabase.close();
							  return false;
					} else {
							  sqLiteDatabase.execSQL(sql, obj);
							  sqLiteDatabase.close();
							  return true;
					}
					
		  }
		  
		  public boolean showaccount(String username, String passwd) {
					SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
					String sql = "select * from user where username = ? and passwd = ?";
					String sql_ac = "select * from account where username = ? and passwd = ?";
					Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username, passwd });
					Cursor Query = sqLiteDatabase.rawQuery(sql_ac, new String[] { username, passwd });
					if (rawQuery.moveToFirst() == true) {
							  if (Query.moveToFirst() == true) {
										// DeleteAccount();
										Query.close();
							  }
							  User user = new User();
							  user.setUsername(rawQuery.getString(rawQuery.getColumnIndex("username")));
							  user.setPasswd(rawQuery.getString(rawQuery.getColumnIndex("passwd")));
							  user.setSex(rawQuery.getString(rawQuery.getColumnIndex("sex")));
							  user.setAge(rawQuery.getInt(rawQuery.getColumnIndex("age")));
							  sqLiteDatabase.close();
							  Insert(user);
							  
							  rawQuery.close();
							  return true;
							  
					} else {
							  return false;
					}
		  }
		  
		  public boolean Select(String username, String passwd) {
					SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
					String sql = "select * from account where username = ? and passwd = ? ";
					Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username, passwd });
					if (rawQuery.moveToFirst() == true) {
							  rawQuery.close();
							  return true;
					} else {
							  rawQuery.close();
							  return false;
					}
					
		  }
		  
		  public boolean ACSelect() {
					SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
					String sql = "select * from account where id = ? ";
					Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { "1" });
					if (rawQuery.moveToFirst() == true) {
							  rawQuery.close();
							  return true;
					} else {
							  rawQuery.close();
							  return false;
					}
					
		  }
		  
		  public boolean Insert(User user) {
					SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
					String sql = "insert into account (username,passwd,sex,age) values (?,?,?,?)";
					Object obj[] = { user.getUsername(), user.getPasswd(), user.getSex(), user.getAge() };
					sqLiteDatabase.execSQL(sql, obj);
					sqLiteDatabase.close();
					return true;
		  }
		  
		  public User showinfo(User user) {
					SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
					String sql = "select * from account where id = ?";
					Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { "1" });
					if (rawQuery.moveToFirst() == true) {
							  user.setUsername(rawQuery.getString(rawQuery.getColumnIndex("username")));
							  user.setSex(rawQuery.getString(rawQuery.getColumnIndex("sex")));
							  user.setAge(rawQuery.getInt(rawQuery.getColumnIndex("age")));
							  
							  rawQuery.close();
							  return user;
					} else {
							  return null;
					}
		  }
		  
		  public boolean DeleteAccount() {
					SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
					String sql = "delete from account";
					String sql_delete = "DELETE FROM sqlite_sequence WHERE name='account'"; // 将自增长列归零
					sqLiteDatabase.execSQL(sql);
					sqLiteDatabase.execSQL(sql_delete);
					return true;
		  }
}
