package scu.edu.storemanage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import scu.edu.storemanage.R;

/**
 * Created by 周秦春on 2017/4/4.
 */

public class MainActivity extends Activity{

    /**
     * 启动主界面
     * @param savedInstanceState 临时保存数据
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_layout);

    }
}
