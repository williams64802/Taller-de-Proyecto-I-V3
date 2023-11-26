package com.example.ecogotas_v2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class ChatBot extends AppCompatActivity {

    private RecyclerView recyclerViewChat;
    private ChatAdapter chatAdapter;
    private EditText editTextMessage;
    private Button buttonSendMessage;

    private List<String> messages;

    // Clave de API de OpenAI
    private static final String OPENAI_API_KEY = ApiKeyProvider.OPENAI_API_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_bot);

        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSendMessage = findViewById(R.id.buttonSendMessage);

        messages = new ArrayList<>();
        chatAdapter = new ChatAdapter(messages);

        recyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewChat.setAdapter(chatAdapter);

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String userMessage = editTextMessage.getText().toString();
        messages.add(userMessage);
        chatAdapter.notifyItemInserted(messages.size() - 1);
        editTextMessage.getText().clear();

        new OpenAIApiTask().execute(userMessage);
    }

    private class OpenAIApiTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String userMessage = params[0];
            String apiKey = "Bearer " + OPENAI_API_KEY;
            String apiUrl = "https://api.openai.com/v1/chat/completions";

            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json; charset=utf-8");

            // Construir el cuerpo de la solicitud
            String requestBody = "{\"model\": \"gpt-3.5-turbo\", \"messages\": [{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}, {\"role\": \"user\", \"content\": \"" + userMessage + "\"}]}";
            RequestBody body = RequestBody.create(mediaType, requestBody);

            // Construir la solicitud
            Request request = new Request.Builder()
                    .url(apiUrl)
                    .post(body)
                    .addHeader("Authorization", apiKey)
                    .build();

            try {
                // Realizar la solicitud y obtener la respuesta
                Response response = client.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            // Manejar la respuesta de la API y agregar la respuesta del bot a la lista
            if (result != null) {
                try {
                    String botResponse = new JSONObject(result).getString("choices");
                    messages.add(botResponse);
                    chatAdapter.notifyItemInserted(messages.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}