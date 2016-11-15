package com.davidmedinaospina.appnumercico;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import java.util.*;

public class Interpolacion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<Double> vx = new ArrayList<>();
    ArrayList<Double> vy = new ArrayList<>();
    int caso = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolacion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText valX  = (EditText)findViewById(R.id.valX);
        final EditText valY  = (EditText)findViewById(R.id.valY);
        final EditText value = (EditText)findViewById(R.id.value);
        final TextView textoValor   = (TextView)findViewById(R.id.textoValor);
        FloatingActionButton agregarPunto   = (FloatingActionButton)findViewById(R.id.floatingActionButton2);
        FloatingActionButton eliminarPuntos = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
        FloatingActionButton calcularX      = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        agregarPunto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(valX.getText().toString().isEmpty() || valY.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Valor de X o Y no pueden estar vacios", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    double x = Double.parseDouble(valX.getText().toString());
                    double y = Double.parseDouble(valY.getText().toString());
                    vx.add(x);
                    vy.add(y);
                    Snackbar.make(view, ("Punto (" + Double.toString(x) + "," + Double.toString(y) + ") Agregado"), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        eliminarPuntos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vx.clear();
                vy.clear();
                Snackbar.make(view, "Todos los puntos fueron eliminados", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        calcularX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(value.getText().toString().isEmpty()) {
                    Snackbar.make(view, "Valor a evaluar no ingreasado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(caso == 0) {
                    Snackbar.make(view, "Ningun metodo seleccionado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else if(vx.isEmpty() || vy.isEmpty()) {
                    Snackbar.make(view,"No se han ingresado puntos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else {
                    double val = Double.parseDouble(value.getText().toString());
                    double result = -1;
                    double[] x = new double[vx.size()];
                    double[] y = new double[vy.size()];
                    for (int i = 0; i < vx.size(); i++) {
                        x[i] = vx.get(i);
                        y[i] = vy.get(i);
                    }
                    switch (caso) {
                        case 1:
                            result = Newton(x,y,val);
                            break;
                        case 2:
                            result = Langrange(x,y,val);
                            break;
                        case 3:
                            result = Neville(x,y,val);
                            break;
                    }
                    textoValor.setText("El valor evuluado es" + '\n' + Double.toString(result));
                }
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.interpolacion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.solEcuaciones) {
            Intent a = new Intent(this,MainActivity.class);
            startActivity(a);
        }

        if(id == R.id.sistemasEcuaciones) {
            Intent a = new Intent(this,SistemasDeEcuaciones.class);
            startActivity(a);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        TextView selec = (TextView) findViewById(R.id.metodoSelec);

        switch (id) {
            case R.id.newton:
                selec.setText("Newton");
                caso = 1;
                break;
            case R.id.langrange:
                selec.setText("Lagrange");
                caso = 2;
                break;
            case R.id.neville:
                selec.setText("Neville");
                caso = 3;
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public double Newton(double [] puntosX, double[] puntosY, double val){
        int n = puntosX.length;
        double[][] aux = new double[n][n];
        double prod = 1;
        double acum = 0;

        for(int i = 0; i < n; i++){

            aux[i][0] = puntosY[i];

            for(int j = 1; j <= i; j++){
                aux[i][j] = (aux[i][j-1] - aux[i-1][j-1])/(puntosX[i] - puntosX[i-j]);
            }
            if(i > 0){
                prod *= val-puntosX[i-1];
            }
            acum += aux[i][i]*prod;
        }
        return acum;
    }

    public double Langrange(double [] puntosX, double[] puntosY, double val){
        int n = puntosX.length;
        double prod;
        double[] aux = new double[n];
        double acum = 0;

        for(int i = 0; i < n; i++){

            prod = 1;

            for(int j = 0; j < n; j++){

                if(j != i){
                    prod *= (val- puntosX[j])/(puntosX[i]- puntosX[j]);
                }
            }
            aux[i] = prod;
            acum += (aux[i]*puntosY[i]);
        }
        return acum;
    }
    public double Neville(double x[], double[] y, double val){
        int npuntos = x.length;
        double valores[][] = new double[npuntos][npuntos];
        for(int i = 0; i < npuntos; i++){
            valores[i][0] = y[i];
        }
        for(int i = 0; i < npuntos; i++){
            for(int j = 1; j < i + 1; j++){
                valores[i][j] = ((val - x[i-j]) * valores[i][j-1] - ((val - x[i])*valores[i-1][j-1]))/(x[i] - x[i-j]);
            }
        }
        return valores[npuntos-1][npuntos-1];
    }
}
