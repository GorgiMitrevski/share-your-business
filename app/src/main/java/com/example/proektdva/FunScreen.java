package com.example.proektdva;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FunScreen extends AppCompatActivity {

    float x1, x2, y1, y2;
    private ImageButton button;

    //LV
    ListView ll;
    DatabaseHelper databaseHelper;
    ArrayList<Item> arrayList;
    MyAdapter myAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fun_screen);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.logo);
        //////////////
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

        //ListView
        ll = (ListView) findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);
        arrayList = new ArrayList<>();

        loadDataInListView();

        search();
        /////////////////////////START SWIPE LEFT-RIGHT ON LISTVIEW/////////////
        SwipeListViewTouchListener touchListener =
                new SwipeListViewTouchListener(
                        ll,
                        new SwipeListViewTouchListener.OnSwipeCallback() {
                            @Override
                            public void onSwipeLeft(ListView listView, int[] reverseSortedPositions) {
                                Intent i = new Intent(FunScreen.this, IndustryScreen.class);
                                startActivity(i);
                            }
                            @Override
                            public void onSwipeRight(ListView listView, int[] reverseSortedPositions) {
                                Intent i = new Intent(FunScreen.this, ServicesScreen.class);
                                startActivity(i);
                            }
                        },
                        true, // example : left action = dismiss
                        false); // example : right action without dismiss animation
        ll.setOnTouchListener(touchListener);
        /////////////////////////////////END SWIPE LEFT-RIGHT ON LISTVIEW///////////////////////////////////////////////

    }

    //////////////
    public void search(){
        EditText theFilter = (EditText) findViewById(R.id.searchFilter);
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (FunScreen.this).myAdapter.getFilter().filter(s);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }//////////////

    public void loadDataInListView(){
        arrayList = databaseHelper.getDataFun();
        myAdapter = new MyAdapter(this, arrayList);
        ll.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    /////////Navigate to VNESUVANJE screen/////////////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item1) {
            Intent intent = new Intent(this, Vnesuvanje.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }//////////////


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

    //////////////Swipe
    public boolean onTouchEvent(MotionEvent touchEvent){
        switch (touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 > x2){
                    Intent i = new Intent(FunScreen.this, IndustryScreen.class);
                    startActivity(i);
                } else if(x1 < x2){
                    Intent i = new Intent(FunScreen.this, ServicesScreen.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }//////////////

}