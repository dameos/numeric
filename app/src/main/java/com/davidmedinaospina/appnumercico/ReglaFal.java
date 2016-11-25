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

public class ReglaFal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regla_fal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getIntent().getExtras();
                createTable(bundle.getString("expr"));
            }});
    }

    protected void createTable(String exp) {
        final TextView regResultView = (TextView)findViewById(R.id.reg_fal_result);

        final EditText xiEdit = (EditText)findViewById(R.id.xinumberReg);
        final EditText xsEdit = (EditText)findViewById(R.id.xsnumberReg);
        final EditText tolEdit = (EditText)findViewById(R.id.tolnumberReg);
        final EditText iterEdit = (EditText)findViewById(R.id.iternumberReg);

        Double xi = Double.parseDouble(xiEdit.getText().toString());
        Double xs = Double.parseDouble(xsEdit.getText().toString());
        Double tol = Double.parseDouble(tolEdit.getText().toString());
        int iter = Integer.parseInt(iterEdit.getText().toString());

        // Creando el analizador para la función
        try {
            Expression e = new ExpressionBuilder(exp)
                    .variables("x").build();

            // Crear tabla para ingresar los datos obtenidos por el método
            TableLayout table = (TableLayout)findViewById(R.id.reg_fal_table);
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));

            TextView titerView = new TextView(this);
            TextView txiView = new TextView(this);
            TextView txsView = new TextView(this);
            TextView txmView = new TextView(this);
            TextView tfxView = new TextView(this);
            TextView terrorView = new TextView(this);

            titerView.setBackgroundResource(R.drawable.table_border_title);
            txiView.setBackgroundResource(R.drawable.table_border_title);
            txsView.setBackgroundResource(R.drawable.table_border_title);
            txmView.setBackgroundResource(R.drawable.table_border_title);
            tfxView.setBackgroundResource(R.drawable.table_border_title);
            terrorView.setBackgroundResource(R.drawable.table_border_title);

            titerView.setText(" Iter ");
            txiView.setText(" Xi ");
            txsView.setText(" Xs ");
            txmView.setText(" Xm ");
            tfxView.setText(" f(xm) ");
            terrorView.setText(" Error ");

            ttr.addView(titerView);
            ttr.addView(txiView);
            ttr.addView(txsView);
            ttr.addView(txmView);
            ttr.addView(tfxView);
            ttr.addView(terrorView);

            table.addView(ttr,0);

            double err = 5.0f + tol;
            int cnt;
            double yi = e.setVariable("x", xi).evaluate();
            double ys = e.setVariable("x", xs).evaluate();
            if ((yi * ys) < 0) {
                if (iter > 0) {
                    double xm = (xi + xs) / 2;
                    double ym = e.setVariable("x", xm).evaluate();
                    double erra = xm;
                    cnt = 1;
                    while ((ym != 0) && (err > tol) && (cnt < iter)) {
                        TableRow tr = new TableRow(this);
                        TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                        tr.setLayoutParams(lp);
                        TextView iterView = new TextView(this);
                        TextView xiView = new TextView(this);
                        TextView xsView = new TextView(this);
                        TextView xmView = new TextView(this);
                        TextView fxView = new TextView(this);
                        TextView errorView = new TextView(this);

                        iterView.setBackgroundResource(R.drawable.table_border);
                        xiView.setBackgroundResource(R.drawable.table_border);
                        xsView.setBackgroundResource(R.drawable.table_border);
                        xmView.setBackgroundResource(R.drawable.table_border);
                        fxView.setBackgroundResource(R.drawable.table_border);
                        errorView.setBackgroundResource(R.drawable.table_border);

                        iterView.setText(" " + String.valueOf(cnt) + " ");
                        xiView.setText(" " + String.valueOf(xi) + " ");
                        xsView.setText(" " + String.valueOf(xs) + " ");
                        xmView.setText(" " + String.valueOf(xm) + " ");
                        fxView.setText(" " + String.valueOf(ym) + " ");
                        errorView.setText(" " + String.valueOf(err) + " ");

                        tr.addView(iterView);
                        tr.addView(xiView);
                        tr.addView(xsView);
                        tr.addView(xmView);
                        tr.addView(fxView);
                        tr.addView(errorView);

                        table.addView(tr,cnt);

                        if ((yi * ym) < 0) {
                            xs = xm;
                            ys = ym;
                        } else {
                            xi = xm;
                            yi = ym;
                        }
                        xm = xi - ((yi * (xi - xs))/(yi - ys));
                        ym = e.setVariable("x", xm).evaluate();
                        err = Math.abs(xm - erra);
                        erra = xm;
                        cnt++;
                    }
                    if (ym == 0) {
                        regResultView.setText("La ecuación tiene solución en xm = " + xm);
                        return;
                    }
                    if (err < tol) {
                        regResultView.setText("La ecuación tiene solución aproximada en xm = " + xm
                                + " con un error absoluto de " + err);
                        return;
                    }
                    regResultView.setText("El número de iteraciones ha sido superado.");
                    return;
                } else {
                    regResultView.setText("El número de iteraciones debe ser mayor a 0.");
                }
            }
            if (yi == 0){
                regResultView.setText("En xi = " + xi + " existe una raíz.");
                return;
            }
            if (ys == 0){
                regResultView.setText("En xs = " + xs + " existe una raíz.");
                return;
            }
            TextView res = new TextView(this);
            res.setText("no sé qué poner aquí");
            table.addView(res);
        } catch (RuntimeException e) {
            regResultView.setText(e.getMessage());
        }
    }
}
