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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;

import java.util.ArrayList;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.item.Item;

/**
 * Created by 周秦春 on 2017/4/10.
 */

public class SellItemActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;

    //UI
    private TextView total_price_textview;
    private ListView sell_listview;
    private ImageButton return_button;
    private ImageButton scan_button;
    private Button clear_button;
    private Button save_button;
    private Button pay_for_button;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sell_item_layout);
        initUIComponent();

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        if (database==null){
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }

        //监听
        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //监听扫描条形码按钮
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentScanBarCode = new Intent(SellItemActivity.this, CaptureActivity.class);
                startActivityForResult(intentScanBarCode,1);
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

            }
        });

        //监听清空按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    /**
     * 初始化UI控件
     */
    private void initUIComponent(){

        total_price_textview = (TextView)findViewById(R.id.total_price_textView);
        sell_listview = (ListView)findViewById(R.id.sell_layout_listView);
        return_button = (ImageButton)findViewById(R.id.return_button_in_sell_layout);
        scan_button = (ImageButton)findViewById(R.id.scan_button_in_sell_layout);
        clear_button = (Button)findViewById(R.id.clear_button_in_sell);
        save_button = (Button)findViewById(R.id.save_button_in_sell);
        pay_for_button = (Button)findViewById(R.id.pay_for_button_in_sell);

    }

    /**
     * 监听扫描条形码之后的返回事件
     * @param requestCode 请求码
     * @param resultCode 返回码
     * @param data Intent数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 1:
                if (resultCode==RESULT_OK){
                    //获得返回的条形码
                    String barcode = data.getExtras().getString("result");

                    //从数据库中查找有相同条形码的商品信息
                    ItemDatabase itemDatabase = new ItemDatabase(database);
                    ArrayList<Item> items = itemDatabase.searchByBarcode(barcode);


                }
        }

        return;
    }
}