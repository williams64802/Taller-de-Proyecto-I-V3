package com.example.ecogotas_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private TextView registrar;
    private EditText edtReEmail, edtReContrasena;
    private Button btnIniciarSesion;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        edtReEmail = findViewById(R.id.edtReEmail);
        edtReContrasena = findViewById(R.id.edtReContrasena);
        btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        registrar=(TextView) findViewById(R.id.txtRegistrar);

        btnIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Registro.class);
                startActivity(intent);
            }
        });

    }

    private void iniciarSesion() {
        String email = edtReEmail.getText().toString().trim();
        String contrasena = edtReContrasena.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, contrasena)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Datos.class);
                            startActivity(intent);
                        } else {
                            // Si falla el inicio de sesión, muestra un mensaje al usuario.
                            Toast.makeText(Login.this, "Error en el inicio de sesión: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}