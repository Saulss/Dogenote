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

    /**
     * 用户登录
     * 
     * @param username
     * @param password
     * @return
     */
    public boolean Login(String username, String password) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "select * from user where username = ? and password = ? ";
        Cursor rawQuery = sqLiteDatabase.rawQuery(sql, new String[] { username,
                password });
        if (rawQuery.moveToFirst() == true) {
            rawQuery.close();
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     * 
     * @param user
     * @return
     */

    public boolean Register(User user) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        String sql = "insert into user (username,password,sex,age) values (?,?,?,?)";
        Object obj[] = { user.getUsername(), user.getPasswd(), user.getSex(),
                user.getAge() };
        sqLiteDatabase.execSQL(sql, obj);
        return true;
    }
}
