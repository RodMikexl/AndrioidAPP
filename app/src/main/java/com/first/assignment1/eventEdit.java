package com.first.assignment1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class eventEdit extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePicker;
    int dataId,cheack;
    ArrayList<String> temTableContent;
    Button back,submit;
    EditText taskname,location;
    EDatabaseManager EventInfo;
    RadioButton yes,no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);


        Intent intent = getIntent();
        String tem = intent.getStringExtra("dataId");
        dataId = Integer.parseInt(tem);

        yes = (RadioButton)this.findViewById(R.id.radio_yes);
        no = (RadioButton)this.findViewById(R.id.radio_no);
        yes.setOnClickListener(myRL);
        no.setOnClickListener(myRL);


        taskname = (EditText) this.findViewById(R.id.en);
        location = (EditText) this.findViewById(R.id.LOCATION);

        back = (Button)this.findViewById(R.id.editBack);
        submit = (Button)this.findViewById(R.id.editsubmit);

        EventInfo = new EDatabaseManager(eventEdit.this);
        EventInfo.openReadable();
        temTableContent = EventInfo.retall(dataId);

        String[] cutString = new String[]{};
        cutString = temTableContent.get(0).split(",");


        taskname.setText(cutString[1]);
        location.setText(cutString[2]);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(eventEdit.this,Eventsdetile.class);
                String trans = String.valueOf(dataId);
                intent.putExtra("dataId", trans);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                boolean jug = false;
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


                jug = EventInfo.update(dataId,taskname.getText().toString(),location.getText().toString(),cheack,inputDate,inputTime);

                if(jug){
                    Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(eventEdit.this,Eventsdetile.class);
                    String trans = String.valueOf(dataId);
                    intent.putExtra("dataId", trans);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "update fail", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private View.OnClickListener myRL = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RadioButton rb = (RadioButton)view;
            String tem = rb.getText().toString();
            if(tem.equals("yes")){
                cheack = 1;
            }else if(tem.equals("no")){
                cheack = 0;
            }
        }
    };

    public String dealwith(int tem){
        String out = new String();
        if(tem<10){
            out = "0"+tem;
            return out;
        }else
            return String.valueOf(tem);
    }
}