package com.hackuniv.daanveer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hackuniv.daanveer.Model.DonatorDetails;

public class DonatorIntro extends AppCompatActivity {
    private Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donator_intro);
        getSupportActionBar().hide();
        getStarted = findViewById(R.id.btnGetStart);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DonatorIntro.this, DonatorActivity.class));
                finish();
            }
        });
    }
}