package scu.edu.storemanage.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by 周秦春 on 2017/4/7.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private Context mContext;//上下文
    private String type;//建表类型：用户表还是用户数据表

    //表名
    public static final String USER_TABLE = "User";


    //建表语句选择
    public static final String USER = "USER";//建立存储用户信息的表
    public static final String DATA = "DATA";//建立存储该用户下数据信息的表
    //建表语句:USER
    private static final String CREATE_BOOK_USER = "create table User (" +
            "id integer primary key autoincrement," +
            "account text," +
            "password text," +
            "phonenumber text)";

    /**
     * 构造函数
     *
     * @param context
     * @param name
     * @param factory 允许查询数据时返回一个自定义的Cursor，一般传入null
     * @param version
     * @param type    选择建立用户表还是用户数据表
     */
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                              int version, String type) {
        super(context, name, factory, version);

        mContext = context;
        this.type = type;
    }

    /**
     * 数据库表的建立
     *
     * @param db 建立数据库表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d("TGP", "onCreate: reach");
        //建立用户表
        if (type.equals(USER)) {
            db.execSQL(CREATE_BOOK_USER);
            Toast.makeText(mContext, "建表成功", Toast.LENGTH_SHORT).show();
            Log.d("TGP", "onCreate: 建User表成功");
        }


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
