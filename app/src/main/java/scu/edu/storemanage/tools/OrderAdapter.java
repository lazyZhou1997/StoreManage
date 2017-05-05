package scu.edu.storemanage.tools;

import android.content.Context;
import android.support.annotation.IdRes;
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
import scu.edu.storemanage.item.Order;

/**
 * Created by asus on 2017/5/2.
 */

public class OrderAdapter extends ArrayAdapter {

    private Context context;
    private int resource;
    private LayoutInflater layoutInflater;

    public OrderAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获得订单对象
        Order order = (Order) getItem(position);

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = layoutInflater.inflate(resource, null);
            viewHolder = new ViewHolder();

            viewHolder.order_date = (TextView) convertView.findViewById(R.id.member_name_text_in_member_listview);
            viewHolder.price = (TextView) convertView.findViewById(R.id.member_integral_text_in_member_listview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //显示信息
        viewHolder.order_date.setText(order.getDate().toString());
        viewHolder.price.setText("  获利 "+order.getProfit());

        return convertView;
    }


    private class ViewHolder {

        TextView order_date;

        TextView price;
    }
}
