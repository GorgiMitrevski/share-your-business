package com.example.proektdva;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Vnesuvanje extends AppCompatActivity {


    ArrayList<String> selection = new ArrayList<String>();
    public String checkB_finalSelection = "";

    CheckBox servis;
    CheckBox zabava;
    CheckBox industrija;
    CheckBox edukacija;

    //
    ListView ll;
    DatabaseHelper databaseHelper;
    ArrayList<Item> arrayList;
//    MyAdapter myAdapter;
    Button buttonSave;
    EditText editIme, editAdresa, editTelefon, editWebsajt, editEmail, editLatitude, editLongitude;

    //
    private ImageButton button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vnesuvanje);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo);

        //////////////CB
        servis = (CheckBox) findViewById(R.id.Services);
        zabava = (CheckBox) findViewById(R.id.Fun);
        industrija = (CheckBox) findViewById(R.id.Industry);
        edukacija = (CheckBox) findViewById(R.id.Education);

        //////////////ET
        editIme = (EditText) findViewById(R.id.Ime);
        editAdresa = (EditText) findViewById(R.id.Adresa);
        editTelefon = (EditText) findViewById(R.id.Telefon);
        editWebsajt = (EditText) findViewById(R.id.WebSite);
        editEmail = (EditText) findViewById(R.id.Email);
        editLatitude = (EditText) findViewById(R.id.Latitude);
        editLongitude = (EditText) findViewById(R.id.Longitude);


        ////////////img clicks////////////////
        button = findViewById(R.id.ServicesImageBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openServicesScreen();
            }
        });
        button = findViewById(R.id.FunImageBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFunScreen();
            }
        });
        button = findViewById(R.id.IndustryImageBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIndustryScreen();
            }
        });
        button = findViewById(R.id.EducationImageBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEducationScreen();
            }
        });
        //////////////////////////////////

        buttonSave = (Button) findViewById(R.id.btnSave);

//////////////
        ll = (ListView) findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();
//////////////
        AddData();

    }

    ////////////////////////////addinDB
    public void AddData(){
        buttonSave.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted = databaseHelper.insertData(
                                editIme.getText().toString(),
                                editAdresa.getText().toString(),
                                editTelefon.getText().toString(),
                                editWebsajt.getText().toString(),
                                editEmail.getText().toString(),
                                editLatitude.getText().toString(),
                                editLongitude.getText().toString(),
                                checkB_finalSelection
                        );

                        if(isInserted == true) {
                            Toast.makeText(Vnesuvanje.this, "Data Inserted", Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(Vnesuvanje.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    //////////////
    public void selectItem(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.Services:
                if(checked)
                {
                    selection.add("Services");
                    System.out.println(selection);
                }
                else if(!checked)
                {
                    selection.remove("Services");
                    System.out.println(selection);
                }
                break;
            case R.id.Fun:
                if(checked)
                {
                    selection.add("Fun");
                    System.out.println(selection);
                }
                else if(!checked)
                {
                    selection.remove("Fun");
                    System.out.println(selection);
                }
                break;
            case R.id.Industry:
                if(checked)
                {
                    selection.add("Industry");
                    System.out.println(selection);
                }
                else if(!checked)
                {
                    selection.remove("Industry");
                    System.out.println(selection);
                }
                break;
            case R.id.Education:
                if(checked)
                {
                    selection.add("Education");
                    System.out.println(selection);
                }
                else if(!checked)
                {
                    selection.remove("Education");
                    System.out.println(selection);
                }
                break;
        }
        checkB_finalSelection = selection.toString();
    }

    public void openServicesScreen() {
        Intent intent = new Intent(this, ServicesScreen.class);
        startActivity(intent);
    }

    public void openFunScreen() {
        Intent intent = new Intent(this, FunScreen.class);
        startActivity(intent);
    }

    public void openIndustryScreen() {
        Intent intent = new Intent(this, IndustryScreen.class);
        startActivity(intent);
    }

    public void openEducationScreen() {
        Intent intent = new Intent(this, EducationScreen.class);
        startActivity(intent);
    }

}
