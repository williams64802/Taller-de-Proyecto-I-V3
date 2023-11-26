package com.example.ecogotas_v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {

    private LinearLayout layoutEstadistica,layoutNoticias,layoutChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        LinearLayout layoutEstadistica = findViewById(R.id.layoutEstadistica);
        layoutEstadistica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Estadistica.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutNoticias = findViewById(R.id.layoutNoticias);
        layoutNoticias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, NewsActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout layoutChat = findViewById(R.id.layoutChat);
        layoutChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, ChatBot.class);
                startActivity(intent);
            }
        });

    }
}
