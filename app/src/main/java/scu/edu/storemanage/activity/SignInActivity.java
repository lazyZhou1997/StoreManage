package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.MySQLiteOpenHelper;
import scu.edu.storemanage.database.UserDatabase;
import scu.edu.storemanage.item.User;

/**
 * Created by 周秦春 on 2017/4/6.
 */

public class SignInActivity extends Activity implements View.OnClickListener {

    //控件
    private EditText account_edit;
    private EditText password_edit;
    private EditText phonenumber_edit;
    private Button sign_in_button;


    /**
     * 注册界面
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sign_layout);

        initUIComponent();//拿到UI控件id

        sign_in_button.setOnClickListener(this);

    }

    /**
     * 初始化UI控件，拿到UI控件id
     */
    private void initUIComponent() {
        account_edit = (EditText) findViewById(R.id.sign_account_edit);
        password_edit = (EditText) findViewById(R.id.sign_password_edit);
        phonenumber_edit = (EditText) findViewById(R.id.sign_phone_edit);
        sign_in_button = (Button) findViewById(R.id.button);
    }

    /**
     * 响应按钮监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        //获得用户输入
        String account = account_edit.getText().toString();
        String password = password_edit.getText().toString();
        String phonenumber = phonenumber_edit.getText().toString();

        //判断输入的值是否为空
        User user;//用户对象，封装输入信息
        if (account.equals("") || password.equals("") || phonenumber.equals("")) {
            Toast.makeText(SignInActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            return;
        } else {
            user = new User(account, password, phonenumber);//创建一个用户对象
        }

        //查找数据库中是否有匹配项
        //获得数据库
        MySQLiteOpenHelper myHelper = new MySQLiteOpenHelper(SignInActivity.this, "USER.db", null, 1, MySQLiteOpenHelper.USER);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        //对数据库进行操作的类
        UserDatabase userDatabase = new UserDatabase(db);

        //看看user是否已经存在
        if (userDatabase.exsit(user)) {
            Toast.makeText(SignInActivity.this, "用户已经存在", Toast.LENGTH_SHORT).show();
            return;
        }

        //注册
        userDatabase.insert(user);
        Toast.makeText(SignInActivity.this, "注册成功", Toast.LENGTH_SHORT).show();

        //返回值给上一个界面
        Intent resultIntent = new Intent();
        //组装数据
        resultIntent.putExtra("account",user.getAccount());
        resultIntent.putExtra("password",user.getPassword());
        //返回数据
        setResult(RESULT_OK,resultIntent);

        //结束
        finish();
    }
}
