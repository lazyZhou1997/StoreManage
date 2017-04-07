package scu.edu.storemanage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import scu.edu.storemanage.item.User;

/**
 * Created by 周秦春 on 2017/4/7.
 */

public class UserDatabase {

    //数据库
    private SQLiteDatabase database;

    /**
     * 构造函数，传输User数据库
     * @param db User数据库
     */
    public UserDatabase(SQLiteDatabase db){
        database = db;
    }

    /**
     * 传入帐号，返回User对象，失败返回null
     * @param account 帐号
     * @return user对象
     */
    private User searchByAccount(String account){
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.USER_TABLE,null,"account = ?",new String[]{account},null,
                null,null);

        //组装数据
        if (cursor==null)
        {
            return null;
        }

        String user_account;
        String user_password;
        String user_phone;

        if (cursor.moveToFirst()){//指针移动到第一行进行循环
            //遍历cursor对象
            user_account = cursor.getString(cursor.getColumnIndex("account"));
            user_password = cursor.getString(cursor.getColumnIndex("password"));
            user_phone = cursor.getString(cursor.getColumnIndex("phonenumber"));

            //组装User并返回
            User user = new User(user_account,user_password,user_phone);
            return user;
        }else {
            return null;
        }
    }

    /**
     * 根据账户判断是否已经存在该用户,存在返回true，不存在返回false
     * @return 存在返回true，不存在返回false
     */
    public boolean exsit(User user){
        if (searchByAccount(user.getAccount())==null){
            return false;
        }
        return true;
    }

    /**
     * 将提供的User插入数据库
     * @param user 用户
     */
    public void insert(User user){

        //组装数据
        ContentValues values = new ContentValues();
        values.put("account",user.getAccount());
        values.put("password",user.getPassword());
        values.put("phonenumber",user.getPhonenumber());

        //插入数据库
        database.insert(MySQLiteOpenHelper.USER_TABLE,null,values);

        //清空values
        values.clear();

        return;
    }
}
