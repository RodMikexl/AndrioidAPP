package com.first.assignment1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Event extends AppCompatActivity {

    LinearLayout eventButton;

    DatePicker datePicker;
    TimePicker timePicker;
    Button add,cancle,delete,complete,notcomplete,listall,back;
    EditText name,location;
    TextView response;
    EDatabaseManager EventInfo;
    ListView showInfo;
    TableLayout addLayout;
    boolean recInserted;
    EventAdapter eventAdapter;
    EventAdapterCheckBox eventcAdaptercheackbox;
    ArrayList<String> tableContent;
    int cTime = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        EventInfo = new EDatabaseManager(Event.this);
        response = (TextView)findViewById(R.id.responseEvent);
        showInfo = (ListView)findViewById(R.id.eventList);
        delete = (Button)findViewById(R.id.eventdelete_button);
        complete = (Button)findViewById(R.id.listcom);
        notcomplete = (Button)findViewById(R.id.listnotcom);
        listall = (Button)findViewById(R.id.listall);
        addLayout = (TableLayout)findViewById(R.id.eventadd_table);
        add = (Button) findViewById(R.id.eventAdd_button);
        back = (Button) findViewById(R.id.back_button);
        eventButton = (LinearLayout)findViewById(R.id.eventbutton);


        eventButton.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.GONE);
        showInfo.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        back.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Event.this,Event.class);
                startActivity(intent);
            }
        });



        response.setText("All event are shown below.");
//
        List<EventList> listOfEvent = new ArrayList<>();
        EventInfo.openReadable();
        tableContent = EventInfo.retfirstpage();
        listOfEvent = cutString(tableContent);
        eventAdapter = new EventAdapter(this,R.layout.eventlayout,listOfEvent);
        showInfo.setAdapter(eventAdapter);

        showInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView text = (TextView)view.findViewById(R.id.taskId);
                String dataId = text.getText().toString();
                Intent intent = new Intent(Event.this,Eventsdetile.class);
                intent.putExtra("dataId", dataId);
                startActivity(intent);

            }
        });

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String t =  simpleDateFormat.format(date);

    }

    public List<EventList> cutString(ArrayList<String> content){
        List<EventList> list = new ArrayList<>();
        String[] cutString = new String[]{};
        for(int i = 0; i < content.size(); i++) {
            String tem = content.get(i);
            cutString = tem.split(",");
            EventList e = new EventList(Integer.parseInt(cutString[0]),cutString[1],Integer.parseInt(cutString[3]),Integer.parseInt(cutString[4]),Integer.parseInt(cutString[5]));
            list.add(e);
        }
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.eventhome:
                backHome();
                break;
            case R.id.eventinsert:
                insertRec();
                break;
            case R.id.batchDeletion:
                batchDeletion();
                break;
//            case R.id.eventremove:
//                removeRecs();
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    public boolean removeRecs() {
//        EventInfo.clearRecords();
//        response.setText("All Records are removed");
//        showInfo.setAdapter(null);
//        return true;
//    }

    public boolean batchDeletion() {

        eventButton.setVisibility(View.GONE);
        delete.setVisibility(View.VISIBLE);
        addLayout.setVisibility(View.GONE);
        response.setText("this is batch deletion");
        showInfo.setVisibility(View.VISIBLE);

        List<EventList> listOfEvent = new ArrayList<>();
        EventInfo.openReadable();
        tableContent = EventInfo.retfirstpage();
        listOfEvent = cutString(tableContent);
        eventcAdaptercheackbox = new EventAdapterCheckBox(this,R.layout.event_checkbox,listOfEvent);
        showInfo.setAdapter(eventcAdaptercheackbox);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean[] checkboxes = eventcAdaptercheackbox.itemIsChecked();

                for (int i = 0; i < eventcAdaptercheackbox.getCount(); i++) {
                    if (checkboxes[i] == true) {
                        EventList list = eventcAdaptercheackbox.getEventList(i);
                        int num = list.getDateId();
                        EventInfo.deleteRow(num);
                    }
                }
                Intent intent = new Intent(Event.this,Event.class);
                startActivity(intent);
            }
        });



        return true;
    }

    public boolean insertRec() {
        eventButton.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);
        addLayout.setVisibility(View.VISIBLE);
        response.setText("Enter information of the new event");
        showInfo.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);

        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void onClick(View v) {
                name = (EditText)findViewById(R.id.en);
                location = (EditText)findViewById(R.id.lc);
                datePicker = findViewById(R.id.datePicker);
                timePicker = findViewById(R.id.timePicker);

                int mouth,day,hour,min;
                mouth = datePicker.getMonth();
                day = datePicker.getDayOfMonth();
                hour = timePicker.getHour();
                min = timePicker.getMinute();

                String syear = String.valueOf(datePicker.getYear());
                String smouth = dealwith(mouth);
                String sday = dealwith(day);
                String shour = dealwith(hour);
                String smin = String.valueOf(timePicker.getMinute());

                String inputDate = syear+smouth+sday;
                String inputTime = shour+smin;
//                response.setText(String.valueOf(inputTime));
                recInserted = EventInfo.addRow(name.getText().toString(),location.getText().toString(),0,inputDate,inputTime);

                addLayout.setVisibility(View.GONE);
                if (recInserted) {
                    response.setText("The event has been added successfully.");
                }
                else {
                    response.setText("Sorry, errors when inserting to DB");
                }

                EventInfo.close();
                name.setText("");
                location.setText("");
                showInfo.setAdapter(null);
            }
        });

        return true;
    }

//    public void getTime(){
//
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date(System.currentTimeMillis());
//        String t =  simpleDateFormat.format(date);
//        cTime =  Integer.parseInt(t);
////        String t = Integer.toString(cTime);
//    }


    public void completeEvent(View view){
        eventButton.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        addLayout.setVisibility(View.GONE);
        response.setText("this is complete events");
        showInfo.setVisibility(View.VISIBLE);

        List<EventList> listOfEvent = new ArrayList<>();
        EventInfo.openReadable();
        tableContent = EventInfo.retComplete(1);
        listOfEvent = cutString(tableContent);
        eventAdapter = new EventAdapter(this,R.layout.eventlayout,listOfEvent);
        showInfo.setAdapter(eventAdapter);
    }

    public void notCompleteEvent(View view){
        eventButton.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        addLayout.setVisibility(View.GONE);
        response.setText("this is not complete events");
        showInfo.setVisibility(View.VISIBLE);

        List<EventList> listOfEvent = new ArrayList<>();
        EventInfo.openReadable();
        tableContent = EventInfo.retComplete(0);
        listOfEvent = cutString(tableContent);
        eventAdapter = new EventAdapter(this,R.layout.eventlayout,listOfEvent);
        showInfo.setAdapter(eventAdapter);
    }


    public void listAllEvent(View view){
        eventButton.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        addLayout.setVisibility(View.GONE);
        response.setText("this is all events");
        showInfo.setVisibility(View.VISIBLE);

        List<EventList> listOfEvent = new ArrayList<>();
        EventInfo.openReadable();
        tableContent = EventInfo.retfirstpage();
        listOfEvent = cutString(tableContent);
        eventAdapter = new EventAdapter(this,R.layout.eventlayout,listOfEvent);
        showInfo.setAdapter(eventAdapter);
    }


    public boolean backHome() {
        Intent intent = new Intent( Event.this, MainActivity.class);
        startActivity(intent);
        return true;
    }


    public String dealwith(int tem){
        String out = new String();
        if(tem<10){
            out = "0"+tem;
            return out;
        }else
            return String.valueOf(tem);
    }

}