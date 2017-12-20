package com.example.dm2.tratamientoxml;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ejercicio1(View v){
        Intent Ejer1 = new Intent(Main.this, Ejercicio1.class);
        startActivity(Ejer1);
    }

    public void ejercicio2(View v){
        Intent Ejer2 = new Intent(Main.this, Ejercicio2.class);
        startActivity(Ejer2);
    }

}