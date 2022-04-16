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


public class FriendAdapter extends ArrayAdapter<String> {
    private Context context;
    int resource;
    public ArrayList<String> arrayList;
    private boolean[] mItemChecked;
    ViewHolder viewHolder;

    public FriendAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        this.arrayList = objects;
        this.context = context;
        this.resource = resource;
        mItemChecked = new boolean[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            mItemChecked[i] = false;
        }
    }

    private class ViewHolder
    {
        TextView name;
        ImageView image;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


}

