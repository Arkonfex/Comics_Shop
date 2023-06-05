package com.comicsshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.comicsshop.Cart.CartActivity;
import com.comicsshop.Comic.Comic;
import com.comicsshop.Comic.ComicData;
import com.comicsshop.Comic.ComicsAdapter;
import com.comicsshop.Filter.Filter;
import com.comicsshop.Filter.FilterOptions;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewComics;
    private ComicsAdapter comicAdapter;
    private DatabaseHelper databaseHelper;
    private FilterOptions filterOptions;
    private  CartActivity cartActivity;

    private RadioButton radioAuthorName;

    private RadioButton radioSortDescending;

    private RadioButton radioSortAscending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ініціалізація DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

/*        // Удаление старой базы данных
        this.deleteDatabase("comics.db");*/

        // Создание нового экземпляра DatabaseHelper
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        // Загрузка і перевірка наявності даних у базі даних
        if (databaseHelper.getComicCount() == 0) {
            // Якщо база даних порожня, завантажуємо дані
            ComicData comicData = new ComicData(databaseHelper);
            comicData.loadComicData();
        }

        // Отримання списку коміксів з бази даних
        List<Comic> comics = databaseHelper.getAllComics();

        // Створення адаптера та встановлення його для RecyclerView
        comicAdapter = new ComicsAdapter(this, comics);
        recyclerViewComics = findViewById(R.id.comicsRecyclerView);
        recyclerViewComics.setAdapter(comicAdapter);
        recyclerViewComics.setLayoutManager(new LinearLayoutManager(this));

        Button filterButton = findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filter filterDialog = new Filter(MainActivity.this, comicAdapter, databaseHelper);
                filterDialog.showFilterDialog();

                // Отримання вибраних опцій фільтру
                filterOptions = filterDialog.getSelectedOptions();

                // Фільтрація коміксів та оновлення списку
                comicAdapter.showFilteredComics(filterOptions);
            }
        });


        Button btnShowCart = findViewById(R.id.openCartButton);
        btnShowCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Очистка базы данных при закрытии активности
        databaseHelper.deleteAllComics();
        // Закрытие базы данных при уничтожении активности
        databaseHelper.close();
    }
}
