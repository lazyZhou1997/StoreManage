package scu.edu.storemanage.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.tools.Date;

/**
 * Created by 周秦春 on 2017/5/6.
 */

public class ItemChangeActivity extends Activity {

    //UI
    private ImageButton return_button;
    private EditText item_name_edit;
    private EditText cost_price_edit;
    private EditText selling_price_edit;
    private EditText quality_date_edit;
    private EditText quantity_edit;
    private EditText product_date_year_edit;
    private EditText product_date_month_edit;
    private EditText product_date_day_edit;
    private TextView barcode_text;
    private Button save_button;
    private Button clear_button;

    //Item对象
    private static Item item;
    //ItemDatabase数据库
    private static ItemDatabase itemDatabase;

    /**
     * 获得商品对象
     * @param newItem 商品对象
     */
    public static void setItem(Item newItem) {
        item = newItem;
    }

    /**
     * 设置商品的数据库对象
     * @param newItemDatabase 商品数据库对象
     */
    public static void setItemDatabase(ItemDatabase newItemDatabase){
        itemDatabase = newItemDatabase;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.item_change_layout);
        initUIComponent();//初始化UI控件
        iniData();//初始化数据

        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //监听清空按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(ItemChangeActivity.this);
                builder.setTitle("清空");
                builder.setMessage("清空所有已经输入的内容！");
                builder.setCancelable(true);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        item_name_edit.setText("");
                        cost_price_edit.setText("");
                        selling_price_edit.setText("");
                        quality_date_edit.setText("");
                        quantity_edit.setText("");
                        product_date_year_edit.setText("");
                        product_date_month_edit.setText("");
                        product_date_day_edit.setText("");
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(ItemChangeActivity.this, "取消清空", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.show();
            }
        });


        //监听保存按钮
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
    }

    /**
     * 初始化UI控件
     */
    private void initUIComponent() {
        return_button = (ImageButton) findViewById(R.id.return_button_in_item_change_layout);
        item_name_edit = (EditText) findViewById(R.id.item_name_edit_in_item_change);
        cost_price_edit = (EditText) findViewById(R.id.cost_price_edit_in_item_change);
        selling_price_edit = (EditText) findViewById(R.id.sell_price_edit_in_item_change);
        quality_date_edit = (EditText) findViewById(R.id.quality_date_edit_in_item_change);
        quantity_edit = (EditText) findViewById(R.id.quantity_edit_in_item_change);
        product_date_year_edit = (EditText) findViewById(R.id.product_year_edit_in_item_change);
        product_date_month_edit = (EditText) findViewById(R.id.product_month_edit_in_item_change);
        product_date_day_edit = (EditText) findViewById(R.id.product_day_edit_in_item_change);
        barcode_text = (TextView) findViewById(R.id.barcode_text_in_item_change);
        save_button = (Button) findViewById(R.id.save_button_in_item_change);
        clear_button = (Button) findViewById(R.id.clear_button_in_item_change);
    }

    /**
     * 初始化数据于EditText和TextView中
     */
    private void iniData(){
        item_name_edit.setText(item.getName());
        cost_price_edit.setText(item.getCostPrice()+"");
        selling_price_edit.setText(item.getSellingPrice()+"");
        quality_date_edit.setText(item.getQualityDate()+"");
        quantity_edit.setText(item.getQuantity()+"");

        //解析Date
        Date date = item.getProductDate();
        product_date_year_edit.setText(date.getYear()+"");
        product_date_month_edit.setText(date.getMonth()+"");
        product_date_day_edit.setText(date.getDay()+"");

    }

    //保存用户输入的数据
    private void saveData(){

        //edit中获取输入数据
        String name = item_name_edit.getText().toString();
        String costPrice = cost_price_edit.getText().toString();
        String sellingPrice = selling_price_edit.getText().toString();
        String qualityDate = quality_date_edit.getText().toString();
        String quantity = quantity_edit.getText().toString();
        String productDate = product_date_year_edit.getText().toString()+"-"+product_date_month_edit.getText().toString()+
                "-"+product_date_day_edit.getText().toString();

        //判断输入是否为空
        if (name.equals("")||costPrice.equals("")||sellingPrice.equals("")||qualityDate.equals("")||
                quantity.equals("")||productDate.equals("")){
            Toast.makeText(this, "请补全输入", Toast.LENGTH_SHORT).show();
            return;
        }

        //设置更新数据
        item.setProductDate(new Date(productDate));
        item.setQualityDate(Integer.parseInt(qualityDate));
        item.setName(name);
        item.setCostPrice(Double.parseDouble(costPrice));
        item.setSellingPrice(Double.parseDouble(sellingPrice));
        item.setQuantity(Double.parseDouble(quantity));

        //根据商品ID更新数据
        itemDatabase.updateByID(item);

        Toast.makeText(this, "商品更新成功", Toast.LENGTH_SHORT).show();
    }

}
