package com.example.ecogotas_v2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private List<NewsModel> newsList;

    public NewsAdapter(List<NewsModel> newsList) {
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflar la vista para cada elemento de la lista
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list_item, parent, false);

        return new NewsViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel news = newsList.get(position);

        // Asignar los datos a las vistas en NewsViewHolder
        holder.titleTextView.setText(news.getTitle());
        holder.descriptionTextView.setText(news.getDescription());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView;
        private TextView descriptionTextView;
        private ImageView newsImageView;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar vistas y otros elementos aquí
            // ...
        }
    }
}