package scu.edu.storemanage.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import scu.edu.storemanage.R;

/**
 * Created by asus on 2017/4/11.
 */

public class OrderActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;
    //该用户下的数据库名字
    private String databaseName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_layout);
        //初始化数据库的名字
        databaseName = getIntent().getStringExtra("databaseName");
        Toast.makeText(this, "OrderActivity", Toast.LENGTH_SHORT).show();
    }
}
