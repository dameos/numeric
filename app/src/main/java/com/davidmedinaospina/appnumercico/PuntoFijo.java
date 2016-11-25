package com.davidmedinaospina.appnumercico;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class PuntoFijo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punto_fijo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                /*TOCA HACERLA POR PARAMETRO PORQUE SOS DEMASIADO MARICA PARA PONER UNA VARIABLE GLOBAL*/
                createTable(bundle.getString("expr"),bundle.getString("funcG"));
            }});
    }

    protected void createTable(String fn, String gn) {
        final TextView pFijoResultView = (TextView)findViewById(R.id.pfijo_result);

        final EditText xnEdit = (EditText)findViewById(R.id.xnnumberPfijo);
        final EditText tolEdit = (EditText)findViewById(R.id.tolnumberPfijo);
        final EditText iterEdit = (EditText)findViewById(R.id.iternumberPfijo);

        Double xn = Double.parseDouble(xnEdit.getText().toString());
        Double tol = Double.parseDouble(tolEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());

        // Creando el analizador para la función
        try {
            Expression f = new ExpressionBuilder(fn)
                    .variables("x").build();

            Expression g = new ExpressionBuilder(gn)
                    .variables("x").build();

            // Crear tabla para ingresar los datos obtenidos por el método
            TableLayout table = (TableLayout)findViewById(R.id.pfijo_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView titerView = new TextView(this);
            TextView tgxView = new TextView(this);
            TextView tfxView = new TextView(this);
            TextView terrorView = new TextView(this);

            titerView.setBackgroundResource(R.drawable.table_border_title);
            tgxView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);
            terrorView.setBackgroundResource(R.drawable.table_border_title);

            titerView.setText(" Iter ");
            tgxView.setText(" g(x) ");
            tfxView.setText(" f(x) ");
            terrorView.setText(" Error ");

            ttr.addView(titerView);
            ttr.addView(tgxView);
            ttr.addView(tfxView);
            ttr.addView(terrorView);

            table.addView(ttr,0);

            int cont;
            if (iter > 0) {
                double fx = f.setVariable("x", xn).evaluate();
                double error = tol + 1.0;
                double xna = xn;
                cont = 1;
                while ((fx != 0) && (error > tol) && (cont < iter)) {

                    TableRow tr = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tr.setLayoutParams(lp);
                    TextView iterView = new TextView(this);
                    TextView gxView = new TextView(this);
                    TextView fxView = new TextView(this);
                    TextView errorView = new TextView(this);

                    iterView.setBackgroundResource(R.drawable.table_border);
                    gxView.setBackgroundResource(R.drawable.table_border);
                    fxView.setBackgroundResource(R.drawable.table_border);
                    errorView.setBackgroundResource(R.drawable.table_border);

                    iterView.setText(" " + String.valueOf(cont) + " ");
                    gxView.setText(" " + String.valueOf(xn) + " ");
                    fxView.setText(" " + String.valueOf(fx) + " ");
                    errorView.setText(" " + String.valueOf(error) + " ");

                    tr.addView(iterView);
                    tr.addView(gxView);
                    tr.addView(fxView);
                    tr.addView(errorView);

                    table.addView(tr,cont);

                    xna = xn;
                    xn = g.setVariable("x", xn).evaluate();
                    fx = f.setVariable("x", xn).evaluate();
                    error = Math.abs(xn - xna);
                    cont++;
                }
                if (fx == 0) {
                    pFijoResultView.setText("Raíz en xn = " + xn);
                    return;
                }
                if (error < tol) {
                    pFijoResultView.setText("En xn = " + xn + " hay una aproximación a raíz con un error de " + error);
                    return;
                }
                pFijoResultView.setText("Superado el número de iteraciones.");
                return;
            }
            pFijoResultView.setText("El número de iteraciones debe ser mayor a 0.");
            return;
        } catch (RuntimeException e) {
            pFijoResultView.setText(e.getMessage());
        }
    }
}
