package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import scu.edu.storemanage.R;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 2017/4/28.
 */

public class ChangeMemberInfoActivity extends Activity {

    //UI
    private ImageButton return_button;
    private TextView integral_text;
    private TextView name_edit;
    private EditText phone_edit;
    private EditText address_edit;
    private Button clear_button;
    private Button change_button;

    //用户信息
    private double integral;
    private String name;
    private String phone;
    private String address;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.change_member_layout);
        initUIComponent();//初始化控件
        getInformatiobFromCustomer();//获得用户信息

        //显示顾客信息
        integral_text.setText(integral+"");
        name_edit.setText(name);
        phone_edit.setText(phone);
        address_edit.setText(address);

        //监听清空按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_edit.setText("");
                address_edit.setText("");
                Toast.makeText(ChangeMemberInfoActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
            }
        });

        //监听返回按钮
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //监听保存按钮
        change_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获得输入
                String name = name_edit.getText().toString();
                String phone = phone_edit.getText().toString();
                String address = address_edit.getText().toString();

                //判断是否为空
                if (name.equals("")||phone.equals("")||address.equals("")){
                    Toast.makeText(ChangeMemberInfoActivity.this, "请输入完成信息", Toast.LENGTH_SHORT).show();
                    return;
                }

                //将输入返回上一个Activity
                Intent resultIntent = new Intent();
                resultIntent.putExtra("name",name);
                resultIntent.putExtra("phone",phone);
                resultIntent.putExtra("address",address);
                setResult(RESULT_OK,resultIntent);

                finish();
            }
        });

    }

    /**
     * 初始化控件
     */
    private void initUIComponent() {

        return_button = (ImageButton) findViewById(R.id.return_button_in_changemember_layout);
        integral_text = (TextView) findViewById(R.id.integral_in_changemumber);
        name_edit = (TextView) findViewById(R.id.name_in_changemumber);
        phone_edit = (EditText) findViewById(R.id.phone_number_in_changemumber);
        address_edit = (EditText) findViewById(R.id.address_in_changemumber);
        clear_button = (Button) findViewById(R.id.clear_button_in_changemember);
        change_button = (Button) findViewById(R.id.change_button_in_changemember);
    }

    /**
     * 获得顾客信息
     */
    private void getInformatiobFromCustomer() {

        //数据
        Intent data = getIntent();
        //取出数据
        name = data.getStringExtra("name");
        phone = data.getStringExtra("telNumber");
        address = data.getStringExtra("address");
        integral = data.getDoubleExtra("integral",0);

    }
}
