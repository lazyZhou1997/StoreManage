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
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.database.OrdersDatabase;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.item.Order;

/**
 * Created by 周秦春 on 2017/4/11.
 */

public class AnalyseDataActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;
    //UI
    private ImageButton return_button;
    private ImageButton order_button;//订单
    private ImageButton main_page_button;//主功能界面
    private ImageButton member_button;//会员
    private ImageButton profit_button;//本月盈利
    private ImageButton hot_sell_button;//本月热卖
    private ImageButton advise_button;//建议进货
    private ImageButton over_date_button;//即将过期

    //数据库
    private OrdersDatabase ordersDatabase;//订单数据库
    private ItemDatabase itemDatabase;//商品数据库

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.analyse_data_layout);
        initUIComponent();//初始化控件
        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        if (database == null) {
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }
        //订单数据库
        ordersDatabase = new OrdersDatabase(database);
        itemDatabase = new ItemDatabase(database);

        //设置订单界面的监听
        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(AnalyseDataActivity.this, OrderActivity.class);
                startActivity(orderIntent);
            }
        });

        //设置会员界面的监听
        member_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memberIntent = new Intent(AnalyseDataActivity.this, MemberActivity.class);
                startActivity(memberIntent);
            }
        });

        //监听主菜单按钮
        main_page_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听本月盈利按钮
        profit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countProfit();
            }
        });

        //监听本月热卖
        hot_sell_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotSelling();
            }
        });

        //监听建议进货进货
        advise_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adviseGet();
            }
        });

        //监听即将过期按钮
        over_date_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overDue();
            }
        });
    }

    /**
     * 初始化UI控件
     */
    private void initUIComponent() {
        return_button = (ImageButton) findViewById(R.id.return_button_in_analyse_layout);
        order_button = (ImageButton) findViewById(R.id.order_function_button_in_analyse_layout);
        member_button = (ImageButton) findViewById(R.id.member_function_button_in_analyse_layout);
        profit_button = (ImageButton) findViewById(R.id.profit_button_in_analyse_layout);
        main_page_button = (ImageButton) findViewById(R.id.main_page_function_button_in_analyse_layout);
        hot_sell_button = (ImageButton) findViewById(R.id.hot_sell_function_button_in_analyse_layout);
        advise_button = (ImageButton) findViewById(R.id.advise_function_button_in_analyse_layout);
        over_date_button = (ImageButton) findViewById(R.id.overdate_function_button_in_analyse_layout);
    }

    /**
     * 计算本月的盈利，并显示给客户
     */
    private void countProfit() {
        //读取所有的订单信息，出售日期等于系统当前日期
        ArrayList<Order> orders = ordersDatabase.searchCurrentMonthOrder();

        //判断是否有订单
        if (orders.isEmpty()) {
            //对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
            builder.setTitle("利润");
            builder.setMessage("本月没有销售商品！");
            builder.setCancelable(false);

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.show();
            return;
        }

        int totalPrice = 0;
        //计算出利润
        for (Order order :
                orders) {
            totalPrice += order.getProfit();
        }


        //对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
        builder.setTitle("利润");
        builder.setMessage("本月利润： " + totalPrice);
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();

    }

    private void hotSelling() {
        //本月销售的所有商品
        ArrayList<Order> orders = ordersDatabase.searchCurrentMonthOrder();

        //判断本月是否销售商品
        if (orders.isEmpty()) {
            //对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
            builder.setTitle("热卖");
            builder.setMessage("本月没有销售商品！");
            builder.setCancelable(false);

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.show();
            return;
        }

        //整理找出数量最多的商品
        Hashtable<String, Double> hash = new Hashtable<String, Double>();

        int itemID;
        String itemName;
        double quantity;

        //整理出所有商品售卖情况
        for (Order order :
                orders) {
            //获得数据
            itemID = order.getItemID();
            itemName = itemDatabase.searchByItemID(itemID).getName();//查找商品名
            quantity = order.getQuantity();

            if (!hash.containsKey(itemName)) {
                //如果没有则直接添加
                hash.put(itemName, order.getQuantity());
            } else {
                //修改数量，重新添加
                quantity += hash.get(itemName);
                hash.put(itemName, quantity);
            }
        }

        double maxQuantity = 0;
        String maxItemName = "";

        //找出销售量最大的商品ID
        Enumeration<String> enumeration = hash.keys();
        while (enumeration.hasMoreElements()) {
            //获得数量
            itemName = enumeration.nextElement();
            //比较数量
            quantity = hash.get(itemName);
            if (maxQuantity <= quantity) {
                //修改最大属性
                maxItemName = itemName;
                maxQuantity = quantity;
            }
        }

        //查找数据库中的商品信息
        //对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
        builder.setTitle("热卖");
        builder.setMessage("本月热卖： " + maxItemName + "\n销售数量：" + maxQuantity);
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }

    /**
     * 建议进货
     */
    private void adviseGet() {
        //查询所有将要售完的商品
        ArrayList<Item> items = itemDatabase.findItemConsumeOut();

        //货物都还充足
        if (items == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
            builder.setTitle("库存");
            builder.setMessage("货物都还充足");
            builder.setCancelable(false);

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.show();
            return;
        }

        //整理打印信息
        String print = "";
        for (Item item :
                items) {
            print += "\t" + item.getName() + " 库存:" + item.getQuantity() + "\n";
        }

        //显示将要售完的商品
        AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
        builder.setTitle("库存");
        builder.setMessage("以下商品库存不足：\n" + print);
        builder.setCancelable(false);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();

    }

    /**
     * 即将过期的商品
     */
    private void overDue() {

        ArrayList<Item> overDueItems = itemDatabase.searchItemOverdueSoon();

        if (overDueItems == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);

            builder.setTitle("过期");
            builder.setMessage("没有商品即将或已经过期");
            builder.setCancelable(false);

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.show();
        } else {

            //打印信息
            String print = "";

            for (Item item :
                    overDueItems) {
                print += "\t" + item.getName() + "ID:" + item.getID() + "\n";
            }

            //显示将要过期的商品
            AlertDialog.Builder builder = new AlertDialog.Builder(AnalyseDataActivity.this);
            builder.setTitle("过期");
            builder.setMessage("以下商品即将在10天内过期：\n" + print);
            builder.setCancelable(false);

            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            builder.show();
        }

    }
}

