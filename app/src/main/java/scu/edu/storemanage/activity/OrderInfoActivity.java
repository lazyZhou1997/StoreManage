package scu.edu.storemanage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.CustomerDatabase;
import scu.edu.storemanage.database.ItemDatabase;
import scu.edu.storemanage.item.Order;

/**
 * Created by 周秦春 on 2017/5/5.
 */

public class OrderInfoActivity extends Activity {

    //UI
    private ImageButton return_button;
    private TextView ID_text;
    private TextView date_text;
    private TextView quantity_text;
    private TextView item_name_text;
    private TextView customer_name_text;

    //接收Order
    private static Order order;
    //接受ItemDatabase对象
    private static ItemDatabase itemDatabase;
    //接受CustomerDatabase对象
    private static CustomerDatabase customerDatabase;

    /**
     * 接收Order
     * @param neworder Order对象
     */
    public static void setOrder(Order neworder){
        order = neworder;
    }

    /**
     * 传入CustomerDatabase对象
     * @param newCustomerDatabase CustomerDatabase对象
     */
    public static void setCustomerDatabase(CustomerDatabase newCustomerDatabase){
        customerDatabase = newCustomerDatabase;
    }

    /**
     * 接受用户的ItemDatabase
     * @param newItemDatabase 接收用户的Itemdatabase
     */
    public static void setItemDatabase(ItemDatabase newItemDatabase){
        itemDatabase = newItemDatabase;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_item_layout);
        initUIComponent();//初始化UI控件

        ID_text.setText(order.getID()+"");
        date_text.setText(order.getDate().toString());
        quantity_text.setText(order.getQuantity()+"");
        //查询商品名字
        String item_name = itemDatabase.searchByItemID(order.getItemID()).getName();
        item_name_text.setText(item_name);

        if (order.getCustomerID()==-1){
            customer_name_text.setText("匿名");
        }else {

            try {
                //查询客户名称
                String customerName = customerDatabase.searchByID(order.getCustomerID()).getName();
                customer_name_text.setText(customerName);
            } catch (Exception e) {
                customer_name_text.setText("未知顾客");
            }
        }

        //监听返回事件
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    /**
     * 初始化UI控件
     */
    private void initUIComponent(){
        return_button = (ImageButton)findViewById(R.id.return_button_in_order_item_layout);
        ID_text=(TextView)findViewById(R.id.ID_text_in_order_item);
        date_text=(TextView)findViewById(R.id.date_text_in_order_item);
        quantity_text=(TextView)findViewById(R.id.quantity_text_in_order_item);
        item_name_text=(TextView)findViewById(R.id.item_name_text_in_order_item);
        customer_name_text=(TextView)findViewById(R.id.customer_name_text_in_order_item);
    }
}
