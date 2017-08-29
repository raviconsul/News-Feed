package com.example.ravi.newsfeed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    ImageButton bbc,gle,hin,toi;
    String bbc_url="https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=f32d88053a09428788f1a1b8f82f8d91";
    String gle_url=" https://newsapi.org/v1/articles?source=google-news&sortBy=top&apiKey=f32d88053a09428788f1a1b8f82f8d91";
    String hin_url=" https://newsapi.org/v1/articles?source=the-hindu&sortBy=top&apiKey=f32d88053a09428788f1a1b8f82f8d91";
    String toi_url=" https://newsapi.org/v1/articles?source=the-times-of-india&sortBy=top&apiKey=f32d88053a09428788f1a1b8f82f8d91";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tb);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Choose your Source");

        bbc=(ImageButton)findViewById(R.id.iv1);
        gle=(ImageButton)findViewById(R.id.iv2);
        hin=(ImageButton)findViewById(R.id.iv3);
        toi=(ImageButton)findViewById(R.id.iv4);

        bbc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(MainActivity.this,NewsList.class);
                i.putExtra("url",bbc_url);
                startActivity(i);

            }
        });

        gle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NewsList.class);
                i.putExtra("url",gle_url);
                startActivity(i);


            }
        });

        hin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NewsList.class);
                i.putExtra("url",hin_url);
                startActivity(i);

            }
        });

        toi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NewsList.class);
                i.putExtra("url",toi_url);
                startActivity(i);

            }
        });



    }
}
