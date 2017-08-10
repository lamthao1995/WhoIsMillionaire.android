package com.example.demoaltp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by nhatcuong1 on 8/23/15.
 */
public class BaseAdapterUsers extends BaseAdapter {
    private ArrayList<User> myUsers = new ArrayList<User>();
    private Context myContext;
    private LayoutInflater myInflater;

    public BaseAdapterUsers(Context context, ArrayList<User> myUsers) {
        this.myContext = context;
        this.myInflater = LayoutInflater.from(this.myContext);
        this.myUsers = myUsers;

    }

    @Override
    public int getCount() {
        return myUsers.size();
    }

    @Override
    public Object getItem(int position) {
        return myUsers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if (convertView == null) {
            convertView = myInflater.inflate(R.layout.item_user_list, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        } else {
            myViewHolder = (MyViewHolder) convertView.getTag();

        }
        User ps = (User) getItem(position);
        myViewHolder.txt_Username.setText(ps.getUsername()+"");
        myViewHolder.txt_tongsotran.setText(ps.getTongsotran()+"");
        myViewHolder.txt_vcoin.setText(ps.getVcoin()+"");
        myViewHolder.txt_sotranthang.setText(ps.getSotranthang()+"");
        myViewHolder.txt_date_join.setText(ps.getDate_join()+"");

        //myViewHolder.tvTitle.setText(ps.getName());
        //myViewHolder.ivIcon.setImageResource(ps.getIntImage());


        return convertView;
    }

    private class MyViewHolder {
        TextView txt_Username,txt_vcoin,txt_sotranthang,txt_tongsotran,txt_date_join;


        public MyViewHolder(View itemView) {
            txt_date_join=(TextView)itemView.findViewById(R.id.txt_datejoin_info);
            txt_sotranthang=(TextView)itemView.findViewById(R.id.txt_sotranthang_info);
            txt_tongsotran=(TextView)itemView.findViewById(R.id.txt_tongsotran_info);
            txt_vcoin=(TextView)itemView.findViewById(R.id.txt_vcoin_info);
            txt_Username=(TextView)itemView.findViewById(R.id.txt_user_info);
        }
    }

}
