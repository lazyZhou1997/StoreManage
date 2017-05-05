package scu.edu.storemanage.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.database.OrdersDatabase;
import scu.edu.storemanage.item.Order;
import scu.edu.storemanage.tools.OrderAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 2017/4/11.
 */

public class OrderActivity extends Activity {

    //该用户下的数据库
    private static SQLiteDatabase database;
    //商品数据
    private ItemDatabase itemDatabase;
    private OrdersDatabase ordersDatabase;

    //UI
    private ImageButton return_button;
    private ListView order_listview;
    private Button screen_button;

    //所有订单
    private ArrayList<Order> allOrders;

    //Order的ListView适配器
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_layout);
        initUIComponent();//初始化控件

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        //获得商品信息
        itemDatabase = new ItemDatabase(database);
        ordersDatabase = new OrdersDatabase(database);

        //获取所有订单
        allOrders = ordersDatabase.searchAllOrders();

        //日志
        for (Order order:
             allOrders) {

            Log.d(TAG, "onCreate: "+order.getDate());
        }

        //ListView配制.
        if (allOrders!=null){
            orderAdapter = new OrderAdapter(this,R.layout.member_listview_item_layout,allOrders);
            order_listview.setAdapter(orderAdapter);
        }

        if (database == null) {
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }

        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //监听listview点击事件
        order_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * 初始化UI控件
     */
    private void initUIComponent(){
        return_button = (ImageButton) findViewById(R.id.return_button_in_order_layout);
        order_listview = (ListView) findViewById(R.id.order_listview);
        screen_button = (Button) findViewById(R.id.screen_in_order_layout);

    }
}
