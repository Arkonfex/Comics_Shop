package com.comicsshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;


import com.comicsshop.Cart.CartItem;
import com.comicsshop.Comic.Comic;
import com.comicsshop.Filter.FilterOptions;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "comics.db";
    private static final int DATABASE_VERSION = 1;

    // Таблиця для користувача
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_EMAIL = "email";

    // Таблиця для авторів
    private static final String TABLE_AUTHORS = "authors";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    private static final String COLUMN_FIRST_NAME = "first_name";
    private static final String COLUMN_LAST_NAME = "last_name";

    // Таблиця для коміксов
    public static final String TABLE_COMICS = "comics";
    public static final String COLUMN_COMIC_ID = "comic_id";
    public static final String COLUMN_TITLE = "title";
    private static final String COLUMN_AUTHOR_ID_FK = "author_id";
    private static final String COLUMN_PUBLISHER_ID_FK = "publisher_id";
    private static final String COLUMN_RELEASE_YEAR = "release_year";
    public static final String COLUMN_PRICE = "price";
    private static final String COLUMN_COUNTRY = "country";

    // Таблиця для видавництва
    private static final String TABLE_PUBLISHERS = "publishers";
    private static final String COLUMN_PUBLISHER_ID = "publisher_id";
    private static final String COLUMN_PUBLISHER_NAME = "publisher_name";
    //private static final String COLUMN_COUNTRY = "country";

    // Таблиця для замовлення
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_QUANTITY = "quantity";
    public static final String COLUMN_ORDER_TYPE = "order_type";


    private Context context;

    // Метод для вставки комікса в таблицю коміксів
    private static final String CREATE_TABLE_COMICS = "CREATE TABLE " + TABLE_COMICS + "("
            + COLUMN_COMIC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_AUTHOR_ID_FK + " INTEGER,"
            + COLUMN_PUBLISHER_ID_FK + " INTEGER,"
            + COLUMN_RELEASE_YEAR + " INTEGER,"
            + COLUMN_COUNTRY + " TEXT,"
            + COLUMN_PRICE + " REAL,"
            + "FOREIGN KEY(" + COLUMN_AUTHOR_ID_FK + ") REFERENCES " + TABLE_AUTHORS + "(" + COLUMN_AUTHOR_ID + "))";


    // Метод для вставки автора в таблицю авторів
    private static final String CREATE_TABLE_AUTHORS = "CREATE TABLE " + TABLE_AUTHORS + "("
            + COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_FIRST_NAME + " TEXT,"
            + COLUMN_LAST_NAME + " TEXT)";

    // Метод для вставки видавник в таблицю видавництва
    private static final String CREATE_TABLE_PUBLISHERS = "CREATE TABLE " + TABLE_PUBLISHERS + "("
            + COLUMN_PUBLISHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PUBLISHER_NAME + " TEXT,"
            + COLUMN_COUNTRY + " TEXT)";


    // Метод для створення таблиці користувачів
    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_USERNAME + " TEXT PRIMARY KEY,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_EMAIL + " TEXT"
            + ")";

    // Метод для створення таблиці замовлення
    private static final String CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + "("
            + COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_COMIC_ID + " INTEGER,"
            + COLUMN_TITLE + " TEXT,"
            + COLUMN_AUTHOR_ID + " INTEGER,"
            + COLUMN_ORDER_QUANTITY + " INTEGER,"
            + COLUMN_ORDER_TYPE + " TEXT,"
            + COLUMN_PRICE + " REAL"
            + ")";

    ////

    private boolean sortByPriceAscending;
    private boolean sortByPriceDescending;
    private Comic[] comicsList;
    ////

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Створення таблиці користувачів
        db.execSQL(CREATE_TABLE_USERS);

        // Створення таблиці коміксів
        db.execSQL(CREATE_TABLE_COMICS);

        // Створення таблиці авторів
        db.execSQL(CREATE_TABLE_AUTHORS);

        // Створення таблиці видавців
        db.execSQL(CREATE_TABLE_PUBLISHERS);

        // Створення таблиці корзинки
        db.execSQL(CREATE_TABLE_ORDERS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        // Удаляем старые таблицы при обновлении базы данных
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMICS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUBLISHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

        // Создаем таблицы заново
        onCreate(db);
    }

    // Методы для пользователей
    public boolean checkCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        boolean result = (cursor.getCount() > 0);

        cursor.close();
        db.close();

        return result;
    }
    public static class User {
        private String username;
        private String password;
        private String email;

        public User(String username, String password, String email) {
            this.username = username;
            this.password = password;
            this.email = email;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
    public boolean isUserExists(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        boolean result = (cursor.getCount() > 0);

        cursor.close();
        db.close();

        return result;
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_EMAIL, user.getEmail());

        db.insert(TABLE_USERS, null, values);

        db.close();
    }
    public User getUserByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor.moveToFirst()) {
            String retrievedUsername = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            String retrievedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String retrievedEmail = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));

            // Создаем объект User на основе полученных данных из базы данных
            user = new User(retrievedUsername, retrievedPassword, retrievedEmail);
        }

        cursor.close();
        db.close();

        return user;
    }

    //Метод для комикса
    public long insertComic(String title, long authorId, long publisherId,String country, int releaseYear, double price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_AUTHOR_ID_FK, authorId);
        values.put(COLUMN_PUBLISHER_ID_FK, publisherId);
        values.put(COLUMN_COUNTRY, country);
        values.put(COLUMN_RELEASE_YEAR, releaseYear);
        values.put(COLUMN_PRICE, price);
        long comicId = db.insert(TABLE_COMICS, null, values);
        db.close();
        return comicId;
    }

    public List<Comic> getAllComics() {
        List<Comic> comics = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try (Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_COMICS, null)) {
            if (cursor.moveToFirst()) {
                do {
                    long comicId = cursor.getLong(cursor.getColumnIndex(COLUMN_COMIC_ID));
                    String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
                    int authorId = cursor.getInt(cursor.getColumnIndex(COLUMN_AUTHOR_ID_FK));
                    int publisherId = cursor.getInt(cursor.getColumnIndex(COLUMN_PUBLISHER_ID_FK));
                    int releaseYear = cursor.getInt(cursor.getColumnIndex(COLUMN_RELEASE_YEAR));
                    double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

                    String authorFirstName = getAuthorFirstName(authorId);
                    String authorLastName = getAuthorLastName(authorId);
                    String publisherName = getPublisherName(publisherId);
                    String country = getPublisherCountry(publisherId);


                    Comic comic = new Comic(comicId, title, authorId, publisherId, country, releaseYear,  price, authorFirstName, authorLastName, publisherName);
                    comics.add(comic);
                } while (cursor.moveToNext());
            }
        }

        return comics;
    }

    public int getComicCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_COMICS, null);
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }
    public void deleteAllComics() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("comics", null, null);
    }



    //Методы для Автора
    public long insertAuthor(String firstName, String lastName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FIRST_NAME, firstName);
        values.put(COLUMN_LAST_NAME, lastName);
        long authorId = db.insert(TABLE_AUTHORS, null, values);
        db.close();
        return authorId;
    }

    private String getAuthorFirstName(int authorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(authorId)};
        Cursor cursor = db.query(TABLE_AUTHORS, new String[]{COLUMN_FIRST_NAME}, COLUMN_AUTHOR_ID + "=?", selectionArgs, null, null, null);
        String firstName = "";

        if (cursor.moveToFirst()) {
            firstName = cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_NAME));
        }

        cursor.close();
        db.close();
        return firstName;
    }

    private String getAuthorLastName(int authorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(authorId)};
        Cursor cursor = db.query(TABLE_AUTHORS, new String[]{COLUMN_LAST_NAME}, COLUMN_AUTHOR_ID + "=?", selectionArgs, null, null, null);
        String lastName = "";

        if (cursor.moveToFirst()) {
            lastName = cursor.getString(cursor.getColumnIndex(COLUMN_LAST_NAME));
        }

        cursor.close();
        db.close();
        return lastName;
    }

    //Метод для издателя
    public long insertPublisher(String name, String country) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PUBLISHER_NAME, name);
        values.put(COLUMN_COUNTRY, country); // Добавлено объявление и инициализация переменной country
        long publisherId = db.insert(TABLE_PUBLISHERS, null, values);
        db.close();
        return publisherId;
    }


    private String getPublisherName(int publisherId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(publisherId)};
        Cursor cursor = db.query(TABLE_PUBLISHERS, new String[]{COLUMN_PUBLISHER_NAME}, COLUMN_PUBLISHER_ID + "=?", selectionArgs, null, null, null);
        String publisherName = "";

        if (cursor.moveToFirst()) {
            publisherName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));
        }

        cursor.close();
        db.close();
        return publisherName;
    }

    private String getPublisherCountry(long publisherId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] selectionArgs = {String.valueOf(publisherId)};
        Cursor cursor = db.query(TABLE_PUBLISHERS, new String[]{COLUMN_COUNTRY}, COLUMN_PUBLISHER_ID + "=?", selectionArgs, null, null, null);
        String country = "";

        if (cursor.moveToFirst()) {
            country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY));
        }

        cursor.close();
        db.close();
        return country;
    }


    //Метод для Сортування
    public Cursor rawQuery(String sql, String[] selectionArgs) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(sql, selectionArgs);
    }

    public List<Comic> getFilteredComics(FilterOptions options, String searchQuery) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = null;
        String[] selectionArgs = null;

        boolean sortByPriceAscending = false;
        boolean sortByPriceDescending = false;

        // Перевірка обраного метода сортування за назвою або сортуванням спадання чи зрсотання ціни

        if (!TextUtils.isEmpty(searchQuery)) {
            selection = COLUMN_TITLE + " LIKE ?";
            selectionArgs = new String[]{"%" + searchQuery + "%"};
        }
        if (options.getSelectedFilter() == FilterOptions.FILTER_OPTION_2) {
            sortByPriceAscending = true;
            sortByPriceDescending = false;
        } else if (options.getSelectedFilter() == FilterOptions.FILTER_OPTION_3) {
            sortByPriceAscending = false;
            sortByPriceDescending = true;
        }

        // Перевірка обраного метода сортування за ціною
        if (sortByPriceAscending) {
            selection = "price ASC";
        } else if (sortByPriceDescending) {
            selection = "price DESC";
        }
        Cursor cursor = db.query(TABLE_COMICS, null, null, null, null, null, selection);
        List<Comic> comics = new ArrayList<>();

        while (cursor.moveToNext()) {
            int comicId = cursor.getInt(cursor.getColumnIndex(COLUMN_COMIC_ID));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            int authorId = cursor.getInt(cursor.getColumnIndex(COLUMN_AUTHOR_ID_FK));
            int publisherId = cursor.getInt(cursor.getColumnIndex(COLUMN_PUBLISHER_ID_FK));
            int releaseYear = cursor.getInt(cursor.getColumnIndex(COLUMN_RELEASE_YEAR));
            double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));

            String authorFirstName = getAuthorFirstName(authorId);
            String authorLastName = getAuthorLastName(authorId);
            String publisherName = getPublisherName(publisherId);
            String country = getPublisherCountry(publisherId);

            Comic comic = new Comic(comicId, title, authorId, publisherId, country, releaseYear, price, authorFirstName, authorLastName, publisherName);
            comics.add(comic);
        }

        cursor.close();
        db.close();
        return comics;
    }
    // Методи фільтрації коміксів(всього 3 способи)
    public List<Comic> getFilteredComicsByOption1() {
        List<Comic> filteredComics = new ArrayList<>();

        FilterOptions filterOptions = new FilterOptions(1);

        for (Comic comic : comicsList) {
            if (comic.getFilterOption() == filterOptions) {
                filteredComics.add(comic);
            }
        }

        return filteredComics;
    }

    public List<Comic> getFilteredComicsByOption2() {
        List<Comic> filteredComics = new ArrayList<>();

        FilterOptions filterOptions = new FilterOptions(2);

        for (Comic comic : comicsList) {
            if (comic.getFilterOption() == filterOptions) {
                filteredComics.add(comic);
            }
        }

        return filteredComics;
    }

    public List<Comic> getFilteredComicsByOption3() {
        List<Comic> filteredComics = new ArrayList<>();

        FilterOptions filterOptions = new FilterOptions(3);

        for (Comic comic : comicsList) {
            if (comic.getFilterOption() == filterOptions) {
                filteredComics.add(comic);
            }
        }

        return filteredComics;
    }

    //
    public void addToOrder(List<Comic> comicList) {
        SQLiteDatabase db = this.getWritableDatabase();

        for (Comic comic : comicList) { // Изменение переменной comicsList на comicList
            // Получите данные о комиксе из объекта Comic
            long comicId = comic.getComicId();
            String title = comic.getTitle();
            String author = comic.getAuthorName();
            double price = comic.getPrice();

            // Создайте объект ContentValues для добавления данных в таблицу
            ContentValues values = new ContentValues();
            values.put(COLUMN_COMIC_ID, comicId);
            values.put(COLUMN_TITLE, title);
            values.put(COLUMN_AUTHOR_ID, author);
            values.put(COLUMN_PRICE, price);
            values.put(COLUMN_ORDER_TYPE, "cart"); // Установите тип заказа как "cart"

            // Вставьте данные в таблицу "заказы"
            db.insert(TABLE_ORDERS, null, values);
        }
        db.close();
    }

    public void removeFromCart(long comicId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, "comic_id = ?", new String[]{String.valueOf(comicId)});
        db.close();
    }


    public void dropTempTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS temp_comics");

    }

}

