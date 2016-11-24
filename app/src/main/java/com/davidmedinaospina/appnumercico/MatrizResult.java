package com.davidmedinaospina.appnumercico;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MatrizResult extends AppCompatActivity {
    private int tamaño;
    private ArrayList<ArrayList> datos = new ArrayList<>();
    private ArrayList<Double> resultados = new ArrayList<>();
    private String metodo;
    private Double[][] matriz = new Double[datos.size()][datos.size()];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz_result);
        Bundle bundle = getIntent().getExtras();
        tamaño = bundle.getInt("tamaño");
        resultados = (ArrayList<Double>) bundle.getSerializable("resultados");
        datos = (ArrayList<ArrayList>) bundle.getSerializable("datos");
        metodo = bundle.getString("operation");
        TextView met = (TextView) findViewById(R.id.metodo);
        met.setTextSize(20);
        met.setText(metodo);

        TableLayout table = (TableLayout)findViewById(R.id.result_table);
        for(int i = 0; i < datos.size(); i++) {
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));
            ArrayList<Double> lista = datos.get(i);
            for (int j = 0; j < datos.size(); j++) {
                TextView n = new TextView(this);
                n.setBackgroundResource(R.drawable.table_border_title);
                n.setText(String.valueOf(lista.get(j)));
                n.setHeight(50);
                n.setWidth(120);
                ttr.addView(n);
            }
            TextView n = new TextView(this);
            n.setBackgroundResource(R.drawable.edit_border);
            n.setText(String.valueOf(resultados.get(i)));
            n.setHeight(50);
            n.setWidth(120);
            ttr.addView(n);
            table.addView(ttr);
        }



    }

    public static double[][] EliminacionGauss(double[][] a) {
        int n = a.length;
        for (int k = 0; k < n - 1; k++)
        {
            for (int i = k + 1; i < n; i++)
            {
                double mult = a[i][k]/a[k][k];
                for (int j = k; j < n + 1; j++)
                {
                    a[i][j] = a[i][j] - mult*a[k][j];
                }
            }
        }
        return a;
    }

    public static double[] SustitucionRegresiva(double[][] ab) {
        int n = ab.length;
        double[] x = new double[n];
        x[n-1]  = ab[n-1][n]/ab[n-1][n-1];
        for (int i = n - 1; i >= 0; i--)
        {
            double cont = 0;
            for (int j = i + 1; j < n; j++)
            {
                cont+=(ab[i][j]*x[j]);
            }
            x[i] = (ab[i][n] - cont)/ab[i][i];
        }
        return x;
    }

}
