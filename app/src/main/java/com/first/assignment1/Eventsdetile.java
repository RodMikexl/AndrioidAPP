package com.first.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Eventsdetile extends AppCompatActivity {

    int dataId;
    TextView taskname,date,time,location;
    Button back,edit,delete,navigation;
    EDatabaseManager EventInfo;
    ArrayList<String> temTableContent;
    ImageView compulete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventsdetile);

        taskname = (TextView)this.findViewById(R.id.taskName);
        date = (TextView)this.findViewById(R.id.date);
        time = (TextView)this.findViewById(R.id.time);
        location = (TextView)this.findViewById(R.id.eventAddress);

        compulete = (ImageView)this.findViewById(R.id.complete);

        back = (Button)this.findViewById(R.id.Eventdback);
        edit = (Button)this.findViewById(R.id.EventEdit);
        delete = (Button)this.findViewById(R.id.EventDelete);
        navigation = (Button)this.findViewById(R.id.Eventnavigation);

        Intent intent = getIntent();
        String tem = intent.getStringExtra("dataId");
        dataId = Integer.parseInt(tem);

        EventInfo = new EDatabaseManager(Eventsdetile.this);
        EventInfo.openReadable();
        temTableContent = EventInfo.retall(dataId);

        String[] cutString = new String[]{};
        cutString = temTableContent.get(0).split(",");



        int idate = Integer.parseInt(cutString[4]);
        int itime = Integer.parseInt(cutString[5]);
        int year=0,month=0,day=0,hour=0,min=0;
        year = idate/10000;
        month = idate/100%100;
        day = idate%100;
        hour = itime/100;
        min = itime%100;

        String smouth = dealwith(month);
        String sday = dealwith(day);
        String shour = dealwith(hour);

        String sdate = sday+"/"+smouth+"/"+year;
        String stime = shour+":"+min;

        taskname.setText(cutString[1]);
        date.setText(sdate);
        time.setText(stime);
        int jugComplete = Integer.parseInt(cutString[3]);
        if(jugComplete == 0){
            compulete.setImageResource(R.drawable.no);
        }else if (jugComplete == 1){
            compulete.setImageResource(R.drawable.yes);
        }
        location.setText(cutString[2]);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventsdetile.this,Event.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Eventsdetile.this,eventEdit.class);
                String trans = String.valueOf(dataId);
                intent.putExtra("dataId", trans);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean jug = false;
                jug = EventInfo.deleteRow(dataId);
                if(jug){
                    Toast.makeText(getApplicationContext(), "delete success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Eventsdetile.this,Event.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "delete fail", Toast.LENGTH_SHORT).show();
                }


            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mapaddress = location.getText().toString();
                Intent intent = new Intent(Eventsdetile.this, MapsActivity.class);
                intent.putExtra("My address",mapaddress);
                startActivity(intent);
            }
        });

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