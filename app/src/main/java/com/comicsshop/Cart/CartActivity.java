package com.comicsshop.Cart;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.comicsshop.Comic.Comic;
import com.comicsshop.DatabaseHelper;
import com.comicsshop.R;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private final List<CartItem> cartItems = new ArrayList<>();
    private final Context context = CartActivity.this;
    private DatabaseHelper databaseHelper;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;

    // Добавьте объявления переменных COLUMN_ORDER_ID, COLUMN_ORDER_QUANTITY и других необходимых переменных

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Создание экземпляра DatabaseHelper
        databaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.cartRecyclerView);
        cartAdapter = new CartAdapter(context, cartItems);

        // Получите список элементов корзины и установите его в адаптер
        List<CartItem> items = getCartItems(); // Ваш метод для получения элементов корзины
        cartAdapter.setCartItems(items);

        // Установите макет cart_item.xml для элементов списка
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(cartAdapter);
    }

    public void showCartDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Cart");

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);

        builder.setView(recyclerView);
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    // Метод для получения списка элементов корзины
    private List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_ORDERS, null, DatabaseHelper.COLUMN_ORDER_TYPE + "=?", new String[]{"cart"}, null, null, null);

        while (cursor.moveToNext()) {
            long orderId = cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_ID));
            int quantity = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COLUMN_ORDER_QUANTITY));

            // Получите данные о комиксе из таблицы "комиксы" по orderId
            Cursor comicCursor = db.query(DatabaseHelper.TABLE_COMICS, null, DatabaseHelper.COLUMN_COMIC_ID + "=?", new String[]{String.valueOf(orderId)}, null, null, null);
            if (comicCursor.moveToFirst()) {
                String title = comicCursor.getString(comicCursor.getColumnIndex(DatabaseHelper.COLUMN_TITLE));
                String author = comicCursor.getString(comicCursor.getColumnIndex(DatabaseHelper.COLUMN_AUTHOR_ID));
                double price = comicCursor.getDouble(comicCursor.getColumnIndex(DatabaseHelper.COLUMN_PRICE));

                // Создайте объект CartItem и добавьте его в список
                CartItem cartItem = new CartItem(orderId, title, author, price, quantity);
                cartItems.add(cartItem);
            }
            comicCursor.close();
        }

        cursor.close();
        db.close();

        return cartItems;
    }
}
