package com.davidmedinaospina.appnumercico;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import java.text.DecimalFormat;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MatrizResult extends AppCompatActivity {
    private int tamaño;
    private ArrayList<ArrayList<Double>> datos = new ArrayList<>();
    private ArrayList<Double> resultados = new ArrayList<>();
    private String metodo;
    private double[][] matriz;
    private double[][] aumen;
    private int[]  marcas;
    private double[][] l;
    private double[][] u;
    private double[] ans;
    private boolean paso = false;
    private ArrayList<Double> x0 = new ArrayList<>();
    private double tolerance;
    private int iterations;
    private double relas;
    private String metodo1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matriz_result);
        Bundle bundle = getIntent().getExtras();
        tamaño = bundle.getInt("tamaño");
        resultados = (ArrayList<Double>) bundle.getSerializable("resultados");
        datos = (ArrayList<ArrayList<Double>>) bundle.getSerializable("datos");
        paso = bundle.getBoolean("paso");
        metodo = bundle.getString("operation");
        matriz = new double[datos.size()][datos.size()];
        aumen = new double[datos.size()][datos.size()+1];
        marcas = new int[tamaño];
        TextView met = (TextView) findViewById(R.id.metodo);
        met.setTextSize(20);



        switch(metodo) {
            case "elimG":
                metodo1 = "Eliminacion gaussiana";
                break;
            case "elimGPP":
                metodo1 = "E.G Pivoteo parcial";
                break;
            case "elimGPT":
                metodo1 = "E.G Pivoteo total";
                break;
            case "gaussSeidel":
                metodo1 = "Gauss Seidel";
                break;
            case "jacobi":
                metodo1 = "Jacobi";
                break;
            case "cholesky":
                metodo1 = "Cholesky";
                break;
            case "crout":
                metodo1 = "Crout";
                break;
            case "dolitle":
                metodo1 = "Dolittle";
                break;


        }
        met.setText(metodo1);

        for(int i = 0; i < marcas.length; i++){
            marcas[i] = i;
        }

        for (int i = 0; i < tamaño; i++) {
            ArrayList<Double> temp = datos.get(i);
            for (int j = 0; j < tamaño; j++) {
                matriz[i][j] = temp.get(j);
            }
        }
        for (int i = 0; i < tamaño; i++) {
            ArrayList<Double> temp = datos.get(i);
            for (int j = 0; j < tamaño; j++) {
                aumen[i][j] = temp.get(j);
            }
            aumen[i][tamaño] = resultados.get(i);
        }
        double[] results = new double[resultados.size()];
        for (int i = 0; i < results.length; i++) {
            results[i] = resultados.get(i);
        }
        double [][]  res;
        try {
            switch (metodo) {
                case "elimG":
                    res = EliminacionGauss(aumen, 0);
                    if(!paso)Imprimir(res, false);
                    ans = SustitucionRegresiva(res);
                    NameMatrix("");
                    String text = "";
                    for (int i = 0; i < marcas.length; i++) {
                        text = text + "  " +  "X" +  String.valueOf(marcas[i]);
                    }
                    NameMatrix(text);
                    ImprimirArray(ans);
                    break;
                case "elimGPP":
                    res = EliminacionGauss(aumen, 1);
                    if(!paso)Imprimir(res, false);
                    ans = SustitucionRegresiva(res);
                    NameMatrix("");
                    String text1 = "";
                    for (int i = 0; i < marcas.length; i++) {
                        text1 = text1 + "  " + "X" + String.valueOf(marcas[i]);
                    }
                    NameMatrix(text1);
                    ImprimirArray(ans);
                    break;
                case "elimGPT":
                    res = EliminacionGauss(aumen, 2);
                    if(!paso)Imprimir(res, false);
                    ans = SustitucionRegresiva(res);
                    NameMatrix("");
                    String text2 = "";
                    for (int i = 0; i < marcas.length; i++) {
                        text2 = text2 + "  " + "X" + String.valueOf(marcas[i]);
                    }
                    NameMatrix(text2);
                    ImprimirArray(ans);
                    break;
                case "gaussSeidel":
                    iterations = bundle.getInt("iterations");
                    tolerance = bundle.getDouble("tolerance");
                    x0 = (ArrayList<Double>) bundle.getSerializable("x0");
                    relas = (double)bundle.getDouble("relax");
                    double[] x0a = new double[x0.size()];
                    for(int i = 0; i < x0a.length; i++) {
                        x0a[i] = x0.get(i);
                    }
                    if(!paso) {
                        res = SeidelJacobi(x0a, tolerance, iterations, matriz, results, relas, false);
                        ImprimirRes();
                        ImprimirArray(res[0]);
                        NameMatrix("Valor de disp: " + String.valueOf(res[1]));
                    }
                    break;
                case "jacobi":
                    iterations = bundle.getInt("iterations");
                    tolerance = bundle.getDouble("tolerance");
                    x0 = (ArrayList<Double>) bundle.getSerializable("x0");
                    relas = (double)bundle.getDouble("relax");
                    double[] x0a1 = new double[x0.size()];
                    for(int i = 0; i < x0a1.length; i++) {
                        x0a1[i] = x0.get(i);
                    }
                    if(!paso) {
                        res = SeidelJacobi(x0a1, tolerance, iterations, matriz, results, relas, true);
                        ImprimirRes();
                        ImprimirArray(res[0]);
                        NameMatrix(String.valueOf(res[1]));
                    }
                    break;
                case "cholesky":
                    ans = CholeDoliCrout(matriz, results, 1);
                    if (!paso) {
                        NameMatrix("Matriz L");
                        Imprimir(l,true);
                        NameMatrix("Matriz U");
                        Imprimir(u,true);
                        ImprimirRes();
                        ImprimirArray(ans);
                    }
                    break;
                case "crout":
                    ans = CholeDoliCrout(matriz, results, 2);
                    if (!paso) {
                        NameMatrix("Matriz L");
                        Imprimir(l,true);
                        NameMatrix("Matriz U");
                        Imprimir(u,true);

                        ImprimirRes();
                        ImprimirArray(ans);
                    }
                    break;
                case "dolittle":
                    ans = CholeDoliCrout(matriz, results, 3);
                    if (!paso) {
                        NameMatrix("Matriz L");
                        Imprimir(l,true);
                        NameMatrix("Matriz U");
                        Imprimir(u,true);
                        ImprimirRes();
                        ImprimirArray(ans);
                    }
                    break;
            }
        } catch (Exception ex) {
            Snackbar.make(findViewById(R.id.activity_matriz_result),ex.getMessage(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }

    public void NameMatrix(String name) {
        TableLayout table = (TableLayout) findViewById(R.id.result_table);
        TableRow ttr = new TableRow(this);
        ttr.setMinimumHeight(120);
        TextView n = new TextView(this);
        n.setText(name);
        ttr.addView(n);
        table.addView(ttr);
    }

    public void ImprimirRes() {
        NameMatrix("");
        String text = "";
        for (int i = 0; i < matriz.length; i ++) {
            text = text + "  " + "X" + String.valueOf(i);
        }
        NameMatrix(text);
    }

    public double[][] EliminacionGauss(double[][] a, int piv) {
        int n = a.length;
        for (int k = 0; k < n - 1; k++) {
            if(piv == 1){
                a = PivoteoParcial(a,k);
            }
            if(piv == 2) {
                a = PivoteoTotal(a,k);
            }
            for (int i = k + 1; i < n; i++) {
                double mult = a[i][k] / a[k][k];
                for (int j = k; j < n + 1; j++) {
                    a[i][j] = a[i][j] - mult * a[k][j];
                }
            }
            if(paso) {
                NameMatrix("Iteracion: " + Integer.toString(k));
                Imprimir(a, false);
            }
        }
        return a;
    }

    public double[][] PivoteoParcial(double[][] a, int k) {
        double mayor  = Math.abs(a[k][k]);
        int filaMayor = k;
        int n = a.length;
        for (int i = k + 1; i < n; i++)
        {
            if(Math.abs(a[i][k]) > mayor)
            {
                mayor = a[i][k];
                filaMayor = i;
            }
        }
        if(mayor == 0) System.out.println("El sistema no tiene solucion unica");
        else if (filaMayor != k)
        {
            swapRows(a,filaMayor,k);
        }
        return a;
    }

    public double[][] PivoteoTotal(double[][] a, int k) {
        double mayor     = 0;
        int filaMayor    = k;
        int columnaMayor = k;
        int n            = a.length;
        marcas = new int[n];

        for (int i = 1; i <= n; i++){
            marcas[i-1] = i;
        }
        for (int i = k; i < n; i++) {
            for (int j = k; j < n; j++) {
                if(Math.abs(a[i][j]) > mayor)
                {
                    mayor = Math.abs(a[i][j]);
                    filaMayor    = i;
                    columnaMayor = j;
                }
            }
        }
        if(mayor == 0) System.out.println("El sistema no tiene una solucion unica");
        else {
            if(filaMayor != k) a = swapRows(a,filaMayor,k);
            if(columnaMayor != k){
                a = swapCol(a,columnaMayor,k);
                int temp = marcas[columnaMayor];
                marcas[columnaMayor] = marcas[k];
                marcas[k] = temp;
            }
        }
        return a;
    }

    public double[][] swapRows(double array[][], int rowA, int rowB) {
        double tmpRow[] = array[rowA];
        array[rowA] = array[rowB];
        array[rowB] = tmpRow;
        return array;
    }

    public double[][] swapCol(double array[][], int colA, int colB) {
        for (int i = 0; i < array.length; i++) {
            double temp = array[i][(colA)];
            array[i][colA] = array[i][(colB)];
            array[i][colB] = temp;
        }
        return array;
    }

    public double[] SustitucionRegresiva(double[][] ab) {
        int n = ab.length;
        double[] x = new double[n];
        x[n - 1] = ab[n - 1][n] / ab[n - 1][n - 1];
        for (int i = n - 1; i >= 0; i--) {
            double cont = 0;
            for (int j = i + 1; j < n; j++) {
                cont += (ab[i][j] * x[j]);
            }
            x[i] = (ab[i][n] - cont) / ab[i][i];
        }
        return x;
    }

    public double[] CholeDoliCrout(double[][] matCoef,double[] solCoef, int caso) throws Exception {

        int n = matCoef.length;
        double contK;
        double contJ;
        double contI;
        double cont;

        l = new double[n][n];
        u = new double[n][n];
        double[] res = new double[n];
        double[] aux = new  double[n];

        for(int k = 0; k < n; k++){
            contK = 0;
            for(int p = 0; p < k;p++){
                contK += (l[k][p] * u[p][k]);
            }
            switch (caso){
                case 1:
                    l[k][k] = Math.sqrt(matCoef[k][k] - contK);
                    u[k][k] = l[k][k];
                    break;
                case 2:
                    l[k][k] = matCoef[k][k] - contK;
                    u[k][k] = 1;
                    break;
                case 3:
                    l[k][k] = 1;
                    u[k][k] = matCoef[k][k] - contK;
                    break;
                default:
                    break;
            }

            for(int i = k+1; i < n;i++){
                contI = 0;
                for(int p = 0; p <= k;p++){
                    contI += (l[i][p] * u[p][k]);
                }
                if(l[k][k] != 0){
                    l[i][k] = (matCoef[i][k] - contI)/u[k][k];
                }else{
                    throw new Exception("Sistema sin solucion");
                }
            }

            for(int j = k+1; j < n;j++){
                contJ = 0;
                for(int p = 0; p <= k;p++){
                    contJ += (l[k][p] * u[p][j]);
                }

                if(l[k][k] != 0) {
                    u[k][j] = (matCoef[k][j] - contJ) / l[k][k];
                }else{
                    throw new Exception("Sistema sin solucion");
                }
            }
            if(paso) {
                NameMatrix("L iteracion: " + Integer.toString(k));
                Imprimir(l, true);
                NameMatrix("U iteracion: " + Integer.toString(k));
                Imprimir(u, true);
            }
        }

        for(int i = 0;i < n; i++){    //Sust Progresiva
            cont = 0;
            for(int j = 0; j < i; j++){
                cont += l[i][j] * aux[j];
            }

            aux[i] = (solCoef[i]-cont)/l[i][i];
        }
        for(int i = n-1;i >= 0; i--){   // Sust Regresiva
            cont = 0;
            for(int j = n-1; j > i; j--){
                cont += u[i][j] * res[j];
            }
            res[i] = (aux[i]-cont)/u[i][i];
        }
        return res;
    }

    public double[][] SeidelJacobi(double[] x0,double tol, int iter, double[][] matA, double[] vecB, double rel, boolean jac) throws Exception {

        int cont = 0;
        double disp = tol +1;
        double[] x1;
        double[] aux;

        if(iter > 0){
            if(tol > 0){

                while(disp > tol && cont < iter){

                    if(jac){
                        x1 = calcX1Jac(x0, matA, vecB);
                    }else{
                        x1 = calcX1Seid(x0, matA, vecB);
                    }

                    aux = restaVec(x1,x0);
                    disp = calcNorma(aux)/calcNorma(x1);
                    if(paso){
                        NameMatrix("Iteracion: " + cont);
                        ImprimirArray(x1);
                    }
                    for(int i = 0; i < x0.length; i++){

                        x1[i] = (rel*x1[i])+((1-rel)*x0[1]);

                    }

                    x0 = x1;
                    cont++;
                }
                if(disp < tol){

                    double[][] res = {x0,{disp}};
                    return res;

                }else{
                    throw new Exception("Error: Exedio iteraciones");
                }


            }else{
                throw new Exception("Tolerancia menor que 0");
            }
        }else{
            throw new Exception("Iteraciones menores o iguales que 0");
        }
    }

    public double[] calcX1Jac(double[] x0, double[][] matA, double[] vecB) throws Exception {
        int n = x0.length;
        double[] res = new double[n];
        double cont;

        for(int i = 0; i < n; i++){
            cont = 0;
            for(int j = 0; j < n; j++){
                if(i != j){
                    cont += matA[i][j]*x0[j];
                }
            }
            if(matA[i][i] != 0){
                res[i] = (vecB[i] - cont)/matA[i][i];
            }else{
                throw new Exception("El sistema posiblemente no tiene solucion");
            }
        }

        return res;

    }

    public double[] calcX1Seid(double[] x0, double[][] matA, double[] vecB) throws Exception {

        int n = x0.length;
        double[] res = new double[n];
        double cont;

        for(int i = 0; i < n; i++){
            res[i] = x0[i];
        }

        for(int i = 0; i < n; i++){
            cont = 0;
            for(int j = 0; j < n; j++){
                if(i != j){
                    cont += matA[i][j]*res[j];
                }
            }
            if(matA[i][i] != 0){
                res[i] = (vecB[i] - cont)/matA[i][i];
            }else{
                throw new Exception("El sistema posiblemente no tiene solucion");
            }
        }

        return res;

    }

    public double calcNorma(double[] x0) {

        double cont = 0;

        for(int i = 0; i < x0.length;i++){

            cont += Math.abs(x0[i]) * Math.abs(x0[i]);

        }
        return Math.sqrt(cont);
    }

    public double[] restaVec(double[] x0, double[] x1) {

        int n = x0.length;
        double[] res = new double[n];

        for(int i = 0; i < n; i++){
            res[i] = x0[i] - x1[i];
        }

        return res;
    }



    public void Imprimir(double[][] a, boolean isCuadrada) {
        TableLayout table = (TableLayout) findViewById(R.id.result_table);
        for (int i = 0; i < a.length; i++) {
            TableRow ttr = new TableRow(this);
            TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            DecimalFormat df = new DecimalFormat( "#########0.00" );
            ttr.setLayoutParams(tlp);
            ttr.setBackgroundColor(Color.parseColor("#CFD8DC"));
            for (int j = 0; j < a.length; j++) {
                TextView n = new TextView(this);
                n.setBackgroundResource(R.drawable.table_border_title);
                n.setText(String.valueOf(df.format(a[i][j])));
                //n.setText(String.valueOf(a[i][j]));
                n.setHeight(50);
                n.setWidth(120);
                ttr.addView(n);
            }
            if(!isCuadrada) {
                TextView n = new TextView(this);
                n.setBackgroundResource(R.drawable.edit_border);
                n.setText(String.valueOf(df.format(a[i][tamaño])));
                //n.setText(String.valueOf(a[i][tamaño]));
                n.setHeight(50);
                n.setWidth(120);
                ttr.addView(n);
            }
            table.addView(ttr);
        }
    }

    public void ImprimirArray(double[] a){
        TableLayout table = (TableLayout) findViewById(R.id.result_table);
        TableRow ttr = new TableRow(this);
        TableRow.LayoutParams tlp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
        DecimalFormat df = new DecimalFormat( "#########0.00" );
        ttr.setLayoutParams(tlp);
        for(int i = 0; i < a.length; i++){
            TextView n = new TextView(this);
            n.setText(String.valueOf(df.format(a[i])));
            //n.setText(String.valueOf(a[i]));
            n.setHeight(50);
            n.setWidth(120);
            n.setBackgroundColor(Color.parseColor("#CFD8DC"));
            ttr.addView(n);
        }
        table.addView(ttr);
    }
}
