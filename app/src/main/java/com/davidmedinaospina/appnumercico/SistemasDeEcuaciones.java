package com.davidmedinaospina.appnumercico;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.support.design.widget.Snackbar;

import com.androidplot.ui.TableOrder;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SistemasDeEcuaciones extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int tamaño;
    private ArrayList<ArrayList> datos = new ArrayList<>();
    private ArrayList<Double> resultados = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistemas_de_ecuaciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mst = (Button) findViewById(R.id.crear);
        Button guardar = (Button) findViewById(R.id.guardar);

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTable();
            }});


        mst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTable();
            }});
        


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
        getMenuInflater().inflate(R.menu.sistemas_de_ecuaciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id == R.id.interpolation){
            Intent a = new Intent(this, Interpolacion.class);
            startActivity(a);
        }

        if(id == R.id.solEcuaciones) {
            Intent a = new Intent(this, MainActivity.class);
            startActivity(a);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    protected void createTable() {

        final EditText nmatriz = (EditText)findViewById(R.id.nmatriz);


        tamaño = Integer.parseInt(nmatriz.getText().toString());

        try {
            TableLayout table = (TableLayout) findViewById(R.id.ingreso_datos);
            final LayoutParams lparams = new LayoutParams(20, LayoutParams.MATCH_PARENT);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);


            for (int i = 0; i < tamaño; i++) {
                TableRow ttr = new TableRow(this);
                ttr.setLayoutParams(tlp);
                ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));
                for (int j = 0; j <= tamaño; j++) {
                    EditText etxt = new EditText(this);
                    etxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                    //etxt.setBackgroundResource(R.drawable.table_border);
                    //etxt.setLayoutParams(lparams);
                    ttr.addView(etxt);
                }
                table.addView(ttr);
            }
            Button guardar = (Button) findViewById(R.id.guardar);
            guardar.setVisibility(View.VISIBLE);


        } catch (RuntimeException e) {
            System.out.println("Holi");


        }
}


    protected void saveTable() {

        TableLayout table = (TableLayout) findViewById(R.id.ingreso_datos);

        for (int i = 0; i < tamaño; i++) {
            ArrayList<Double> fila = new ArrayList<>();
            TableRow t = (TableRow) table.getChildAt(i);
            for (int j = 0; j < tamaño; j++) {
                EditText etxt = (EditText) t.getChildAt(j);
                Double num = Double.parseDouble(etxt.getText().toString());
                fila.add(num);
            }
            datos.add(fila);
        }
        for (int i = 0; i < tamaño; i++) {
            TableRow t = (TableRow) table.getChildAt(i);
            EditText etxt = (EditText) t.getChildAt(tamaño);
            Double num = Double.parseDouble(etxt.getText().toString());
            resultados.add(num);
        }
    }
}
