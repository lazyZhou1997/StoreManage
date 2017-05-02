package scu.edu.storemanage.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.database.OrdersDatabase;

/**
 * Created by 周秦春 on 2017/4/11.
 */

public class OrderActivity extends Activity {

    //该用户下的数据库
    private static SQLiteDatabase database;
    //商品数据
    private ItemDatabase itemDatabase;
    private OrdersDatabase ordersDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_layout);

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        //获得商品信息
        itemDatabase = new ItemDatabase(database);
        ordersDatabase = new OrdersDatabase(database);

        if (database == null) {
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
}
