package com.first.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class displayFrienddetile extends AppCompatActivity {


    TextView fname,lname,gender,age,address;
    Button back,edit,delete,navigation;
    FDatabaseManager friendInfo;
    ArrayList<String> temTableContent;
    int dataId;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_frienddetile);


        fname = (TextView)this.findViewById(R.id.friendFirstName);
        lname = (TextView)this.findViewById(R.id.friendLastName);
        gender = (TextView)this.findViewById(R.id.friendGender);
        age = (TextView)this.findViewById(R.id.friendAge);
        address = (TextView)this.findViewById(R.id.friendAddress);
        image = (ImageView)this.findViewById(R.id.friendimage);

        back = (Button)this.findViewById(R.id.friendback);
        edit = (Button)this.findViewById(R.id.friendEdit);
        delete = (Button)this.findViewById(R.id.friendDelete);
        navigation = (Button)this.findViewById(R.id.navigation);


        Intent intent = getIntent();
        String tem = intent.getStringExtra("dataId");
        dataId = Integer.parseInt(tem);

        friendInfo = new FDatabaseManager(displayFrienddetile.this);
        friendInfo.openReadable();
        temTableContent = friendInfo.retfull(dataId);

        String[] cutString = new String[]{};
        cutString = temTableContent.get(0).split(",");

        fname.setText(cutString[1]);
        lname.setText(cutString[2]);
        gender.setText(cutString[3]);
        age.setText(cutString[4]);
        address.setText(cutString[5]);
        image.setImageResource(R.drawable.hot);



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(displayFrienddetile.this,Friend.class);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(displayFrienddetile.this,friendEdit.class);
                String trans = String.valueOf(dataId);
                intent.putExtra("dataId", trans);
                startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean jug = false;
                jug = friendInfo.deleteRow(dataId);
                if(jug){
                    Toast.makeText(getApplicationContext(), "delete success", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(displayFrienddetile.this,Friend.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "delete fail", Toast.LENGTH_SHORT).show();
                }

            }
        });

        navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mapaddress = address.getText().toString();
                Intent intent = new Intent(displayFrienddetile.this, MapsActivity.class);
                intent.putExtra("My address",mapaddress);
                startActivity(intent);
            }
        });

    }


}