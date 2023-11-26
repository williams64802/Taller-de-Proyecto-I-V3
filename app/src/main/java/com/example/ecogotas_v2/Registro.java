package com.example.ecogotas_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    private EditText edtReEmail, edtReContrasena, edtReVueCont;
    private Button btnRegistrarse;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        firebaseAuth = FirebaseAuth.getInstance();

        edtReEmail = findViewById(R.id.edtReEmail);
        edtReContrasena = findViewById(R.id.edtReContrasena);
        edtReVueCont = findViewById(R.id.edtReVueCont);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        final String email = edtReEmail.getText().toString().trim();
        String contrasena = edtReContrasena.getText().toString().trim();
        String repetirContrasena = edtReVueCont.getText().toString().trim();

        if (contrasena.equals(repetirContrasena)) {
            firebaseAuth.createUserWithEmailAndPassword(email, contrasena)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(Registro.this, Datos.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(Registro.this, "Error en el registro: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }
    }
}
