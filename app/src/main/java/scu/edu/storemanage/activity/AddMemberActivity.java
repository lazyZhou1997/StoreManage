package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import scu.edu.storemanage.R;

/**
 * Created by 周秦春 on 2017/4/27.
 */

public class AddMemberActivity extends Activity {

    //UI
    private EditText name_edit;
    private EditText phone_number_edit;
    private EditText address_edit;
    private ImageButton return_button;
    private Button clear_button;
    private Button save_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_member_layout);
        initUIComponent();//初始化控件

        //监听清空按钮
        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_edit.setText("");
                phone_number_edit.setText("");
                address_edit.setText("");
                Toast.makeText(AddMemberActivity.this, "清空成功", Toast.LENGTH_SHORT).show();
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
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获得输入
                String name = name_edit.getText().toString();
                String phone = phone_number_edit.getText().toString();
                String address = address_edit.getText().toString();
                //判断是否为空
                if (name.equals("")||phone.equals("")||address.equals("")){
                    Toast.makeText(AddMemberActivity.this, "请输入完成信息", Toast.LENGTH_SHORT).show();
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
     * 初始化UI控件
     */
    private void initUIComponent() {

        name_edit = (EditText) findViewById(R.id.name_in_addmumber);
        phone_number_edit = (EditText) findViewById(R.id.phone_number_in_addmumber);
        address_edit = (EditText) findViewById(R.id.address_in_addmumber);
        return_button = (ImageButton) findViewById(R.id.return_button_in_addmember_layout);
        clear_button = (Button) findViewById(R.id.clear_button_in_addmember);
        save_button = (Button) findViewById(R.id.save_button_in_addmember);
    }
}
