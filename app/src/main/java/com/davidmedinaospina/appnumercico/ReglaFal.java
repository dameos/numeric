package com.davidmedinaospina.appnumercico;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ReglaFal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regla_fal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final TextView mText = (TextView) findViewById(R.id.textView9);
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
