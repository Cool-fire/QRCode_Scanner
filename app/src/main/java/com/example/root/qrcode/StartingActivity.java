package com.example.root.qrcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class StartingActivity extends AppCompatActivity {

    private Button Scan;
    private Button GeneRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Scan = (Button)findViewById(R.id.scan);
        GeneRate = (Button)findViewById(R.id.generate);

        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        GeneRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartingActivity.this,GenerateActivity.class);
                startActivity(intent);
            }
        });
    }

}
