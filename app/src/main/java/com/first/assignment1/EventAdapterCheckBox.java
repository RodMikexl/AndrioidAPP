package com.first.assignment1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapterCheckBox extends ArrayAdapter<EventList> {

    private int resource;
    private ViewHolder viewHolder;
    private Context context;
    private boolean[] mItemChecked;
    public List<EventList> arrayList;


    public EventAdapterCheckBox(Context context, int resource, List<EventList> objects) {
        super(context, resource, objects);
        this.arrayList = objects;
        this.context = context;
        this.resource = resource;
        mItemChecked = new boolean[arrayList.size()];
        for (int i=0; i<arrayList.size(); i++){
            mItemChecked[i] = false;
        }
    }


private class ViewHolder {
    TextView name, date ,id,time;
    ImageView image;
    CheckBox eventCheckBox;
}

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        EventList eventList = getItem(position);
        View view;
        viewHolder = new ViewHolder();
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, parent, false);
            viewHolder.name = (TextView) view.findViewById(R.id.taskName);
            viewHolder.date = (TextView) view.findViewById(R.id.date);
            viewHolder.id = (TextView) view.findViewById(R.id.taskId);
            viewHolder.time = (TextView) view.findViewById(R.id.time);
            viewHolder.image = (ImageView) view.findViewById(R.id.icon);
            viewHolder.eventCheckBox = (CheckBox) view.findViewById(R.id.eventcheckBox);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.name.setText(eventList.getTaskName());
        viewHolder.date.setText(eventList.getDate());
        viewHolder.id.setText(String.valueOf(eventList.getDateId()));
        viewHolder.time.setText(eventList.getTime());
        viewHolder.eventCheckBox.setChecked(mItemChecked[position]);

        viewHolder.eventCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((CheckBox)view).isChecked()) {
                    mItemChecked[position] = true;
                }
                else
                    mItemChecked[position]=false;
            }
        });

        String s = eventList.getStatus();
        if (s.startsWith("complete")) {
            viewHolder.image.setImageResource(R.drawable.yes);
        } else {
            viewHolder.image.setImageResource(R.drawable.no);
        }

        return view;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    public boolean[] itemIsChecked(){
        return mItemChecked;
    }
    public EventList getEventList(int pos){
        EventList retarrlist = arrayList.get(pos);
        return retarrlist;
    }

}