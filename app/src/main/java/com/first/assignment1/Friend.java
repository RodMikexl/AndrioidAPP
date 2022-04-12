package com.first.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Friend extends AppCompatActivity {

    ImageView image;
    Button add,cancle,delete,back;
    EditText fn,ln,address;
    TextView response;
    FDatabaseManager friendInfo;
    ListView showInfo;
    TableLayout addLayout;
    boolean recInserted;
    FriendAdapter fadapter;
    firstPageShowFriend firstFAdapter;
    ArrayList<String> tableContent;
    ArrayList<String> temTableContent;
    Spinner gender;
    NumberPicker age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        friendInfo = new FDatabaseManager(Friend.this);
        response = (TextView)findViewById(R.id.response);
        showInfo = (ListView)findViewById(R.id.friendList);
        delete = (Button)findViewById(R.id.delete_button);
        addLayout = (TableLayout)findViewById(R.id.add_table);
        add = (Button) findViewById(R.id.add_button);
        back = (Button) findViewById(R.id.back_button);
        image = (ImageView)findViewById(R.id.friendImage);
        age = (NumberPicker) findViewById(R.id.numberPicker);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Friend.this,Friend.class);
                startActivity(intent);
            }
        });


        age.setMinValue(0);
        age.setMaxValue(100);
        age.setWrapSelectorWheel(true);
        age.setValue(20);


        addLayout.setVisibility(View.GONE);
        showInfo.setVisibility(View.VISIBLE);
        delete.setVisibility(View.GONE);
        back.setVisibility(View.GONE);

        friendInfo.openReadable();
        temTableContent = friendInfo.retFFriend();
        List<friendFirstPage> listOfFriend = new ArrayList<>();
        listOfFriend = cutString(temTableContent);


        response.setText("All friends are shown below.");

        firstFAdapter = new firstPageShowFriend(this,R.layout.rowlayout,listOfFriend);
        showInfo.setAdapter(firstFAdapter);

        showInfo.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {

                TextView text = (TextView)v.findViewById(R.id.friendId);
                String dataId = text.getText().toString();
                Intent intent = new Intent(Friend.this,displayFrienddetile.class);
                intent.putExtra("dataId", dataId);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), item + " selected", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friend_menu, menu);
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
            case R.id.home:
                backHome();
                break;
            case R.id.insert:
                insertRec();
                break;
//            case R.id.remove:
//                removeRecs();
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

//    public boolean removeRecs() {
//        friendInfo.clearRecords();
//        response.setText("All Records are removed");
//        showInfo.setAdapter(null);
//        return true;
//    }

    public boolean insertRec() {

        delete.setVisibility(View.GONE);
        addLayout.setVisibility(View.VISIBLE);
        response.setText("Enter information of the new friend");
        showInfo.setVisibility(View.GONE);
        back.setVisibility(View.VISIBLE);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photo = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivity(photo);
//                startActivityForResult(photo, RESULT_PICK_IMAGE);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                fn = (EditText)findViewById(R.id.fn);
                ln = (EditText)findViewById(R.id.ln);
                gender = (Spinner)findViewById(R.id.spinner);
                address = (EditText)findViewById(R.id.address);
                image = (ImageView)findViewById(R.id.friendImage);

                recInserted = friendInfo.addRow(fn.getText().toString(),ln.getText().toString(), gender.getSelectedItem().toString(),
                        age.getValue(), address.getText().toString());

                addLayout.setVisibility(View.GONE);
                if (recInserted) {
                    response.setText("The friend has been added successfully.");
                }
                else {
                    response.setText("Sorry, errors when inserting to DB");
                }

                friendInfo.close();
                fn.setText("");
                ln.setText("");
                address.setText("");
                image.setImageResource(0);
                showInfo.setAdapter(null);
            }
        });

        return true;
    }

    public boolean backHome() {
        Intent intent = new Intent( Friend.this, MainActivity.class);
        startActivity(intent);

        return true;
    }



    public List<friendFirstPage> cutString(ArrayList<String> content){

        List<friendFirstPage> list = new ArrayList<>();
        String[] cutString = new String[]{};
        for(int i = 0; i < content.size(); i++) {
            String tem = content.get(i);
            cutString = tem.split(",");
            friendFirstPage e = new friendFirstPage(cutString[0],cutString[1],Integer.parseInt(cutString[2]));
            list.add(e);
        }
        return list;
    }

}