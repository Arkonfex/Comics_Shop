package com.comicsshop.Comic;

import com.comicsshop.DatabaseHelper;

public class ComicData {
    private DatabaseHelper databaseHelper;

    public ComicData(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    public void loadComicData() {

        if (databaseHelper.getComicCount() > 0) {
            // База данных уже содержит записи, пропускаем загрузку данных
            return;
        }

        // Створення Автора
        long author1Id = databaseHelper.insertAuthor("Brian", "K. Vaughan");
        long author2Id = databaseHelper.insertAuthor("Al", "Ewing");
        long author3Id = databaseHelper.insertAuthor("Scott", "Snyder");
        long author4Id = databaseHelper.insertAuthor("G. Willow", "Wilson");
        long author5Id = databaseHelper.insertAuthor("Kieron", "Gillen");
        long author6Id = databaseHelper.insertAuthor("Jeff", "Lemire");
        long author7Id = databaseHelper.insertAuthor("Neil", "Gaiman");
        long author8Id = databaseHelper.insertAuthor("Jason", "Aaron");
        long author9Id = databaseHelper.insertAuthor("Jeph", "Loeb");

        // Створення Видавника
        long publisher1Id = databaseHelper.insertPublisher("Image Comics", "США");
        long publisher2Id = databaseHelper.insertPublisher("Marvel Comics", "США");
        long publisher3Id = databaseHelper.insertPublisher("DC Comics", "США");
        long publisher4Id = databaseHelper.insertPublisher("Dark Horse Comics", "США");

        // Додавання комиксов
        databaseHelper.insertComic("Saga", author1Id, publisher1Id, "США", 2012, 70.0);
        databaseHelper.insertComic("The Immortal Hulk", author2Id, publisher2Id, "США", 2018, 20.0);
        databaseHelper.insertComic("Batman: The Court of Owls", author3Id, publisher3Id, "США", 2011, 16.0);
        databaseHelper.insertComic("Ms. Marvel", author4Id, publisher2Id, "США", 2014, 18.0);
        databaseHelper.insertComic("The Wicked + The Divine", author5Id, publisher1Id, "США", 2014, 63.0);
        databaseHelper.insertComic("Black Hammer", author6Id, publisher4Id, "США", 2016, 51.0);
        databaseHelper.insertComic("Paper Girls", author1Id, publisher1Id, "США", 2015, 94.0);
        databaseHelper.insertComic("The Sandman", author7Id, publisher3Id, "США", 1989, 22.0);
        databaseHelper.insertComic("Thor: God of Thunder", author8Id, publisher2Id, "США", 2012, 74.0);
        databaseHelper.insertComic("Batman: Hush", author9Id, publisher3Id, "США", 2002, 62.0);
    }

}
