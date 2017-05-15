package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.MySQLiteOpenHelper;
import scu.edu.storemanage.item.User;

/**
 * Created by 周秦春 on 2017/4/8.
 */

public class MainFunctionActivity extends Activity {


    //UI控件
    private ImageButton input_item_button;//录入
    private ImageButton sell_item_button;//售卖
    private ImageButton search_item_button;//查找
    private ImageButton analyse_data_button;//分析数据
    private ImageButton order_button;//订单
    private ImageButton member_button;//会员

    //登录的用户信息
    private User user;
    //数据库名
    private String databaseName;
    //数据库
    private static SQLiteDatabase database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_function_layout);
        initUIComponent();//初始化控件

        getUserInfo(getIntent());//获得登录用户信息(user)
        databaseName = user.getAccount()+user.getPassword()+".db";
        //创建数据库
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(MainFunctionActivity.this,databaseName,null,1,MySQLiteOpenHelper.DATA);
        database = helper.getWritableDatabase();

        //设置录入商品按钮的监听
        input_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputIntent = new Intent(MainFunctionActivity.this,InputItemActivity.class);
                startActivity(inputIntent);
            }
        });

        //设置售卖商品的监听
        sell_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sellingIntent = new Intent(MainFunctionActivity.this,SellItemActivity.class);
                startActivity(sellingIntent);
            }
        });

        //设置搜索商品的监听
        search_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MainFunctionActivity.this,SearchItemActivity.class);
                startActivity(searchIntent);
            }
        });

        //设置分析数据的监听
        analyse_data_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent analyseIntent = new Intent(MainFunctionActivity.this,AnalyseDataActivity.class);
                startActivity(analyseIntent);
            }
        });

        //设置订单界面的监听
        order_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent orderIntent = new Intent(MainFunctionActivity.this,OrderActivity.class);
                startActivity(orderIntent);
            }
        });

        //设置会员界面的监听
        member_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent memberIntent = new Intent(MainFunctionActivity.this,MemberActivity.class);
                startActivity(memberIntent);
            }
        });
    }


    /**
     * 初始化UI控件
     */
    private void initUIComponent(){

        //4个功能Button
        input_item_button = (ImageButton)findViewById(R.id.input_function_button);
        analyse_data_button = (ImageButton)findViewById(R.id.analyse_function_button);
        search_item_button = (ImageButton)findViewById(R.id.search_function_button);
        sell_item_button = (ImageButton)findViewById(R.id.sell_function_button);
        //三个底部Button
        order_button = (ImageButton)findViewById(R.id.order_function_button);
        member_button = (ImageButton)findViewById(R.id.member_function_button);
    }

    /**
     * 获取登录的用户信息
     * @param intent 保存有用户信息的 意图
     */
    private void getUserInfo(Intent intent){

        String account = intent.getStringExtra("account");
        String password = intent.getStringExtra("password");
        String phoneNumber = intent.getStringExtra("phoneNumber");

        //组装用户信息
        user = new User(account,password,phoneNumber);
        return;
    }

    /**
     *获得用户数据数据库
     * @return 用户数据库,可能会为空
     */
    public static SQLiteDatabase getDatabase(){
        return database;
    }
}
