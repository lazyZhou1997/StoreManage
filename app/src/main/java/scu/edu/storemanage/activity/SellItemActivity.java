package scu.edu.storemanage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;

import java.util.ArrayList;
import java.util.Collections;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.tools.ItemAdapter;
import scu.edu.storemanage.tools.ItemComparator;

/**
 * Created by 周秦春 on 2017/4/10.
 */

public class SellItemActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;
    //商品数据
    private ItemDatabase itemDatabase;

    //UI
    private static TextView total_price_textview;
    private ListView sell_listview;
    private ImageButton return_button;
    private ImageButton scan_button;
    private Button clear_button;
    private Button save_button;
    private Button pay_for_button;

    //订单中的所有商品
    private ArrayList<Item> orderItems = new ArrayList<Item>();
    private ItemAdapter itemAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sell_item_layout);
        initUIComponent();

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        //获得商品信息
        itemDatabase = new ItemDatabase(database);

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

            }
        });

        //监听保存按钮
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrders();
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
        save_button = (Button) findViewById(R.id.save_button_in_sell);
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
                        orderItems.add(items.get(0));//不存在则直接把生产日期最早的放入其中
                    }

                    //显示总价：
                    double totalPrice = 0;
                    for (Item it :
                            orderItems) {
                        totalPrice += it.countTotalSellingPrice();
                    }
                    total_price_textview.setText(totalPrice + " " + "元");


                    itemAdapter.notifyDataSetChanged();


                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "扫描条码失败", Toast.LENGTH_SHORT).show();
                }
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
        for (Item i :
                orderItems) {
            quantity = itemDatabase.countTotalQuantitySameBarcode(i.getBarCode());
            if (i.getQuantity() > quantity) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("商品不足");
                builder.setMessage(i.getName()+"    库存只有"+quantity);
                builder.setCancelable(false);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(SellItemActivity.this, "保存(结账)失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                });

                builder.show();
            }
        }

        //FIXME
    }

    @Override
    public void finish() {

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

    }
}