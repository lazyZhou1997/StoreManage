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
import scu.edu.storemanage.item.Customer;

/**
 * Created by 周秦春 on 2017/4/27.
 */

public class MemberAdapter extends ArrayAdapter<Customer> {

    private Context context;
    private int resource;
    private LayoutInflater layoutInflater;

    public MemberAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //获得当前的顾客信息
        Customer customer = getItem(position);

        ViewHolder viewHolder;
        if (null == convertView) {
            convertView = layoutInflater.inflate(resource, null);
            viewHolder = new ViewHolder();

            viewHolder.member_name = (TextView) convertView.findViewById(R.id.member_name_text_in_member_listview);
            viewHolder.member_integral = (TextView) convertView.findViewById(R.id.member_integral_text_in_member_listview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //显示信息
        viewHolder.member_name.setText(customer.getName());
        viewHolder.member_integral.setText("  积分 "+customer.getIntegral());

        return convertView;
    }


    private class ViewHolder {

        TextView member_name;

        TextView member_integral;
    }
}
