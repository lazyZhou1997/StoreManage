package scu.edu.storemanage.database;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by asus on 2017/4/27.
 */

public class OrdersItem {

    //数据库
    private SQLiteDatabase database;

    /**
     * 构造函数，传输用户Data数据库
     *
     * @param db User数据库
     */
    public OrdersItem(SQLiteDatabase db) {
        database = db;
    }


}
