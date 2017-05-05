package scu.edu.storemanage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.item.Order;
import scu.edu.storemanage.item.User;
import scu.edu.storemanage.tools.Date;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 2017/4/27.
 */

public class OrdersDatabase {

    //数据库
    private SQLiteDatabase database;

    /**
     * 构造函数，传输用户Data数据库
     *
     * @param db User数据库
     */
    public OrdersDatabase(SQLiteDatabase db) {
        database = db;
    }

    /**
     * 将提供的Order插入数据库
     *
     * @param order 订单
     */
    public void insert(Order order) {

        //组装数据
        ContentValues values = new ContentValues();
        values.put("purchaseDate", order.getDate().toString());
        Log.d(TAG, "insert: "+order.getDate().toString());
        values.put("quantity", order.getQuantity());
        values.put("itemID", order.getItemID());
        values.put("customID", order.getCustomerID());
        values.put("profit", order.getProfit());

        //插入数据库
        database.insert(MySQLiteOpenHelper.ORDER_TABLE, null, values);

        //清空values
        values.clear();

        return;
    }

    /**
     * 查找所有的订单
     *
     * @return 查找所有的订单
     */
    public ArrayList<Order> searchAllOrders() {
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ORDER_TABLE, null, null, null, null,
                null, null);

        //组装数据
        if (cursor.moveToFirst()) {//指针移动到第一行进行循环

            //order属性定义
            int ID;
            Date date;
            double quantity;
            int itemID;
            int customerID;
            double profits;


            //返回数据时使用
            ArrayList<Order> orders = new ArrayList<Order>();

            //遍历cursor对象
            do {
                ID = cursor.getInt(cursor.getColumnIndex("ID"));
                date = new Date(cursor.getString(cursor.getColumnIndex("purchaseDate")));
                quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));
                itemID = cursor.getInt(cursor.getColumnIndex("itemID"));
                customerID = cursor.getInt(cursor.getColumnIndex("customID"));
                profits = cursor.getDouble(cursor.getColumnIndex("profit"));

                //组装Order并返回
                orders.add(new Order(ID,date,quantity,itemID,customerID,profits));

            } while (cursor.moveToNext());

            return orders;
        } else {
            return null;
        }
    }

}
