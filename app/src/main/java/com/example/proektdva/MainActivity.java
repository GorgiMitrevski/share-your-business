package com.example.proektdva;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    float x1, x2, y1, y2;
    private ImageButton button;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        //////////////

    }

    /////////Navigate to Vnesuvanje Screen/////////////////////////////
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

    //////////////Swipe nadvor od LV //////////////
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
                    Intent i = new Intent(MainActivity.this, ServicesScreen.class);
                    startActivity(i);
                } else if(x2 > x1){
                    Intent i = new Intent(MainActivity.this, EducationScreen.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }


    public void openServicesScreen(){
        Intent intent = new Intent(this, ServicesScreen.class);
        startActivity(intent);
    }
    public void openFunScreen(){
        Intent intent = new Intent(this, FunScreen.class );
        startActivity(intent);
    }
    public void openIndustryScreen(){
        Intent intent = new Intent(this, IndustryScreen.class );
        startActivity(intent);
    }
    public void openEducationScreen(){
        Intent intent = new Intent(this, EducationScreen.class );
        startActivity(intent);
    }

}
