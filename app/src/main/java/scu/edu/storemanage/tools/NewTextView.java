package scu.edu.storemanage.tools;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by 周秦春 on 2017/5/7.
 */

public class NewTextView extends android.widget.TextView {

    public NewTextView(Context context) {
        super(context);
    }

    public NewTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        return true;
    }

    public NewTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}

