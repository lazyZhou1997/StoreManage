package scu.edu.storemanage.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import scu.edu.storemanage.R;

/**
 * Created by 周秦春 on 2017/4/8.
 */

public class MainFunctionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_function_layout);
    }
}
