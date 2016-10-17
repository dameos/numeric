package com.davidmedinaospina.appnumercico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
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
        final TextView mText   = (TextView)findViewById(R.id.textView3);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        Bundle bundle = getIntent().getExtras();
        final String exp = bundle.getString("expr");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mText.setText(exp);
            }
        });
    }

}
