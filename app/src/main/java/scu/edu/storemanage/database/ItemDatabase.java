package scu.edu.storemanage.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.tools.Date;

/**
 * Created by 周秦春 on 2017/4/4.
 */

public class ItemDatabase {

    //数据库
    private SQLiteDatabase database;

    /**
     * 构造函数，传输用户Data数据库
     *
     * @param db Data数据库
     */
    public ItemDatabase(SQLiteDatabase db) {
        database = db;
    }

    /**
     * 传入条形码，返回Item[]对象数组，失败返回null
     *
     * @param barCode 帐号
     * @return Item对象数组
     */
    public ArrayList<Item> searchByBarcode(String barCode) {
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE, null, "barCode = ?", new String[]{barCode}, null,
                null, null);

        //组装数据
        if (cursor == null) {
            return null;
        }

        //Item属性定义
        int ID;
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

        if (cursor.moveToFirst()) {//指针移动到第一行进行循环

            //遍历cursor对象
            do {
                ID = cursor.getInt(cursor.getColumnIndex("ID"));
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
            } while (cursor.moveToNext());
            return items;

        } else {
            return null;
        }
    }

    /**
     * 传入商品名称，返回Item[]对象数组，失败返回null
     *
     * @param itemNames 商品名称
     * @return Item对象数组
     */
    public ArrayList<Item> searchByItemName(String itemNames) {
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE, null, "name = ?", new String[]{itemNames}, null,
                null, null);

        //组装数据

        if (cursor.moveToFirst()) {//指针移动到第一行进行循环

            //Item属性定义
            int ID;
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

            //遍历cursor对象
            do {
                ID = cursor.getInt(cursor.getColumnIndex("ID"));
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
            } while (cursor.moveToNext());

            return items;
        } else {
            return null;
        }
    }

    /**
     * 将该商品插入数据库中
     *
     * @param item
     */
    public void insert(Item item) {

        //组装数据
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("purchaseDate", item.getPurchaseDate().toString());
        values.put("productDate", item.getProductDate().toString());
        values.put("qualityDate", item.getQualityDate());
        values.put("costPrice", item.getCostPrice());
        values.put("sellingPrice", item.getSellingPrice());
        values.put("quantity", item.getQuantity());
        values.put("barCode", item.getBarCode());
        database.insert(MySQLiteOpenHelper.ITEM_TABLE, null, values);
        values.clear();

        return;
    }

    /**
     * 根据传入的item的商品的条形码，购买日期，生产日期更新数据库
     *
     * @param item
     */
    public void updateByBarcodeAndPurchaseDateAndProductDate(Item item) {
        //组装数据
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("purchaseDate", item.getPurchaseDate().toString());
        values.put("productDate", item.getProductDate().toString());
        values.put("qualityDate", item.getQualityDate());
        values.put("costPrice", item.getCostPrice());
        values.put("sellingPrice", item.getSellingPrice());
        values.put("quantity", item.getQuantity());
        values.put("barCode", item.getBarCode());
        database.update(MySQLiteOpenHelper.ITEM_TABLE, values, "purchaseDate = ? and productDate = ? and barCode = ?",
                new String[]{item.getPurchaseDate().toString(), item.getProductDate().toString(), item.getBarCode()});

        return;

    }

    /**
     * 根据传入的item的商品的ID更新数据
     *
     * @param item
     */
    public void updateByID(Item item) {
        //组装数据
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("purchaseDate", item.getPurchaseDate().toString());
        values.put("productDate", item.getProductDate().toString());
        values.put("qualityDate", item.getQualityDate());
        values.put("costPrice", item.getCostPrice());
        values.put("sellingPrice", item.getSellingPrice());
        values.put("quantity", item.getQuantity());
        values.put("barCode", item.getBarCode());
        //更新数据
        database.update(MySQLiteOpenHelper.ITEM_TABLE, values, " ID = ? ", new String[]{item.getID() + ""});

        return;

    }

    /**
     * 根据传入item商品的条形码，购买日期，生产日期判断该商品在数据库中是否存在
     *
     * @param item 商品对象
     * @return 存在返回true，不存在返回false
     */
    public boolean exitByBarcodeAndPurchaseDateAndProductDate(Item item) {

        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE, null, "purchaseDate = ? and productDate = ? and barCode = ?",
                new String[]{item.getPurchaseDate().toString(), item.getProductDate().toString(), item.getBarCode()}, null,
                null, null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据传入item商品的条形码，购买日期，生产日期获得商品
     *
     * @param item 商品对象
     * @return 返回商品对象
     */
    public Item SearchByBarcodeAndPurchaseDateAndProductDate(Item item) {
        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE, null, "purchaseDate = ? and productDate = ? and barCode = ?",
                new String[]{item.getPurchaseDate().toString(), item.getProductDate().toString(), item.getBarCode()}, null,
                null, null);

        //组装数据
        if (cursor == null) {
            return null;
        }


        if (cursor.moveToFirst()) {//指针移动到第一行进行循环

            //Item属性定义
            int ID;
            String itemName;
            Date purchaseDate;
            Date productDate;
            int qualityDate;
            double costPrice;
            double sellingPrice;
            double quantity;
            String barcode;

            //组装数据
            ID = cursor.getInt(cursor.getColumnIndex("ID"));
            itemName = cursor.getString(cursor.getColumnIndex("name"));
            purchaseDate = new Date(cursor.getString(cursor.getColumnIndex("purchaseDate")));
            productDate = new Date(cursor.getString(cursor.getColumnIndex("productDate")));
            qualityDate = cursor.getInt(cursor.getColumnIndex("qualityDate"));
            costPrice = cursor.getDouble(cursor.getColumnIndex("costPrice"));
            sellingPrice = cursor.getDouble(cursor.getColumnIndex("sellingPrice"));
            quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));
            barcode = cursor.getString(cursor.getColumnIndex("barCode"));

            //组装item并返回
            return new Item(ID, barcode, itemName, purchaseDate, productDate, qualityDate, costPrice,
                    sellingPrice, quantity);

        } else {
            return null;
        }

    }

    /**
     * 返回具有相同条形码的商品的总数量
     *
     * @param barcode
     * @return 商品总数量
     */
    public double countTotalQuantitySameBarcode(String barcode) {

        ArrayList<Item> items = searchByBarcode(barcode);

        double quantity = 0;
        for (Item i :
                items) {
            quantity += i.getQuantity();
        }

        return quantity;
    }

    /**
     * 根据商品ID返回商品对象
     *
     * @param ID 商品ID
     * @return 商品对象
     */
    public Item searchByItemID(int ID) {

        //查询数据
        Cursor cursor = database.query(MySQLiteOpenHelper.ITEM_TABLE, null, "ID = ?", new String[]{ID + ""}, null,
                null, null);

        if (cursor.moveToFirst()) {//指针移动到第一行进行循环

            //Item属性定义
            String itemName;
            Date purchaseDate;
            Date productDate;
            int qualityDate;
            double costPrice;
            double sellingPrice;
            double quantity;
            String barcode;

            //组装数据
            itemName = cursor.getString(cursor.getColumnIndex("name"));
            purchaseDate = new Date(cursor.getString(cursor.getColumnIndex("purchaseDate")));
            productDate = new Date(cursor.getString(cursor.getColumnIndex("productDate")));
            qualityDate = cursor.getInt(cursor.getColumnIndex("qualityDate"));
            costPrice = cursor.getDouble(cursor.getColumnIndex("costPrice"));
            sellingPrice = cursor.getDouble(cursor.getColumnIndex("sellingPrice"));
            quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));
            barcode = cursor.getString(cursor.getColumnIndex("barCode"));

            //组装item并返回
            return new Item(ID, barcode, itemName, purchaseDate, productDate, qualityDate, costPrice,
                    sellingPrice, quantity);

        } else {
            return null;
        }
    }

    /**
     * 找出数量小于5的手又商品
     *
     * @return
     */
    public ArrayList<Item> findItemConsumeOut() {

        //查询数据
        Cursor cursor = database.rawQuery("select name,sum(quantity) as total_quantity " +
                "from " + MySQLiteOpenHelper.ITEM_TABLE + " " +
                "group by name " +
                "having total_quantity <= 5", null);

        //组装并返回数据
        if (cursor.moveToFirst()) {//指针移动到第一行进行循环

            //Item属性定义
            String itemName;
            double quantity;

            //临时Item
            Item item;
            //返回数据时使用
            ArrayList<Item> items = new ArrayList<Item>();

            //遍历cursor对象
            do {
                itemName = cursor.getString(cursor.getColumnIndex("name"));
                quantity = cursor.getDouble(cursor.getColumnIndex("total_quantity"));

                //组装User并返回
                item = new Item();
                item.setName(itemName);
                item.setQuantity(quantity);

                items.add(item);
            } while (cursor.moveToNext());

            return items;
        } else {
            return null;
        }
    }


    /**
     * 查找已经过期或者将要过期的商品
     *
     * @return 查找已经过期或者将要过期的商品的商品
     */
    public ArrayList<Item> searchItemOverdueSoon() {

        //查找所有商品
        Cursor cursor = database.rawQuery("SELECT *" +
                " FROM Item", null);

        //所有商品
        ArrayList<Item> allItems = null;

        if (cursor.moveToFirst()) {
            //分配空间
            allItems = new ArrayList<Item>();

            //Item的属性
            int ID;
            String sProductDate;
            int qualityDate;
            String barCode;//商品条形码
            String name;//商品名称
            String sPurchaseDate;//商品买入日期
            double costPrice;//商品进价
            double sellingPrice;//商品售价
            double quantity;//商品数量

            //商品
            Item item = new Item();

            //日期：
            Date productDate;
            Date purchaseDate;
            Date currentDate = new Date(Date.getCurrentTime());

            //从产品生产到现在经过了多少天
            long deltDay;

            do {
                sProductDate = cursor.getString(cursor.getColumnIndex("productDate"));
                productDate = new Date(sProductDate);
                qualityDate = cursor.getInt(cursor.getColumnIndex("qualityDate"));

                //产品生产至今所经历过的时间
                deltDay = productDate.overDate(currentDate);

                //过期日期小于10天，则添加入
                if ((qualityDate * 30 - deltDay) < 10) {

                    ID = cursor.getInt(cursor.getColumnIndex("ID"));
                    //购买日期
                    sPurchaseDate = cursor.getString(cursor.getColumnIndex("purchaseDate"));
                    purchaseDate = new Date(sPurchaseDate);

                    barCode = cursor.getString(cursor.getColumnIndex("barCode"));
                    name = cursor.getString(cursor.getColumnIndex("name"));
                    costPrice = cursor.getDouble(cursor.getColumnIndex("costPrice"));
                    sellingPrice = cursor.getDouble(cursor.getColumnIndex("sellingPrice"));
                    quantity = cursor.getDouble(cursor.getColumnIndex("quantity"));

                    item = new Item(ID, barCode, name, purchaseDate, productDate, qualityDate, costPrice, sellingPrice, quantity);
                    allItems.add(item);
                }

            } while (cursor.moveToNext());

            //如果没有过期商品，则返回null
            if (allItems.isEmpty()) {
                return null;
            } else {
                return allItems;
            }

        } else {
            return null;
        }
    }

}
