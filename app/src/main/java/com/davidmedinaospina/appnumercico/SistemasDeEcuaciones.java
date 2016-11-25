package com.davidmedinaospina.appnumercico;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

import com.androidplot.ui.TableOrder;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class SistemasDeEcuaciones extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int tamaño;
    private ArrayList<ArrayList> datos = new ArrayList<>();
    private ArrayList<Double> resultados = new ArrayList<>();
    private boolean paso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sistemas_de_ecuaciones);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button mst = (Button) findViewById(R.id.crear);
        Button guardar = (Button) findViewById(R.id.guardar);
        Button rela = (Button) findViewById(R.id.relajacion);
        Button iter = (Button) findViewById(R.id.iterativos);

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

        rela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relajacion();
            }});

        iter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iteraciones();
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
        Bundle bun = new Bundle();
        bun.putInt("tamaño", tamaño);
        bun.putSerializable("datos", datos);
        bun.putSerializable("resultados", resultados);
        bun.putBoolean("paso",paso);
        int id = item.getItemId();
        switch (id){
            case R.id.elimG:
                bun.putString("operation", "elimG");
                Intent a = new Intent(this, MatrizResult.class);
                a.putExtras(bun);
                startActivity(a);
                break;
            case R.id.elimGPP:
                bun.putString("operation", "elimGPP");
                Intent b = new Intent(this, MatrizResult.class);
                b.putExtras(bun);
                startActivity(b);
                break;
            case R.id.elimGPT:
                bun.putString("operation", "elimGPT");
                Intent c = new Intent(this, MatrizResult.class);
                c.putExtras(bun);
                startActivity(c);
                break;
            case R.id.gaussSeidel:
                bun.putString("operation", "gaussSeidel");
                Intent d = new Intent(this, MatrizResult.class);
                d.putExtras(bun);
                startActivity(d);
                break;
            case R.id.jacobi:
                bun.putString("operation", "jacobi");
                Intent e = new Intent(this, MatrizResult.class);
                e.putExtras(bun);
                startActivity(e);
                break;
            case R.id.cholesky:
                bun.putString("operation","cholesky");
                Intent f = new Intent(this, MatrizResult.class);
                f.putExtras(bun);
                startActivity(f);
                break;
            case R.id.crout:
                bun.putString("operation","crout");
                Intent g = new Intent(this, MatrizResult.class);
                g.putExtras(bun);
                startActivity(g);
                break;
            case R.id.dolittle:
                bun.putString("operation","dolittle");
                Intent h = new Intent(this, MatrizResult.class);
                h.putExtras(bun);
                startActivity(h);
                break;

        }
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
                ttr.setMinimumHeight(110);
                for (int j = 0; j <= tamaño; j++) {
                    EditText etxt = new EditText(this);
                    etxt.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    etxt.setHeight(100);
                    etxt.setWidth(100);
                    if(j == tamaño){
                        etxt.setBackgroundResource(R.drawable.edit_border);
                    }else{
                        etxt.setBackgroundResource(R.drawable.table_border);
                    }
                    ttr.addView(etxt);
                }
                table.addView(ttr);
            }
            Button guardar = (Button) findViewById(R.id.guardar);
            Button rela = (Button) findViewById(R.id.relajacion);
            Button iter = (Button) findViewById(R.id.iterativos);
            guardar.setVisibility(View.VISIBLE);
            rela.setVisibility(View.VISIBLE);
            iter.setVisibility(View.VISIBLE);



        } catch (RuntimeException e) {
            System.out.println("Holi");


        }
}


    protected void saveTable() {

        TableLayout table = (TableLayout) findViewById(R.id.ingreso_datos);
        CheckBox check = (CheckBox) findViewById(R.id.checkP);
        paso = check.isEnabled();
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
        Bundle bundle = new Bundle();
        bundle.putSerializable("datos", datos);
        bundle.putSerializable("resultados",resultados);
        bundle.putInt("tamaño",tamaño);
        bundle.putBoolean("paso", paso);
        Intent a = new Intent(this,MatrizResult.class);



    }

    protected void relajacion(){
        TableLayout table = (TableLayout) findViewById(R.id.relajaciont);
        TableRow ttr = new TableRow(this);
        TextView txt = new TextView(this);
        txt.setText("Valor de la relajacion");
        EditText etxt = new EditText(this);
        etxt.setKeyListener(new DigitsKeyListener());
        etxt.setHeight(100);
        etxt.setWidth(100);
        etxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        ttr.addView(txt);
        ttr.addView(etxt);
        table.addView(ttr);
    }


    protected void iteraciones(){
        TableLayout table = (TableLayout) findViewById(R.id.iterativost);
        TableRow ttr = new TableRow(this);
        ttr.setMinimumHeight(110);
        for(int i = 0; i < tamaño; i++){
            EditText etxt = new EditText(this);
            etxt.setWidth(100);
            etxt.setHeight(100);
            etxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
            etxt.setKeyListener(new DigitsKeyListener());
            ttr.addView(etxt);
        }
        TableRow iter = new TableRow(this);
        TextView txt = new TextView(this);
        txt.setText("Numero de iteraciones");
        EditText etxt = new EditText(this);
        etxt.setKeyListener(new DigitsKeyListener());
        etxt.setWidth(100);
        etxt.setHeight(100);
        iter.addView(txt);
        iter.addView(etxt);
        TableRow iter1 = new TableRow(this);
        TextView txt1 = new TextView(this);
        txt.setText("Tolerancia");
        EditText etxt1 = new EditText(this);
        etxt.setKeyListener(new DigitsKeyListener());
        etxt.setWidth(100);
        etxt.setHeight(100);
        iter.addView(txt);
        iter.addView(etxt);
        table.addView(iter);
        table.addView(iter);
        table.addView(ttr);
    }
}
