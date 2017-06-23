package com.example.ubuntu.whyyes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by zakovacr on 23.6.2017.
 */

public class CodesAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<CodeToVerify> mDataSource;

    public CodesAdapter(Context context, ArrayList<CodeToVerify> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //1
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    //2
    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    //3
    @Override
    public long getItemId(int position) {
        return position;
    }

    //4
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get view for row item
        View rowView = mInflater.inflate(R.layout.list_item_code, parent, false);

        // Get title element
        TextView titleTextView = (TextView) rowView.findViewById(R.id.code_list_title);

        // Get detail element
        TextView detailTextView = (TextView) rowView.findViewById(R.id.code_list_detail);

        // Get thumbnail element
        ImageView thumbnailImageView = (ImageView) rowView.findViewById(R.id.code_list_thumbnail);

        // 1
        CodeToVerify codeToVerify = (CodeToVerify) getItem(position);

// 2
        titleTextView.setText(codeToVerify.codeNumber);
        detailTextView.setText(codeToVerify.store);

// 3
        if (codeToVerify.status.contains("ok")){
            thumbnailImageView.setImageResource(R.mipmap.ok);
        }



        return rowView;
    }
}
