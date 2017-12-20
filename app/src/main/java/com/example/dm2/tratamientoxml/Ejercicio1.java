package com.example.dm2.tratamientoxml;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class Ejercicio1 extends AppCompatActivity {

    private TextView txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio1);
        txtResultado = (TextView)findViewById(R.id.txtResultado);
        Ejercicio1.CargarXmlTask tarea = new Ejercicio1.CargarXmlTask();
        tarea.execute("http://www.aemet.es/es/noticias.rss");
    }

    private class CargarXmlTask extends AsyncTask<String, Integer, Boolean> {

        List<Noticia> noticias;

        protected Boolean doInBackground(String... params) {
            RssParserDomNoticia saxparser = new RssParserDomNoticia(params[0]);
            noticias = saxparser.parse();
            return true;
        }

        protected void onPostExecute(Boolean result) {
            txtResultado.setText("");
            for (int i = 0; i < noticias.size(); i++) {
                txtResultado.setText(
                        txtResultado.getText().toString() +
                                noticias.get(i).getTitulo()+"\n" +
                                "Link: "+noticias.get(i).getLink()+"\n\n");
            }
        }
    }
}