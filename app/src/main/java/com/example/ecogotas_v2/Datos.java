package com.example.ecogotas_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Datos extends AppCompatActivity {


    private EditText edtapodo;

    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos);

        edtapodo=(EditText)findViewById(R.id.edtApodo);
        btnContinuar=(Button)findViewById(R.id.btnContinuar);


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Datos.this, Menu.class);
                startActivity(intent);
            }
        });
    }

}