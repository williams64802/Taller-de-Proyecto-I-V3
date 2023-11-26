package com.example.ecogotas_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Advertencia extends AppCompatActivity {

    private Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertencia);

        volver=(Button) findViewById(R.id.RegresarM);


        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Advertencia.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
