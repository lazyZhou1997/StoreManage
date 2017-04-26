package scu.edu.storemanage.tools;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import scu.edu.storemanage.R;
import scu.edu.storemanage.item.Item;

import static android.content.ContentValues.TAG;

/**
 * Created by 周秦春 on 17-4-25.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

    //记录手指触碰的EditText的位置
    private int mTouchItemPositon = -1;

    private Context context;
    private int resource;

    public ItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        //获得当前滚入屏幕的商品
        Item item = getItem(position);

        View view;
        ViewHolder viewHolder;

        if (convertView==null){
            view = LayoutInflater.from(context).inflate(resource,null);
            //保存控件
            viewHolder = new ViewHolder();
            viewHolder.itemname_textview = (TextView)view.findViewById(R.id.listview_layout_itemname_textview);
            viewHolder.editText = (EditText)view.findViewById(R.id.listview_layout_item_quantity_edit);
            viewHolder.add_button = (ImageButton)view.findViewById(R.id.listview_layout_add_button);
            viewHolder.sub_button = (ImageButton)view.findViewById(R.id.listview_layout_sub_button);

            //监听哪个EditText曾经获得焦点
            viewHolder.editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //记录下获得焦点的位置
                    Log.d(TAG, "onTouch: 1");
                    mTouchItemPositon = (Integer) view.getTag();
                    return false;
                }
            });

            // 让ViewHolder持有一个TextWathcer，动态更新position来防治数据错乱；不能将position定义成final直接使用，必须动态更新
            viewHolder.mTextWatcher = new MyTextWatcher();
            viewHolder.editText.addTextChangedListener(viewHolder.mTextWatcher);
            viewHolder.updatePosition(position);

            //将ViewHolder存储在view中
            view.setTag(viewHolder);
        }else {
            Log.d(TAG, "getView: 2");
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
            //动态更新TextWathcer的position
            viewHolder.updatePosition(position);
        }

        //给EditText编号
        viewHolder.editText.setTag(position);
        Log.d(TAG, "getView: 3");
        //让曾经获得焦点的EditText再次获得焦点
        if (position==mTouchItemPositon){
            viewHolder.editText.requestFocus();
            viewHolder.editText.setSelection(viewHolder.editText.getText().length());
        }else {

            viewHolder.editText.clearFocus();
        }

        //设置商品名称
        viewHolder.itemname_textview.setText(item.getName());
        //设置商品数量
        viewHolder.editText.setText(item.getQuantity()+"");
        //FIXME

        return view;
    }

    //保存控件
    class ViewHolder{

        TextView itemname_textview;
        ImageButton add_button;
        ImageButton sub_button;
        EditText editText;

        MyTextWatcher mTextWatcher;

        //动态更新TextWathcer的position
        public void updatePosition(int position) {
            mTextWatcher.updatePosition(position);
        }

    }

    class MyTextWatcher implements TextWatcher {
        //由于TextWatcher的afterTextChanged中拿不到对应的position值，所以自己创建一个子类
        private int mPosition;
        private EditText editText;

        public void updatePosition(int position) {
            mPosition = position;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //FIXME
            Log.d(TAG, "afterTextChanged: "+mPosition+s.toString());
        }
    };
}
