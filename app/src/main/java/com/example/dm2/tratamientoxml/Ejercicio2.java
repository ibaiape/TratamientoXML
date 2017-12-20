package com.example.dm2.tratamientoxml;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class Ejercicio2 extends AppCompatActivity {
    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio2);
        txtResultado = (TextView)findViewById(R.id.txtResultado);
        Ejercicio2.CargarXmlTask tarea = new Ejercicio2.CargarXmlTask();
        tarea.execute("http://www.aemet.es/xml/municipios/localidad_01059.xml");
    }

    private class CargarXmlTask extends AsyncTask<String, Integer, Boolean> {

        List<Noticia> noticias;

        String str;

        protected Boolean doInBackground(String... params) {
            RssParserDomTiempo saxparser = new RssParserDomTiempo(params[0]);
            str = saxparser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            txtResultado.setText(""+str);
        }
    }
}
