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
    public static final String USER_TABLE = "User";//用户表
    public static final String ITEM_TABLE = "Item";//商品表
    public static final String CUSTOMER_TABLE = "Customer";//顾客表
    public static final String ORDER_TABLE = "Orders";//订单表



    //建表语句选择
    public static final String USER = "USER";//建立存储用户信息的表
    public static final String DATA = "DATA";//建立存储该用户下数据信息的表
    //建表语句:USER
    private static final String CREATE_BOOK_USER = "create table User (" +
            "id integer primary key autoincrement," +
            "account text," +
            "password text," +
            "phonenumber text)";
    //建表语句：Item
    private static final String CREATE_BOOK_ITEM = "create table Item(" +
            "ID integer primary key autoincrement," +
            "name text," +
            "purchaseDate text," +
            "productDate text," +
            "qualityDate integer," +
            "costPrice real," +
            "sellingPrice real," +
            "quantity real," +
            "barCode text)";
    //建表语句：Customer
    private static final String CREATE_BOOK_CUSTOMER = "create table Customer(" +
            "ID integer primary key autoincrement," +
            "name text," +
            "telNumber text," +
            "address text," +
            "integral integer)";
    //建表语句：Order
    private static final String CREATE_BOOK_ORDER = "create table Orders(" +
            "ID integer primary key autoincrement," +
            "purchaseDate text," +
            "quantity real," +
            "itemID integer," +
            "customID integer," +
            "actSellPrice real," +
            "profit real)";


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

        //建立用户表
        if (type.equals(USER)) {
            db.execSQL(CREATE_BOOK_USER);
            Toast.makeText(mContext, "建表成功", Toast.LENGTH_SHORT).show();
        }else if (type.equals(DATA)){//建数据表

            db.execSQL(CREATE_BOOK_CUSTOMER);//建顾客表
            db.execSQL(CREATE_BOOK_ITEM);//建商品表
            db.execSQL(CREATE_BOOK_ORDER);//建订单表
            Toast.makeText(mContext, "建表数据表成功", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 升级数据库时使用，本次开发不用2017-4-10
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
