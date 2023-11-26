package com.example.ecogotas_v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NewsApi {
    private static final String API_KEY = "3bb0c1f34d0f4f55a26fe5292fa45136";
    private static final String SEARCH_KEYWORD = "water";
    private static final String API_URL = "https://newsapi.org/v2/everything?q=tesla&from=2023-10-24&sortBy=publishedAt&apiKey=3bb0c1f34d0f4f55a26fe5292fa45136" + SEARCH_KEYWORD + "&apiKey=" + API_KEY;

    public String fetchNewsData(String apiUrl) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJsonString = null;

        try {
            // Construir la URL de la API
            URL url = new URL(apiUrl);

            // Inicializar la conexión
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Leer la respuesta de la API
            InputStream inputStream = urlConnection.getInputStream();
            StringBuilder builder = new StringBuilder();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            newsJsonString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return newsJsonString;
    }
}

