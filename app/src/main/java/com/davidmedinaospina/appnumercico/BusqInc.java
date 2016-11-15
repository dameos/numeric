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

public class BusqInc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busq_inc);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                createTable(bundle.getString("expr"));
            }
        });
    }

    protected void createTable(String exp) {
        final TextView busqIncResultView = (TextView)findViewById(R.id.busqInc_result);

        final EditText xoEdit = (EditText)findViewById(R.id.x0numberBusq);
        final EditText deltaEdit = (EditText)findViewById(R.id.delnumberBusq);
        final EditText iterEdit = (EditText)findViewById(R.id.iternumberBusq);

        Double x0 = Double.parseDouble(xoEdit.getText().toString());
        Double delta = Double.parseDouble(deltaEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());

        // Creando el analizador para la función
        try {
            Expression e = new ExpressionBuilder(exp)
                    .variables("x").build();

            // Crear tabla para ingresar los datos obtenidos por el método
            TableLayout table = (TableLayout)findViewById(R.id.busqInc_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView txView = new TextView(this);
            TextView tfxView = new TextView(this);

            txView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);

            txView.setText(" x ");
            tfxView.setText(" f(x) ");

            ttr.addView(txView);
            ttr.addView(tfxView);

            table.addView(ttr,0);

            double x1, y0, y1;
            int cont;

            if (delta != 0) {
                if (iter > 0) {
                    y0 = e.setVariable("x", x0).evaluate();
                    if (y0 != 0) {
                        x1 = x0 + delta;
                        y1 = e.setVariable("x", x1).evaluate();
                        cont = 1;
                        while (((y0 * y1) > 0) && cont < iter) {
                            TableRow tr = new TableRow(this);
                            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                            tr.setLayoutParams(lp);
                            TextView xView = new TextView(this);
                            TextView fxView = new TextView(this);

                            xView.setBackgroundResource(R.drawable.table_border);
                            fxView.setBackgroundResource(R.drawable.table_border);

                            xView.setText(" " + String.valueOf(x0) + " ");
                            fxView.setText(" " + String.valueOf(y0) + " ");

                            tr.addView(xView);
                            tr.addView(fxView);

                            table.addView(tr,cont);

                            x0 = x1;
                            y0 = y1;
                            x1 = x0 + delta;
                            y1 = e.setVariable("x", x1).evaluate();
                            cont++;
                        }
                        if (y0 == 0) {
                            busqIncResultView.setText(x1 + " es una raíz.");
                            return;
                        } else {
                            String res = ((y0 * y1) < 0) ? "Raíz entre los valores (" + x0 + "," + x1 + ")" : "Fracasé, soy imbécil.";
                            busqIncResultView.setText(res);
                            return;
                        }
                    } else {
                        busqIncResultView.setText(x0 + " es una raíz.");
                        return;
                    }
                } else {
                    busqIncResultView.setText("El número de iteraciones debe ser mayor de 0.");
                    return;
                }
            } else {
                busqIncResultView.setText("Delta debe ser diferente de 0.");
                return;
            }
        } catch (RuntimeException e) {
            busqIncResultView.setText(e.getMessage());
        }
    }

}
