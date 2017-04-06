package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import scu.edu.storemanage.R;

/**
 * Created by 周秦春on 2017/4/4.
 */

public class MainActivity extends Activity {

    //UI
    private EditText account_edit;//帐号输入
    private EditText password_edit;//密码输入
    private CheckBox acount_check;//记住帐号选中
    private CheckBox password_check;//记住密码选中
    private Button login_button;//登录按钮
    private Button sign_in_button;//注册按钮
    private Button search_password_button;//找回密码按钮

    /**
     * 启动主界面
     *
     * @param savedInstanceState 临时保存数据
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);

        //拿到所有控件
        initUICompoment();

        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = new Intent(MainActivity.this,SignInActivity.class);
                startActivityForResult(signIntent,1);
            }
        });
    }

    /**
     * 拿到所有控件
     */
    private void initUICompoment() {

        //编辑框
        account_edit = (EditText) findViewById(R.id.account_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);

        //复选框
        acount_check = (CheckBox) findViewById(R.id.rem_account_check);
        password_check = (CheckBox) findViewById(R.id.rem_password_check);

        //按钮
        login_button = (Button) findViewById(R.id.log_button);
        sign_in_button = (Button) findViewById(R.id.sign_button);
        search_password_button = (Button) findViewById(R.id.forget_button);

        return;
    }
}
