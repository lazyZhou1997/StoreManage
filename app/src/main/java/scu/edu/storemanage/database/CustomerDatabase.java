package scu.edu.storemanage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import scu.edu.storemanage.item.Customer;
import scu.edu.storemanage.item.Item;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 2017/4/27.
 */

public class CustomerDatabase {

    //数据库
    private SQLiteDatabase database;

    /**
     * 构造函数，传输用户Data数据库
     *
     * @param db Data数据库
     */
    public CustomerDatabase(SQLiteDatabase db) {
        database = db;
    }

    /**
     * 将该会员信息插入数据库中
     *
     * @param customer
     */
    public void insert(Customer customer) {

        //组装数据
        ContentValues values = new ContentValues();
        values.put("name", customer.getName());
        values.put("address", customer.getAddress());
        values.put("telNumber", customer.getTelNumber());
        values.put("integral", customer.getIntegral());
        database.insert(MySQLiteOpenHelper.CUSTOMER_TABLE, null, values);

        values.clear();
        return;
    }

    /**
     * 根据顾客的名字查找顾客
     *
     * @param name 顾客的名字
     * @return 返回查找到的顾客，没找到返回null
     */
    public Customer searchByName(String name) {

        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.CUSTOMER_TABLE, null, "name = ?", new String[]{name}, null,
                null, null);

        //组装数据
        if (cursor.moveToFirst()) {
            //组装数据
            int ID;
            String telNumber;
            String address;
            double integral;

            //获取数据库中数据
            ID = cursor.getInt(cursor.getColumnIndex("ID"));
            telNumber = cursor.getString(cursor.getColumnIndex("telNumber"));
            address = cursor.getString(cursor.getColumnIndex("address"));
            integral = cursor.getDouble(cursor.getColumnIndex("integral"));

            Customer customer = new Customer(ID, name, telNumber, address, integral);
            return customer;

        } else {
            //没有查找到
            return null;
        }
    }

    /**
     * 返回数据库中所有会员信息
     *
     * @return 所有会员信息
     */
    public ArrayList<Customer> searchAllCustomer() {

        //查找数据
        Cursor cursor = database.query(MySQLiteOpenHelper.CUSTOMER_TABLE, null, null, null, null,
                null, null);

        //组装数据
        if (cursor.moveToFirst()){

            //定义属性
            int ID;
            String name;
            String telNumber;
            String address;
            double integral;

            ArrayList<Customer> customers = new ArrayList<Customer>();

            do {

                //获得数据库中数据
                ID = cursor.getInt(cursor.getColumnIndex("ID"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                telNumber = cursor.getString(cursor.getColumnIndex("telNumber"));
                address = cursor.getString(cursor.getColumnIndex("address"));
                integral = cursor.getDouble(cursor.getColumnIndex("integral"));

                //添加数据到
                customers.add(new Customer(ID,name,telNumber,address,integral));

            }while (cursor.moveToNext());

            //返回顾客
            return customers;
        }else {
            return null;
        }
    }

    /**
     * 通过名字更新顾客
     * @param customer
     */
    public void updateByName(Customer customer){

        //组装数据
        ContentValues values = new ContentValues();
        values.put("telNumber", customer.getTelNumber());
        values.put("address", customer.getAddress());
        database.update(MySQLiteOpenHelper.CUSTOMER_TABLE, values, "name = ?",
                new String[]{customer.getName()});

        return;
    }

}
