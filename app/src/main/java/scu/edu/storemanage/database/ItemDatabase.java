package scu.edu.storemanage.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Vector;

import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.item.User;
import scu.edu.storemanage.tools.Date;

/**
 * Created by 周秦春 on 2017/4/4.
 */

public class ItemDatabase {

    //数据库
    private SQLiteDatabase database;

    /**
     * 构造函数，传输用户Data数据库
     * @param db User数据库
     */
    public ItemDatabase(SQLiteDatabase db){
        database = db;
    }

    /**
     * 传入条形码，返回Item[]对象数组，失败返回null
     * @param barCode 帐号
     *
     * @return Item对象数组
     */
    public ArrayList<Item> searchByBarcode(String barCode){
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE,null,"barCode = ?",new String[]{barCode},null,
                null,null);

        //组装数据
        if (cursor==null)
        {
            return null;
        }

        //Item属性定义
        String ID;
        String itemName;
        Date purchaseDate;
        Date productDate;
        int qualityDate;
        double costPrice;
        double sellingPrice;
        double quantity;
        String barcode;

        //返回数据时使用
        ArrayList<Item> items = new ArrayList<Item>();

        if (cursor.moveToFirst()){//指针移动到第一行进行循环

            //遍历cursor对象
            do {
                ID = cursor.getString(cursor.getColumnIndex("ID"));
                itemName = cursor.getString(cursor.getColumnIndex("name"));
                purchaseDate = new Date(cursor.getString(cursor.getColumnIndex("purchaseDate")));
                productDate = new Date(cursor.getString(cursor.getColumnIndex("productDate")));
                qualityDate = cursor.getInt(cursor.getColumnIndex("qualityDate"));
                costPrice = cursor.getDouble(cursor.getColumnIndex("costPrice"));
                sellingPrice = cursor.getDouble(cursor.getColumnIndex("sellingPrice"));
                quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));
                barcode = cursor.getString(cursor.getColumnIndex("barCode"));

                //组装User并返回
                items.add(new Item(ID, barcode, itemName, purchaseDate, productDate, qualityDate, costPrice,
                        sellingPrice, quantity));
            }while (cursor.moveToNext());
        }else {
            return null;
        }

        return items;
    }

    /**
     * 传入商品名称，返回Item[]对象数组，失败返回null
     * @param itemName 商品名称
     *
     * @return Item对象数组
     */
    public ArrayList<Item> searchByItemNamw(String itemNames){
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE,null,"name = ?",new String[]{itemNames},null,
                null,null);

        //组装数据
        if (cursor==null)
        {
            return null;
        }

        //Item属性定义
        String ID;
        String itemName;
        Date purchaseDate;
        Date productDate;
        int qualityDate;
        double costPrice;
        double sellingPrice;
        double quantity;
        String barcode;

        //返回数据时使用
        ArrayList<Item> items = new ArrayList<Item>();

        if (cursor.moveToFirst()){//指针移动到第一行进行循环

            //遍历cursor对象
            do {
                ID = cursor.getString(cursor.getColumnIndex("ID"));
                itemName = cursor.getString(cursor.getColumnIndex("name"));
                purchaseDate = new Date(cursor.getString(cursor.getColumnIndex("purchaseDate")));
                productDate = new Date(cursor.getString(cursor.getColumnIndex("productDate")));
                qualityDate = cursor.getInt(cursor.getColumnIndex("qualityDate"));
                costPrice = cursor.getDouble(cursor.getColumnIndex("costPrice"));
                sellingPrice = cursor.getDouble(cursor.getColumnIndex("sellingPrice"));
                quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));
                barcode = cursor.getString(cursor.getColumnIndex("barCode"));

                //组装User并返回
                items.add(new Item(ID, barcode, itemName, purchaseDate, productDate, qualityDate, costPrice,
                        sellingPrice, quantity));
            }while (cursor.moveToNext());
        }else {
            return null;
        }

        return items;
    }

    /**
     * 将该商品插入数据库中
     * @param item
     */
    public void insert(Item item){

    }


}
