package scu.edu.storemanage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import scu.edu.storemanage.R;
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

    /**
     * 接收Order
     * @param neworder Order对象
     */
    public static void setOrder(Order neworder){
        order = neworder;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_item_layout);
        initUIComponent();//初始化UI控件

        

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
