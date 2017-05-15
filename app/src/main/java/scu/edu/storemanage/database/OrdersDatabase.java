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
        Log.d(TAG, "insert: " + order.getDate().toString());
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
                orders.add(new Order(ID, date, quantity, itemID, customerID, profits));

            } while (cursor.moveToNext());

            return orders;
        } else {
            return null;
        }
    }

    /**
     * 根据传入的日期字符串查找订单
     *
     * @param currentDate 日期字符串
     * @return 符合要求的订单
     */
    public ArrayList<Order> searchOrdersByDate(String currentDate) {

        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ORDER_TABLE, null, " purchaseDate = ? ", new String[]{currentDate}, null,
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
                orders.add(new Order(ID, date, quantity, itemID, customerID, profits));

            } while (cursor.moveToNext());

            return orders;
        } else {
            return null;
        }
    }

    /**
     * 传入 年-月 类型的字符串，查处本月的所有订单
     * @return 本月的所有订单
     */
    public ArrayList<Order> searchCurrentMonthOrder() {

        //系统当前日期,年-月
        android.text.format.Time time = new android.text.format.Time();
        time.setToNow();
        String currentMonth = time.year + "-" + (time.month + 1);

        //返回的订单
        ArrayList<Order> allOrders = new ArrayList<Order>();
        ArrayList<Order> orders;

        for (int i = 1; i <= 31; i++) {//查找每一日的订单

            //查找数据
            orders = searchOrdersByDate(currentMonth + "-" + i);

            //将数据放入结果中
            if (orders != null){
                allOrders.addAll(orders);
            }

        }
        return allOrders;
    }

}
