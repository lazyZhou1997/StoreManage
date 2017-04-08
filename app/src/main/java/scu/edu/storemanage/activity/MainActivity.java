package scu.edu.storemanage.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import scu.edu.storemanage.R;
import scu.edu.storemanage.database.MySQLiteOpenHelper;
import scu.edu.storemanage.database.UserDatabase;
import scu.edu.storemanage.item.User;

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

    //保存密码
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        initUIComponent();
        //记住密码和帐号的显示
        rememberAccountAndPassword();

        //登录
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //启动到注册界面
        sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signIntent = new Intent(MainActivity.this, SignInActivity.class);
                startActivityForResult(signIntent, 1);
            }
        });


    }

    /**
     * 记住密码和帐号的显示
     */
    public void rememberAccountAndPassword() {
        //查看是否有记住密码以及记住账户
        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        //是否记住账户
        boolean remember_account = sharedPreferences.getBoolean("remember_account", false);
        //是否记住密码
        boolean remember_password = sharedPreferences.getBoolean("remember_password", false);

        //如果记住账户
        if (remember_account) {
            //将账户写入输入框
            String account = sharedPreferences.getString("account", "");
            account_edit.setText(account);
            //设置复选框记住帐号为选中
            acount_check.setChecked(true);

            //如果记住密码
            if (remember_password) {
                //将密码写入输入框
                String password = sharedPreferences.getString("password", "");
                password_edit.setText(password);
                //设置复选框记住密码为选中
                password_check.setChecked(true);
            }
        }
    }

    /**
     * 登录并保存信息
     */
    public void login(){

        //查找数据库中是否有匹配项
        //获得数据库
        MySQLiteOpenHelper myHelper = new MySQLiteOpenHelper(MainActivity.this, "USER.db", null, 1, MySQLiteOpenHelper.USER);
        SQLiteDatabase db = myHelper.getWritableDatabase();

        //对数据库进行操作的类
        UserDatabase userDatabase = new UserDatabase(db);

        //获得用户输入
        String account = account_edit.getText().toString();//账户
        String password = password_edit.getText().toString();//密码
        boolean remember_account = acount_check.isChecked();//记住帐号是否被选中
        boolean remember_password = password_check.isChecked();//记住密码是否被选中

        //判断输入的值是否为空
        if (account.equals("") || password.equals("")) {
            Toast.makeText(MainActivity.this, "请输入完整信息", Toast.LENGTH_SHORT).show();
            return;
        }

        //从数据库中获得信息
        User user = userDatabase.searchByAccount(account);

        //登录
        if (user!=null&&user.getPassword().equals(password)){

            //将登录信息保存到sharePreference中
            editor = sharedPreferences.edit();
            //如果记住帐号被选中
            if (remember_account){
                editor.putBoolean("remember_account",true);//保存复选框状态
                editor.putString("account",account);//保存帐号

                //如果记住密码被选中
                if (remember_password){
                    editor.putBoolean("remember_password",true);//保存记住密码复选框状态
                    editor.putString("password",password);//保存密码
                }
            }else{
                //没有选中，清除信息
                editor.clear();
            }
            //提交保存信息
            editor.commit();

            Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            //跳转界面
            Intent intentFunction =new Intent(MainActivity.this,MainFunctionActivity.class);
            startActivity(intentFunction);
            finish();
        }else {
            //登录失败
            Toast.makeText(MainActivity.this, "账户或密码错误", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 拿到所有控件
     */
    private void initUIComponent() {

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

    /**
     * 处理注册界面返回的注册帐号信息
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    account_edit.setText(data.getStringExtra("account"));
                    password_edit.setText(data.getStringExtra("password"));
                }
        }
    }
}
