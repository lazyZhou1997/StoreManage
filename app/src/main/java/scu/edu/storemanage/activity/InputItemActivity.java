package scu.edu.storemanage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.format.Time;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zxing.activity.CaptureActivity;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.database.MySQLiteOpenHelper;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.tools.Date;

/**
 * Created by 周秦春 on 2017/4/10.
 */

public class InputItemActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;

    //UI
    private ImageButton return_button;//返回按钮
    private ImageButton scan_button;//跳转到扫描条形码
    private EditText name_edit;//商品名输入框
    private EditText cost_price_edit;//进价输入框
    private EditText sell_price_edit;//售价输入框
    private EditText quality_date_edit;//保质期输入框
    private EditText quantity_edit;//数量输入框
    private EditText product_year_edit;//生产日期，年
    private EditText product_month_edit;//生产日期，月
    private EditText product_day_edit;//生产日期，日
    private TextView barcode_text;//条形码显示框
    private Button clear_button;//清空按钮
    private Button save_button;//保存按钮



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.input_item_layout);
        initUIComponent();//初始化UI

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        if (database==null){
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }

        //直接跳转到扫描条形码的位置
        Intent barcodeIntent = new Intent(this, CaptureActivity.class);
        startActivityForResult(barcodeIntent, 0);

        //监听跳转到扫描二条形码界面
        scan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent barcodeIntent = new Intent(InputItemActivity.this, CaptureActivity.class);
                startActivityForResult(barcodeIntent, 0);
            }
        });

        //监听退出录入商品
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听清空所有数据按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(InputItemActivity.this);
                builder.setTitle("清空");
                builder.setMessage("清空所有已经输入的内容！");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cost_price_edit.setText("");
                        name_edit.setText("");
                        product_day_edit.setText("");
                        product_month_edit.setText("");
                        product_year_edit.setText("");
                        quality_date_edit.setText("");
                        quantity_edit.setText("");
                        sell_price_edit.setText("");
                        barcode_text.setText("");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(InputItemActivity.this, "取消清空", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });

        //监听保存数据按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();;
            }
        });


    }

    //保存用户输入的数据
    private void saveData(){

        //edit中获取输入数据
        String name = name_edit.getText().toString();
        String costPrice = cost_price_edit.getText().toString();
        String sellingPrice = sell_price_edit.getText().toString();
        String qualityDate = quality_date_edit.getText().toString();
        String quantity = quantity_edit.getText().toString();
        String productDate = product_year_edit.getText().toString()+"_"+product_month_edit.getText().toString()+
                "_"+product_day_edit.getText().toString();
        String barcode = barcode_text.getText().toString();

        //判断输入是否为空
        if (name.equals("")||costPrice.equals("")||sellingPrice.equals("")||qualityDate.equals("")||
                quantity.equals("")||productDate.equals("")||barcode.equals("")){
            Toast.makeText(this, "请补全输入", Toast.LENGTH_SHORT).show();
            return;
        }

        //取得系统的当前日期
        Time time = new Time();
        String purchaseDate = time.year+"_"+(time.month+1)+"_"+time.monthDay;

        Item item = new Item(-1,barcode,name, new Date(purchaseDate),new Date(productDate),
                Integer.parseInt(qualityDate),Double.parseDouble(costPrice),Double.parseDouble(sellingPrice),
                Double.parseDouble(quantity));

        //数据库操作
        ItemDatabase itemDatabase = new ItemDatabase(database);
        //是否已经存在
        if (itemDatabase.exitByBarcodeAndPurchaseDateAndProductDate(item)){
            //已经存在,更新
            Item itemInDatabase = itemDatabase.SearchByBarcodeAndPurchaseDateAndProductDate(item);
            item.setQuantity(itemInDatabase.getQuantity()+item.getQuantity());
            itemDatabase.updateByBarcodeAndPurchaseDateAndProductDate(item);
        }else {
            itemDatabase.insert(item);
        }
    }

    /**
     * 初始化UI控件
     */
    private void initUIComponent() {

        return_button = ( ImageButton )findViewById(R.id.return_button);
        scan_button = ( ImageButton )findViewById(R.id.scan_button);
        name_edit = ( EditText )findViewById(R.id.item_name_edit);
        cost_price_edit = ( EditText )findViewById(R.id.cost_price_edit);
        sell_price_edit = ( EditText )findViewById(R.id.sell_price_edit);
        quality_date_edit = ( EditText )findViewById(R.id.quality_date_edit);
        quantity_edit = ( EditText )findViewById(R.id.quantity_edit);
        product_year_edit = ( EditText )findViewById(R.id.product_year_edit);
        product_month_edit = ( EditText )findViewById(R.id.product_month_edit);
        product_day_edit = ( EditText )findViewById(R.id.product_day_edit);
        barcode_text = ( TextView )findViewById(R.id.barcode_text);
        clear_button = ( Button )findViewById(R.id.clear_button);
        save_button = ( Button )findViewById(R.id.save_button);

    }

    /**
     * 处理二维码扫描的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == RESULT_OK) {//成功返回条形码
                    String barcode = data.getExtras().getString("result");
                    barcode_text.setText(barcode);
                } else {
                    barcode_text.setText("条码出错");
                }
                break;
        }

        return;
    }
}
