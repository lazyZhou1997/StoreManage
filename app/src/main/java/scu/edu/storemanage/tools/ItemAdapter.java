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

import java.util.ArrayList;
import java.util.List;

import scu.edu.storemanage.R;
import scu.edu.storemanage.activity.SellItemActivity;
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
    private ArrayList<Item> orderItems;//订单列表

    public ItemAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Item> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        orderItems = (ArrayList<Item>) objects;
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

            //传入viewHolder方便扯着EditText
            viewHolder.add_button.setTag(viewHolder);
            viewHolder.sub_button.setTag(viewHolder);


            //监听增加按钮
            viewHolder.add_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = ((ViewHolder)view.getTag()).editText;//获取输入框
                    double quantity;//商品数量
                    String editString = editText.getText().toString();
                    if (editString.equals("")){
                        quantity = 1;
                        editText.setText(""+quantity);
                    }else {
                        quantity = Double.parseDouble(editString);
                        editText.setText((quantity+1)+"");
                    }

                    //显示总价：
                    double totalPrice = 0;
                    for (Item it :
                            orderItems) {
                        totalPrice += it.countTotalSellingPrice();
                    }
                    SellItemActivity.getTotal_price_textview().setText(totalPrice+" "+"元");
                }
            });

            //监听减少按钮
            viewHolder.sub_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText editText = ((ViewHolder)view.getTag()).editText;//获取输入框
                    double quantity;//商品数量
                    String editString = editText.getText().toString();
                    if (editString.equals("")){
                        Toast.makeText(context, "不能小于0", Toast.LENGTH_SHORT).show();
                    }else {
                        quantity = Double.parseDouble(editString);
                        //保证数量不会小于0
                        if (quantity==0){
                            Toast.makeText(context, "不能小于0", Toast.LENGTH_SHORT).show();
                        }else {
                            editText.setText((quantity-1)+"");

                            //显示总价：
                            double totalPrice = 0;
                            for (Item it :
                                    orderItems) {
                                totalPrice += it.countTotalSellingPrice();
                            }
                            SellItemActivity.getTotal_price_textview().setText(totalPrice+" "+"元");
                        }
                    }
                }
            });


            //监听哪个EditText曾经获得焦点
            viewHolder.editText.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //记录下获得焦点的位置
                    mTouchItemPositon = (Integer) view.getTag();
                    return false;
                }
            });

            // 让ViewHolder持有一个TextWathcer，动态更新position来防治数据错乱；不能将position定义成final直接使用，必须动态更新
            viewHolder.mTextWatcher = new MyTextWatcher();
            viewHolder.editText.addTextChangedListener(viewHolder.mTextWatcher);
            viewHolder.mTextWatcher.editText = viewHolder.editText;
            viewHolder.updatePosition(position);

            //将ViewHolder存储在view中
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
            //动态更新TextWathcer的position
            viewHolder.updatePosition(position);
        }

        //给EditText编号
        viewHolder.editText.setTag(position);
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


    //监听EditText中值发生改变
    class MyTextWatcher implements TextWatcher {
        //由于TextWatcher的afterTextChanged中拿不到对应的position值，所以自己创建一个子类
        private int mPosition;
        EditText editText;

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

            //商品数量
            double quantity;
            String editText = s.toString();//获得修改后的文本值
            //如果是空，商品数量直接为0
            if (editText.equals("")){
                quantity = 0;
            }else{
                quantity = Double.parseDouble(editText);
            }
            Item item = orderItems.remove(mPosition);//去出要修改的商品对象。
            item.setQuantity(quantity);//修改数量
            orderItems.add(item);//修改数量
            this.editText.removeTextChangedListener(this);
            this.editText.setSelection(editText.length());
            this.editText.addTextChangedListener(this);
            //notifyDataSetChanged();//通知数据已经修改

            //显示总价：
            double totalPrice = 0;
            for (Item it :
                    orderItems) {
                totalPrice += it.countTotalSellingPrice();
            }
            SellItemActivity.getTotal_price_textview().setText(totalPrice+" "+"元");
        }
    }

}
