package com.example.ecogotas_v2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NewsAdapter newsAdapter;
    private List<NewsModel> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        
        recyclerView = findViewById(R.id.recyclerView);
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(newsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(newsAdapter);

        fetchNews();
    }

    private void fetchNews() {

        NewsApi newsApi = new NewsApi();

        String apiUrl = "https://newsapi.org/v2/everything?q=tesla&from=2023-10-24&sortBy=publishedAt&apiKey=3bb0c1f34d0f4f55a26fe5292fa45136";

        new Thread(() -> {
            // Realizar la solicitud a la API y obtener la respuesta como cadena JSON
            String newsJson = newsApi.fetchNewsData(apiUrl);

            if (newsJson != null && !newsJson.isEmpty()) {
                // Analizar la cadena JSON y actualizar la lista de noticias
                updateNewsList(newsJson);
            } else {
                // Manejar caso de error
                runOnUiThread(() -> Toast.makeText(this, "Error al obtener noticias", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private void updateNewsList(String newsJson) {
        try {
            // Analizar la cadena JSON y convertirla a una lista de objetos NewsModel
            List<NewsModel> newNewsList = new ArrayList<>();
            JSONArray articlesArray = new JSONObject(newsJson).getJSONArray("articles");

            for (int i = 0; i < articlesArray.length(); i++) {
                try {
                    JSONObject articleObject = articlesArray.getJSONObject(i);

                    // Obtener los valores reales del objeto JSON
                    String title = articleObject.getString("title");
                    String description = articleObject.getString("description");
                    String imageUrl = articleObject.getString("urlToImage");

                    // Crear un objeto NewsModel y establecer los valores reales
                    NewsModel news = new NewsModel();
                    news.setTitle(title);
                    news.setDescription(description);
                    news.setImageUrl(imageUrl);

                    // Agregar el objeto NewsModel a la lista
                    newNewsList.add(news);

                } catch (JSONException e) {
                    e.printStackTrace();
                    // Manejar la excepción (por ejemplo, omitir este artículo o agregar valores predeterminados)
                }
            }

            // Actualizar la lista de noticias en el hilo principal
            runOnUiThread(() -> {
                newsList.clear();
                newsList.addAll(newNewsList);
                newsAdapter.notifyDataSetChanged();
            });
        } catch (JSONException e) {
            e.printStackTrace();
            // Manejar errores de parsing de JSON
            runOnUiThread(() -> Toast.makeText(this, "Error al analizar datos de noticias", Toast.LENGTH_SHORT).show());
        }
    }
}
