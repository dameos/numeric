package com.davidmedinaospina.appnumercico;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Launcher extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        Button interpolacion    = (Button)findViewById(R.id.interpo);
        Button ecuacionesUnaVar = (Button)findViewById(R.id.ecuacione);
        Button sitemas          = (Button)findViewById(R.id.solcionSIs);
        Button integra          = (Button)findViewById(R.id.integration);
        interpolacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Launcher.this, Interpolacion.class);
                startActivity(a);
            }
        });
        ecuacionesUnaVar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Launcher.this, MainActivity.class);
                startActivity(a);
            }
        });
        sitemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Launcher.this, SistemasDeEcuaciones.class);
                startActivity(a);
            }
        });
        integra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Launcher.this, Integracion.class);
                startActivity(a);
            }
        });
    }
}
