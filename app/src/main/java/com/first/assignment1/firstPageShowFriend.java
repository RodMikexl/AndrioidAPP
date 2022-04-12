package com.first.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class firstPageShowFriend extends ArrayAdapter<friendFirstPage> {


    int resource;
    private ViewHolder viewHolder;

    public firstPageShowFriend(Context context, int resource, List<friendFirstPage> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    private class ViewHolder {
        TextView name,id;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        friendFirstPage friend = getItem(position);
        View view;
        viewHolder = new ViewHolder();
        if (convertView == null) {
        view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        viewHolder.name = (TextView) view.findViewById(R.id.friendName);
        viewHolder.id = (TextView) view.findViewById(R.id.friendId);
        view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(friend.getName());
        viewHolder.id.setText(String.valueOf(friend.getId()));
        return view;
    }
}