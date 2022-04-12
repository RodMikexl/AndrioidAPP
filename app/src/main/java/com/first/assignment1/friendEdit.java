package com.first.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class friendEdit extends AppCompatActivity {

    Spinner gender;
    NumberPicker age;
    int dataId;
    FDatabaseManager friendInfo;
    ArrayList<String> temTableContent;
    Button back,submit;
    EditText fname,lname,address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_edit);


        Intent intent = getIntent();
        String tem = intent.getStringExtra("dataId");
        dataId = Integer.parseInt(tem);

        address = (EditText) this.findViewById(R.id.editAddress);
        fname = (EditText) this.findViewById(R.id.editfistname);
        lname = (EditText) this.findViewById(R.id.editlastname);
        age = (NumberPicker) this.findViewById(R.id.numberPicker);
        gender = (Spinner)this.findViewById(R.id.spinner);
        back = (Button)this.findViewById(R.id.Editdback);
        submit = (Button)this.findViewById(R.id.EditSubmit);

        age.setMinValue(0);
        age.setMaxValue(100);
        age.setWrapSelectorWheel(true);

        friendInfo = new FDatabaseManager(friendEdit.this);
        friendInfo.openReadable();
        temTableContent = friendInfo.retfull(dataId);

        String[] cutString = new String[]{};
        cutString = temTableContent.get(0).split(",");


        fname.setText(cutString[1]);
        lname.setText(cutString[2]);
        age.setValue(Integer.parseInt(cutString[4]));
        address.setText(cutString[5]);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(friendEdit.this,displayFrienddetile.class);
                String trans = String.valueOf(dataId);
                intent.putExtra("dataId", trans);
                startActivity(intent);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                boolean jug = false;
                jug = friendInfo.update(dataId,fname.getText().toString(),lname.getText().toString(), gender.getSelectedItem().toString(),
                        age.getValue(), address.getText().toString());
                if(jug){
                    Toast.makeText(getApplicationContext(), "update success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(friendEdit.this,displayFrienddetile.class);
                    String trans = String.valueOf(dataId);
                    intent.putExtra("dataId", trans);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "update fail", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}