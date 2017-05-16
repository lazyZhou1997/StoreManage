package scu.edu.storemanage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.CustomerDatabase;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.database.OrdersDatabase;
import scu.edu.storemanage.item.Customer;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.item.Order;
import scu.edu.storemanage.tools.Date;
import scu.edu.storemanage.tools.ItemAdapter;
import scu.edu.storemanage.tools.ItemComparator;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 2017/4/10.
 */

public class SellItemActivity extends Activity {

    //该用户下的数据库
    private static SQLiteDatabase database;
    //商品数据
    private ItemDatabase itemDatabase;
    //订单数据
    private OrdersDatabase ordersDatabase;
    //顾客数据
    private CustomerDatabase customerDatabase;

    //UI
    private static TextView total_price_textview;
    private ListView sell_listview;
    private ImageButton return_button;
    private ImageButton scan_button;
    private Button clear_button;
    private Button pay_for_button;

    //订单中的所有商品
    private ArrayList<Item> orderItems = new ArrayList<Item>();
    private ItemAdapter itemAdapter;

    //顾客ID
    private int customerID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sell_item_layout);
        initUIComponent();

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        //获得数据信息
        itemDatabase = new ItemDatabase(database);
        ordersDatabase = new OrdersDatabase(database);
        customerDatabase = new CustomerDatabase(database);

        if (database == null) {
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }

        //ListView
        itemAdapter = new ItemAdapter(this, R.layout.listview_item_layout, orderItems);
        sell_listview.setAdapter(itemAdapter);

        //监听
        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //监听扫描条形码按钮
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentScanBarCode = new Intent(SellItemActivity.this, CaptureActivity.class);
                startActivityForResult(intentScanBarCode, 1);
            }
        });

        //监听结帐按钮
        pay_for_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //判断是否已经下单
                if (orderItems.isEmpty()) {
                    Toast.makeText(SellItemActivity.this, "请选择要购买的商品", Toast.LENGTH_SHORT).show();
                    return;
                }

                //判断库存是否足够。
                double quantity;
                for (Item i :
                        orderItems) {

                    quantity = itemDatabase.countTotalQuantitySameBarcode(i.getBarCode());

                    if (i.getQuantity() > quantity) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
                        builder.setTitle("商品不足");
                        builder.setMessage(i.getName() + "    库存只有" + quantity);
                        builder.setCancelable(false);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //购买失败对话框
                                AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
                                builder.setTitle("提示");
                                builder.setMessage("购买失败！");
                                builder.setCancelable(false);

                                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                                builder.show();
                            }
                        });

                        builder.show();

                        return;
                    }
                }

                //是否选择客户信息
                AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
                builder.setTitle("提示");
                builder.setMessage("是否选择订单顾客");
                builder.setCancelable(false);
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent chooseActivityIntent = new Intent(SellItemActivity.this, ChooseCustomerInfo.class);
                        ChooseCustomerInfo.setDatabase(database);
                        startActivityForResult(chooseActivityIntent, 2);
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        customerID = -1;

                        //保存订单信息
                        saveOrders();

                        AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
                        builder.setTitle("提示");
                        builder.setMessage("购买成功！");
                        builder.setCancelable(false);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //清空订单中的数据
                                orderItems.clear();
                                itemAdapter.notifyDataSetChanged();
                            }
                        });

                        builder.show();

                    }
                });
                builder.show();

            }
        });

        //监听清空按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
                builder.setTitle("清空");
                builder.setMessage("清空所有已经输入的内容！");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //清空订单中的所有数据
                        orderItems.clear();
                        //通知已经清空所有订单
                        itemAdapter.notifyDataSetChanged();

                        //显示总价：
                        double totalPrice = 0;
                        for (Item it :
                                orderItems) {
                            totalPrice += it.countTotalSellingPrice();
                        }
                        total_price_textview.setText(totalPrice + " " + "元");
                    }
                });

                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SellItemActivity.this, "取消清空", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });
    }


    /**
     * 初始化UI控件
     */
    private void initUIComponent() {

        total_price_textview = (TextView) findViewById(R.id.total_price_textView);
        sell_listview = (ListView) findViewById(R.id.sell_layout_listView);
        return_button = (ImageButton) findViewById(R.id.return_button_in_sell_layout);
        scan_button = (ImageButton) findViewById(R.id.scan_button_in_sell_layout);
        clear_button = (Button) findViewById(R.id.clear_button_in_sell);
        pay_for_button = (Button) findViewById(R.id.pay_for_button_in_sell);

    }

    /**
     * 监听扫描条形码之后的返回事件
     *
     * @param requestCode 请求码
     * @param resultCode  返回码
     * @param data        Intent数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    //获得返回的条形码
                    String barcode = data.getExtras().getString("result");

                    //从数据库中查找有相同条形码的商品信息
                    ArrayList<Item> items = itemDatabase.searchByBarcode(barcode);

                    //判断数据库中有无此商品
                    if (null == items) {
                        Toast.makeText(this, "暂时并没有商品", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //将items中的Item对象按照生产日期和购买日期排序,由小到大。
                    Collections.sort(items, new ItemComparator());
                    Collections.reverse(items);

                    //将最早生产的商品添加入订单，判断是否已经添加，如果已经添加，则把商品数量+1.
                    int index = -1;//存在商品在订单中的索引
                    Item item;//存在的商品
                    if ((index = orderItems.indexOf(items.get(0))) != -1) {//已经存在
                        item = orderItems.remove(index);//获取存在的商品。
                        item.setQuantity(item.getQuantity() + 1);//将商品的数量+1
                        orderItems.add(item);//将商品放入订单中
                    } else {//不存在
                        //设置数量为1
                        items.get(0).setQuantity(1);
                        orderItems.add(items.get(0));//不存在则直接把生产日期最早的放入其中
                    }

                    //显示总价：
                    double totalPrice = 0;
                    for (Item it :
                            orderItems) {
                        totalPrice += it.countTotalSellingPrice();
                    }

                    BigDecimal bg = new BigDecimal(totalPrice);
                    totalPrice = bg.setScale(2,BigDecimal.ROUND_UP).doubleValue();

                    total_price_textview.setText(totalPrice + " " + "元");


                    itemAdapter.notifyDataSetChanged();


                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "扫描条码失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (RESULT_OK == resultCode) {
                    customerID = data.getIntExtra("customerID", -1);
                    //保存订单信息
                    saveOrders();

                    AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("购买成功！");
                    builder.setCancelable(false);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //清空订单中的数据
                            orderItems.clear();
                            itemAdapter.notifyDataSetChanged();
                        }
                    });

                    builder.show();

                } else {
                    Toast.makeText(this, "选择会员失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return;
    }

    /**
     * 获得显示总价的TextView
     *
     * @return TextView
     */
    public static TextView getTotal_price_textview() {

        return total_price_textview;
    }

    /**
     * 保存出售订单
     */
    public void saveOrders() {

        //检查是否都有库存
        double quantity;


        //存储商品到订单中

        //将订单保存，将库存减少
        //属性
        ArrayList<Item> itemsInDatabase;//数据库中商品
        Order order;//订单
        Date currentdate;//购买日期

        //对每个在订单中的商品
        for (Item it :
                orderItems) {


            //从数据库中获得商品
            itemsInDatabase = itemDatabase.searchByBarcode(it.getBarCode());
            //将商品按日期排序
            Collections.sort(itemsInDatabase, new ItemComparator());

            //获得要购买的商品数量
            quantity = it.getQuantity();

            //更新数据库中商品数量
            for (Item itdatabase :
                    itemsInDatabase) {
                //商品是否足够
                if (itdatabase.getQuantity() < quantity) {
                    itdatabase.setQuantity(0);
                    quantity -= itdatabase.getQuantity();

                    //更新数据库中信息
                    itemDatabase.updateByBarcodeAndPurchaseDateAndProductDate(itdatabase);
                } else {
                    itdatabase.setQuantity(itdatabase.getQuantity() - quantity);

                    //更新数据库中信息
                    itemDatabase.updateByBarcodeAndPurchaseDateAndProductDate(itdatabase);
                    break;
                }
            }

            currentdate = new Date(Date.getCurrentTime());

            //更新订单数据库中的信息
            //价格
            double profit = (it.getSellingPrice() - it.getCostPrice()) * it.getQuantity();
            BigDecimal bg = new BigDecimal(profit);
            //订单
            order = new Order(-1, currentdate, it.getQuantity(), it.getID(), customerID, bg.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());

            //更新该用户的积分
            if (customerID != -1) {
                //获得顾客实例
                Customer customer = customerDatabase.searchByID(customerID);

                //更新顾客积分
                double integral = customer.getIntegral() + order.getProfit();

                //保留两位小数
                if ((integral * 1000) % 10 >= 5) {
                    integral = ((int) (integral * 100 + 1)) / 100.0;
                } else {
                    integral = ((int) (integral * 100)) / 100.0;
                }

                //更新积分
                customer.setIntegral(integral);

                //写回数据库
                customerDatabase.updateIntegralByID(customer);
            }

            //插入数据库
            ordersDatabase.insert(order);
        }

    }

    @Override
    public void finish() {

        if (!orderItems.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SellItemActivity.this);
            builder.setTitle("提示");
            builder.setMessage("如果没有保存，退出后订单将会消失！");
            builder.setCancelable(true);
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SellItemActivity.super.finish();
                }
            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(SellItemActivity.this, "取消退出", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        } else {
            super.finish();
        }
    }

}