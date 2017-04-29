package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.CustomerDatabase;
import scu.edu.storemanage.item.Customer;
import scu.edu.storemanage.tools.MemberAdapter;

/**
 * Created by asus on 2017/4/11.
 */

public class MemberActivity extends Activity {

    //该用户下的数据库
    private SQLiteDatabase database;
    //获得用户数据库
    private CustomerDatabase customerDatabase;

    //UI
    private ImageButton return_button;
    private ListView member_listview;
    private Button add_member_button;

    //顾客信息
    ArrayList<Customer> members;
    MemberAdapter membersAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.member_layout);
        initUIComponent();//初始化控件

        //获得该用户下的数据库
        database = MainFunctionActivity.getDatabase();
        if (database == null) {
            Toast.makeText(this, "读取数据库失败", Toast.LENGTH_SHORT).show();
            finish();
        }
        customerDatabase = new CustomerDatabase(database);

        //显示数据库中所有会员信息
        //查找数据库中
        members = customerDatabase.searchAllCustomer();

        //放入listview中
        if (null != members) {
            membersAdapter = new MemberAdapter(this, R.layout.member_listview_item_layout, members);
            member_listview.setAdapter(membersAdapter);
        }

        //监听增加会员按钮
        add_member_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddMember = new Intent(MemberActivity.this, AddMemberActivity.class);
                startActivityForResult(intentAddMember, 2);
            }
        });

        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //监听listview的点击
        member_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //被点击的会员
                Customer customer = members.get(i);
                //将数据传给下一个app
                Intent changememberIntent = new Intent(MemberActivity.this,ChangeMemberInfoActivity.class);
                changememberIntent.putExtra("name",customer.getName());
                changememberIntent.putExtra("integral",customer.getIntegral());
                changememberIntent.putExtra("telNumber",customer.getTelNumber());
                changememberIntent.putExtra("address",customer.getAddress());
                //启动下一个Activity
                startActivityForResult(changememberIntent,4);
            }
        });
    }

    /**
     * 初始化控件
     */
    private void initUIComponent() {
        return_button = (ImageButton) findViewById(R.id.return_button_in_member_layout);
        member_listview = (ListView) findViewById(R.id.member_listview);
        add_member_button = (Button) findViewById(R.id.add_member_in_member_layout);
    }

    /**
     * 处理返回结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 2:
                if (RESULT_OK == resultCode) {
                    //获得数据
                    String name = data.getStringExtra("name");
                    String phone = data.getStringExtra("phone");
                    String address = data.getStringExtra("address");

                    //储存数据
                    //判断是否已经存在该数据
                    customerDatabase = new CustomerDatabase(database);
                    if (null != customerDatabase.searchByName(name)) {//如果已经有该会员了
                        Toast.makeText(this, "已经存在该会员了", Toast.LENGTH_SHORT).show();
                        return;
                    } else {//不存在该会员
                        Customer customer = new Customer(-1, name, phone, address, 0);
                        //插入数据库
                        customerDatabase.insert(customer);
                        Toast.makeText(this, "添加会员 " + name + " 成功", Toast.LENGTH_SHORT).show();
                        //刷新数据
                        members.add(customer);
                        //刷新通知
                        membersAdapter.notifyDataSetChanged();
                    }
                }else{
                    Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case 4:
                if (RESULT_OK == resultCode) {
                    //获得数据
                    String name = data.getStringExtra("name");
                    String phone = data.getStringExtra("phone");
                    String address = data.getStringExtra("address");

                    //组装数据
                    Customer customer = new Customer(-1,name,phone,address,0);
                    //更新数据库中数据
                    customerDatabase.updateByName(customer);
                    //从数据库中重新获取数据
                    members.clear();
                    members.addAll(customerDatabase.searchAllCustomer());
                    membersAdapter.notifyDataSetChanged();

                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                Toast.makeText(this, "添加失败", Toast.LENGTH_SHORT).show();

        }

        return;
    }
}
