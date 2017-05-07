package scu.edu.storemanage.tools;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import scu.edu.storemanage.R;
import scu.edu.storemanage.item.Item;
import scu.edu.storemanage.item.Order;

/**
 * Created by 周秦春 on 2017/5/6.
 */

public class ItemChoseAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private LayoutInflater layoutInflater;

    public ItemChoseAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获得订单对象
        Item item = (Item) getItem(position);

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = layoutInflater.inflate(resource, null);
            viewHolder = new ViewHolder();

            viewHolder.order_date = (TextView) convertView.findViewById(R.id.member_name_text_in_member_listview);
            viewHolder.item_name = (TextView) convertView.findViewById(R.id.member_integral_text_in_member_listview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.item_name.setSelected(true);
        //显示信息
        viewHolder.order_date.setText(item.getProductDate().toString());
        viewHolder.item_name.setText("  商品名： "+item.getName());


        return convertView;
    }


    private class ViewHolder {

        TextView order_date;

        TextView item_name;
    }
}
