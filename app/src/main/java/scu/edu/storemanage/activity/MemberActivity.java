package scu.edu.storemanage.activity;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import scu.edu.storemanage.R;
import scu.edu.storemanage.item.Customer;
import scu.edu.storemanage.tools.MemberAdapter;

/**
 * Created by asus on 2017/4/11.
 */

public class MemberActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;
    //UI
    private ImageButton return_button;
    private ListView member_listview;
    private Button add_member_button;

    //顾客信息
    ArrayList<Customer> members = new ArrayList<Customer>();
    MemberAdapter membersAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_layout);
        initUIComponent();//初始化控件

        //FIXME
        Customer customer = new Customer("周","17761249642","四川大学",1714.1);
        for (int i= 0;i<10;i++){
            members.add(customer);
        }
        membersAdapter = new MemberAdapter(this,R.layout.member_listview_item_layout,members);
        member_listview.setAdapter(membersAdapter);
    }

    /**
     * 初始化控件
     */
    private void initUIComponent(){
        return_button = (ImageButton)findViewById(R.id.return_button_in_member_layout);
        member_listview=(ListView)findViewById(R.id.member_listview);
        add_member_button=(Button)findViewById(R.id.add_member_in_member_layout);
    }
}
