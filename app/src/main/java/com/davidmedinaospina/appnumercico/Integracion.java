package com.davidmedinaospina.appnumercico;

import android.net.Uri;
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

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;


public class Integracion extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    int caso = 0;
    double a = 0d;
    double b = 0d;
    int    n1 = 0;
    String expr = "";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integracion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final EditText puntoA = (EditText) findViewById(R.id.pointA);
        final EditText puntoB = (EditText) findViewById(R.id.pointB);
        final EditText func   = (EditText) findViewById(R.id.funcIntegrate);
        final EditText n      = (EditText) findViewById(R.id.numberOfPoints);
        final TextView res    = (TextView) findViewById(R.id.resultadito);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n1 = 0;
                double ans = 0d;
                String pA  = puntoA.getText().toString();
                String pB  = puntoB.getText().toString();
                String nuN = n.getText().toString();
                if (!pA.isEmpty()) a   = Double.parseDouble(pA);
                if (!pB.isEmpty()) b   = Double.parseDouble(pB);
                if (!nuN.isEmpty()) n1 = Integer.parseInt(nuN);
                expr = func.getText().toString();
                try {
                    switch (caso) {
                        case 0:
                            Snackbar.make(view, "Ningun metodo seleccionado", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            break;
                        case 1:
                            ans = trapecioSencillo(a,b);
                            res.setText("El valor de la Integral es" + '\n' + Double.toString(ans));
                            break;
                        case 2:
                            if( n1 == 0) {
                                Snackbar.make(view, "N no puede ser vacio para los metodos generalizados", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                ans = trapecioCompuesto(a,b,n1);
                                res.setText("El valor de la Integral es" + '\n' + Double.toString(ans));
                            }
                            break;
                        case 3:
                            ans = simpsonUnTercio(a,b);
                            res.setText("El valor de la Integral es" + '\n' + Double.toString(ans));
                            break;
                        case 4:
                            if( n1 == 0) {
                                Snackbar.make(view, "N no puede ser vacio para los metodos generalizados", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                ans = simpsonUnTercioGen(a,b,n1);
                                res.setText("El valor de la Integral es" + '\n' + Double.toString(ans));
                            }
                            break;
                        case 5:
                            ans = simpsonTresOctavos(a,b);
                            res.setText("El valor de la Integral es" + '\n' + Double.toString(ans));
                            break;
                        case 6:
                            if( n1 == 0) {
                                Snackbar.make(view, "N no puede ser vacio para los metodos generalizados", Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();
                            } else {
                                ans = simpsonTresOctavosGen(a,b,n1);
                                res.setText("El valor de la Integral es" + '\n' + Double.toString(ans));
                            }
                            break;
                    }
                } catch (RuntimeException ex) {
                    Snackbar.make(view, ex.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
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
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
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
        getMenuInflater().inflate(R.menu.integracion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        TextView selectedMethod = (TextView) findViewById(R.id.integrationMethod);

        switch (id) {
            case R.id.trapecioSencillo:
                selectedMethod.setText("Trapecio Sencillo");
                caso = 1;
                break;
            case R.id.trapecioCompuesto:
                selectedMethod.setText("Trapecio Gen");
                caso = 2;
                break;
            case R.id.simpsonUnTercio:
                selectedMethod.setText("Simpson 1/3");
                caso = 3;
                break;
            case R.id.simpsonUnTercioGen:
                selectedMethod.setText("Simpson 1/3 Gen");
                caso = 4;
                break;
            case R.id.simpsonTresOct:
                selectedMethod.setText("Simpson 3/8");
                caso = 5;
                break;
            case R.id.simpsonTresOctGen:
                selectedMethod.setText("Simpson 3/8 Gen");
                caso = 6;
                break;
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public double trapecioSencillo(double a, double b)   {
            Expression e = new ExpressionBuilder(expr)
                    .variable("x")
                    .build();
            double h = (b - a) / 2;
            double eval = e.setVariable("x",a).evaluate() + e.setVariable("x",b).evaluate();
            double res = h * eval;
        return res;
    }

    public double trapecioCompuesto(double a, double b, int n) {
            Expression e = new ExpressionBuilder(expr)
                    .variable("x")
                    .build();
            double h = (b - a) / n;
            double aAux = a;
            double cont = e.setVariable("x",a).evaluate() + e.setVariable("x",b).evaluate();

            for (int i = 0; i < n - 1; i++) {
                aAux += h;
                cont += 2 * e.setVariable("x",aAux).evaluate();
            }

            double res = (h / 2) * (cont);
            return res;
    }

    public double simpsonUnTercio(double a, double b) {

            Expression e = new ExpressionBuilder(expr)
                    .variable("x")
                    .build();
            double h = (b - a) / 3;
            double aMedia = (a + b) / 2;
            double eval = e.setVariable("x",a).evaluate() + e.setVariable("x",b).evaluate() + (4 * e.setVariable("x",aMedia).evaluate());
            double res = (h / 2) * eval;
            return res;
    }

    public double simpsonUnTercioGen(double a, double b, int n) {

            Expression e = new ExpressionBuilder(expr)
                    .variable("x")
                    .build();
            double h = (b - a) / n;
            double aAux = a;
            double cont = e.setVariable("x",a).evaluate() + e.setVariable("x",a).evaluate();

            for (int i = 2; i < n; i = i + 2) {
                aAux = a + (i * h);
                cont += 2 * e.setVariable("x",aAux).evaluate();
            }

            for (int i = 1; i < n; i = i + 2) {
                aAux = a + (i * h);
                cont += 4 * e.setVariable("x",aAux).evaluate();
            }

            double res = (h / 3) * (cont);
            return res;
    }

    public double simpsonTresOctavos(double a, double b) {
            Expression e = new ExpressionBuilder(expr)
                    .variable("x")
                    .build();
            double h = (b - a) / 3;
            double a1 = a + h;
            double a2 = a + h + h;
            double eval = e.setVariable("x",a).evaluate() + e.setVariable("x",b).evaluate() + 3 * e.setVariable("x",a1).evaluate() + 3 * e.setVariable("x",a2).evaluate();
            double res = ((3 * h) / 8) * eval;
            return res;
    }

    public double simpsonTresOctavosGen(double a, double b, int n) {
            Expression e = new ExpressionBuilder(expr)
                    .variable("x")
                    .build();
            double h = (b - a) / n;
            double aAux;
            double cont = e.setVariable("x",a).evaluate() + e.setVariable("x",b).evaluate();

            for (int i = 1; i < n; i++) {
                aAux = a + (i * h);

                if (i % 3 == 0) {
                    cont += 2 * e.setVariable("x",aAux).evaluate();
                } else {
                    cont += 3 * e.setVariable("x",aAux).evaluate();
                }
            }

            double res = ((3 * h) / 8) * cont;
            return res;
    }
}
