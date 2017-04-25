package scu.edu.storemanage.tools;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import scu.edu.storemanage.R;
import scu.edu.storemanage.item.Item;

/**
 * Created by 周秦春 on 17-4-25.
 */

public class ItemAdapter extends ArrayAdapter<Item> {

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

            //将ViewHolder存储在view中
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();//重新获取ViewHolder
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
    }
}
