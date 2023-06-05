package com.comicsshop.Filter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.EditText;

import com.comicsshop.Comic.Comic;
import com.comicsshop.Comic.ComicsAdapter;
import com.comicsshop.DatabaseHelper;
import com.comicsshop.R;
import com.comicsshop.Filter.FilterOptions;


import java.util.List;

public class Filter {
    private DatabaseHelper databaseHelper;
    private Context context;
    private ComicsAdapter comicAdapter;
    private RadioButton radioAuthorName;
    private RadioButton radioSortDescending;
    private RadioButton radioSortAscending;

    public Filter(Context context, ComicsAdapter comicAdapter, DatabaseHelper databaseHelper) {
        this.context = context;
        this.comicAdapter = comicAdapter;
        this.databaseHelper = databaseHelper;
    }


    public void filterComics(FilterOptions filterOptions, String searchQuery) {
        // Очищаем текущий список комиксов
        comicAdapter.clear();

        // Фильтруем комиксы во временной таблице
        List<Comic> filteredComics = databaseHelper.getFilteredComics(filterOptions, searchQuery);

        // Добавляем отфильтрованные комиксы в адаптер
        comicAdapter.addAll(filteredComics);

        // Удаляем временную таблицу
        databaseHelper.dropTempTable();

        // Обновляем отображение списка комиксов
        comicAdapter.notifyDataSetChanged();
    }

    public void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.filter_btn, null);
        builder.setView(dialogView);

        radioAuthorName = dialogView.findViewById(R.id.radioAuthorName);
        EditText etAuthorName = dialogView.findViewById(R.id.etAuthorName);
        radioSortAscending = dialogView.findViewById(R.id.radioSortAscending);
        radioSortDescending = dialogView.findViewById(R.id.radioSortDescending);

        builder.setPositiveButton("Применить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FilterOptions filterOptions = new FilterOptions(1);
                filterOptions.setFilterByAuthorName(radioAuthorName.isChecked());
                filterOptions.setSortByPriceDescending(radioSortDescending.isChecked());
                filterOptions.setSortByPriceAscending(radioSortAscending.isChecked());

                // Установка выбранного фильтра
                if (radioAuthorName.isChecked()) {
                    filterOptions.setSelectedFilter(FilterOptions.FILTER_OPTION_1);
                    String authorName = etAuthorName.getText().toString().trim();
                    filterOptions.setAuthorName(authorName);
                } else if (radioSortAscending.isChecked()) {
                    filterOptions.setSelectedFilter(FilterOptions.FILTER_OPTION_2);
                } else if (radioSortDescending.isChecked()) {
                    filterOptions.setSelectedFilter(FilterOptions.FILTER_OPTION_3);
                }

                String searchQuery = ""; // Инициализация переменной searchQuery
                filterComics(filterOptions, searchQuery);
            }
        });

        builder.setNegativeButton("Отмена", null);

        builder.create().show();
    }


    public FilterOptions getSelectedOptions() {
        FilterOptions selectedOptions = new FilterOptions(1);
        selectedOptions.setFilterByAuthorName(radioAuthorName.isChecked());
        selectedOptions.setSortByPriceDescending(radioSortDescending.isChecked());
        selectedOptions.setSortByPriceAscending(radioSortAscending.isChecked());

        return selectedOptions;
    }

}
