package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.CustomerDatabase;
import scu.edu.storemanage.item.Customer;
import scu.edu.storemanage.tools.MemberAdapter;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 2017/4/29.
 */

public class ChooseCustomerInfo extends Activity {

    //UI
    private ImageButton return_button;
    private ListView listview;

    //数据库
    private static SQLiteDatabase database;
    //用户数据
    private CustomerDatabase customerDatabase;

    //顾客数据
    private ArrayList<Customer> customers;
    //顾客数据适配器
    private MemberAdapter memberAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.choose_customer_layout);
        initUIComponent();//初始化控件

        //用户数据
        if(database==null) {
            Log.d(TAG, "onCreate: "+null);
        }
        customerDatabase = new CustomerDatabase(database);

        //获得所有顾客信息
        customers = customerDatabase.searchAllCustomer();
        if (null == customers) {//判断信息是否为空
            Toast.makeText(this, "暂无会员信息", Toast.LENGTH_SHORT).show();
            Toast.makeText(ChooseCustomerInfo.this, "未选择顾客", Toast.LENGTH_SHORT).show();
            finish();
        }

        //适配
        memberAdapter = new MemberAdapter(this, R.layout.member_listview_item_layout, customers);
        listview.setAdapter(memberAdapter);
        //监听选择顾客按钮
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //获得返回数据
                Customer customer = customers.get(i);
                Intent resultIntent = new Intent();
                resultIntent.putExtra("customerID", customer.getID());
                setResult(RESULT_OK,resultIntent);
                finish();
            }
        });


        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ChooseCustomerInfo.this, "未选择顾客", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }


    /**
     * 初始化控件
     */
    private void initUIComponent() {
        return_button = (ImageButton) findViewById(R.id.return_button_in_chose_customer_layout);
        listview = (ListView) findViewById(R.id.member_listview_in_chose_customer_layout);
    }

    /**
     * 传递数据库到此处
     *
     * @param database
     */
    public static void setDatabase(SQLiteDatabase database) {
        ChooseCustomerInfo.database = database;
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ChooseCustomerInfo.this, "未选择顾客", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}
