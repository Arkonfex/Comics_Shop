package com.comicsshop.Comic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.comicsshop.DatabaseHelper;
import com.comicsshop.Filter.FilterOptions;
import com.comicsshop.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComicsAdapter extends RecyclerView.Adapter<ComicsAdapter.ComicsViewHolder> {
    private DatabaseHelper databaseHelper;

    private Context context;
    private List<Comic> comicsList;
    private List<Comic> filteredComics;
    private FilterOptions filterOptions;
    private boolean sortByPriceDescending;
    private int position;

    public ComicsAdapter(Context context, List<Comic> comicsList) {
        this.context = context;
        this.comicsList = comicsList;
        this.filteredComics = new ArrayList<>(comicsList); // Инициализация списка filteredComics
        databaseHelper = new DatabaseHelper(context);
        sortByPriceDescending = false;
    }

    public void showFilteredComics(FilterOptions options) {
        int selectedFilter = options.getSelectedFilter();

        if (selectedFilter != -1) {
            List<Comic> filteredComics;

            switch (selectedFilter) {
                case FilterOptions.FILTER_OPTION_1:
                    // Применить фильтр 1
                    filteredComics = databaseHelper.getFilteredComicsByOption1();
                    break;
                case FilterOptions.FILTER_OPTION_2:
                    // Применить фильтр 2
                    filteredComics = databaseHelper.getFilteredComicsByOption2();
                    break;
                case FilterOptions.FILTER_OPTION_3:
                    // Применить фильтр 3
                    filteredComics = databaseHelper.getFilteredComicsByOption3();
                    break;
                default:
                    // Если выбран недопустимый фильтр, отобразить все комиксы
                    filteredComics = comicsList;
                    break;
            }

            updateComics(filteredComics);
        }
    }

    public void updateComics(List<Comic> comicsList) {
        this.comicsList = comicsList;
        this.filteredComics = new ArrayList<>(comicsList); // Обновление списка filteredComics
        notifyDataSetChanged();
    }

    public void clear() {
        comicsList.clear();
        filteredComics.clear(); // Очистка списка filteredComics
        notifyDataSetChanged();
    }

    public void addAll(List<Comic> comicsList) {
        this.comicsList.addAll(comicsList);
        this.filteredComics.addAll(comicsList); // Добавление элементов в список filteredComics
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.comic_list, parent, false);
        int position = getItemCount(); // Get the current item position
        return new ComicsViewHolder(itemView, position);
    }
    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Comic comic = filteredComics.get(position);

        holder.titleTextView.setText(comic.getTitle());
        holder.authorTextView.setText(comic.getAuthorName() + " " + comic.getAuthorLastName());
        holder.publisherTextView.setText(String.valueOf(comic.getPublisherId()));
        holder.countryTextView.setText(String.valueOf(comic.getPublisherCountry()));
        holder.releaseYearTextView.setText(String.valueOf(comic.getReleaseYear()));
        holder.priceTextView.setText(String.valueOf(comic.getPrice()));

    }

    @Override
    public int getItemCount() {
        return filteredComics != null ? filteredComics.size() : 0;
    }

    public class ComicsViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView, authorTextView, publisherTextView, countryTextView, releaseYearTextView, priceTextView;
        public ImageButton addToCartButton;


        public ComicsViewHolder(@NonNull View itemView, int position) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            authorTextView = itemView.findViewById(R.id.authorTextView);
            publisherTextView = itemView.findViewById(R.id.publisherTextView);
            countryTextView = itemView.findViewById(R.id.countryTextView);
            releaseYearTextView = itemView.findViewById(R.id.yearTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);

            addToCartButton = itemView.findViewById(R.id.addToCartButton);

            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && position < filteredComics.size()) {
                        Comic comic = filteredComics.get(position);
                        List<Comic> singleComicList = new ArrayList<>();
                        singleComicList.add(comic);
                        databaseHelper.addToOrder(singleComicList);
                    }
                }
            });

        }
    }
    public void filterComics(FilterOptions filterOptions, String searchQuery) {
        // Filter the comics and get the filtered list
        List<Comic> filteredComics = databaseHelper.getFilteredComics(filterOptions,searchQuery);

        // Check if the filteredComics list is null or empty
        if (filteredComics != null && position != RecyclerView.NO_POSITION) {
            Comic comic = filteredComics.get(position);
            List<Comic> singleComicList = new ArrayList<>();
            singleComicList.add(comic);
            databaseHelper.addToOrder(singleComicList);
        }
    }

    public void setSortByPriceDescending(boolean descending) {
        sortByPriceDescending = descending;
        applyPriceSorting();
    }

    public void applyPriceSorting() {
        if (sortByPriceDescending) {
            Collections.sort(filteredComics, new Comparator<Comic>() {
                @Override
                public int compare(Comic comic1, Comic comic2) {
                    return Double.compare(comic2.getPrice(), comic1.getPrice());
                }
            });
        } else {
            Collections.sort(filteredComics, new Comparator<Comic>() {
                @Override
                public int compare(Comic comic1, Comic comic2) {
                    return Double.compare(comic1.getPrice(), comic2.getPrice());
                }
            });
        }

        notifyDataSetChanged();    }
}
