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

import java.text.DecimalFormat;

public class Secant extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secant);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                createTable(bundle.getString("expr"));
            }
        });
    }

    protected void createTable(String fn) {
        final TextView secanteResultView = (TextView)findViewById(R.id.secante_result);

        final EditText x0Edit = (EditText)findViewById(R.id.x0numberSecante);
        final EditText x1Edit = (EditText)findViewById(R.id.x1numberSecante);
        final EditText tolEdit = (EditText)findViewById(R.id.tolnumberSecante);
        final EditText iterEdit = (EditText)findViewById(R.id.iternumberSecante);

        Double x0 = Double.parseDouble(x0Edit.getText().toString());
        Double x1 = Double.parseDouble(x1Edit.getText().toString());
        Double tol = Double.parseDouble(tolEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());

        DecimalFormat df = new DecimalFormat( "#########0.00E00" );

        // Creando el analizador para la función
        try {
            Expression f = new ExpressionBuilder(fn)
                    .variables("x").build();

            // Crear tabla para ingresar los datos obtenidos por el método
            TableLayout table = (TableLayout) findViewById(R.id.secante_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView tnView = new TextView(this);
            TextView txnView = new TextView(this);
            TextView tfxView = new TextView(this);
            TextView terrorView = new TextView(this);

            tnView.setBackgroundResource(R.drawable.table_border_title);
            txnView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);
            terrorView.setBackgroundResource(R.drawable.table_border_title);

            tnView.setText(" n ");
            txnView.setText(" xn ");
            tfxView.setText(" f(xn) ");
            terrorView.setText(" Error ");

            ttr.addView(tnView);
            ttr.addView(txnView);
            ttr.addView(tfxView);
            ttr.addView(terrorView);

            table.addView(ttr, 0);

            if (iter > 0) {
                double fx0 = f.setVariable("x", x0).evaluate();
                double fx1 = f.setVariable("x", x1).evaluate();
                int cont = 0;
                double error = tol + 1;

                TableRow tr0 = new TableRow(this);
                TableRow.LayoutParams lp0 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tr0.setLayoutParams(lp0);

                TextView n0View = new TextView(this);
                TextView xn0View = new TextView(this);
                TextView fx0View = new TextView(this);
                TextView error0View = new TextView(this);

                n0View.setBackgroundResource(R.drawable.table_border);
                xn0View.setBackgroundResource(R.drawable.table_border);
                fx0View.setBackgroundResource(R.drawable.table_border);
                error0View.setBackgroundResource(R.drawable.table_border);

                n0View.setText(" -1 ");
                xn0View.setText(" " + String.valueOf(x0) + " ");
                fx0View.setText(" " + String.valueOf(df.format(fx0)) + " ");
                error0View.setText("  ");

                tr0.addView(n0View);
                tr0.addView(xn0View);
                tr0.addView(fx0View);
                tr0.addView(error0View);

                table.addView(tr0, cont);

                TableRow tr1 = new TableRow(this);
                TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tr0.setLayoutParams(lp1);

                n0View.setText(" 0 ");
                xn0View.setText(" " + String.valueOf(x1) + " ");
                fx0View.setText(" " + String.valueOf(df.format(fx1)) + " ");
                error0View.setText("  ");

                tr1.addView(n0View);
                tr1.addView(xn0View);
                tr1.addView(fx0View);
                tr1.addView(error0View);

                table.addView(tr1, cont + 1);

                double xaux;

                while ((fx1 != 0) && (error > tol) && (cont < iter)) {

                    xaux = x1;
                    x1 = x1 - ((fx1 * (x1 - x0)) / (fx1 - fx0));
                    x0 = xaux;
                    fx0 = fx1;
                    fx1 = f.setVariable("x", x1).evaluate();
                    error = Math.abs(x1 - x0);
                    cont++;

                    TableRow tr = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                    tr.setLayoutParams(lp);

                    TextView nView = new TextView(this);
                    TextView xnView = new TextView(this);
                    TextView fxView = new TextView(this);
                    TextView errorView = new TextView(this);

                    nView.setBackgroundResource(R.drawable.table_border);
                    xnView.setBackgroundResource(R.drawable.table_border);
                    fxView.setBackgroundResource(R.drawable.table_border);
                    errorView.setBackgroundResource(R.drawable.table_border);

                    nView.setText(" " + (String.valueOf(cont) + 1) + " ");
                    xnView.setText(" " + String.valueOf(x1) + " ");
                    fxView.setText(" " + String.valueOf(df.format(fx1)) + " ");
                    errorView.setText(" " + String.valueOf(df.format(error)) + " ");

                    tr.addView(nView);
                    tr.addView(xnView);
                    tr.addView(fxView);
                    tr.addView(errorView);

                    table.addView(tr, cont);


                }
                if (fx1 == 0) secanteResultView.setText("Raíz en xn = " + x1);
                if (error < tol) {
                    secanteResultView.setText("Aproximación a raíz en xn = " + x1 + "con error de " + error);
                    return;
                }
                if ((fx1 - fx0) == 0) {
                    secanteResultView.setText("Posibles múltiples raices.");
                    return;
                }
                secanteResultView.setText("Superado el número de iteraciones.");
                return;
            }
            secanteResultView.setText("El número de iteraciones debe ser mayor a 0.");
            return;
        } catch (RuntimeException e) {
            secanteResultView.setText(e.getMessage());
        }
    }
}
