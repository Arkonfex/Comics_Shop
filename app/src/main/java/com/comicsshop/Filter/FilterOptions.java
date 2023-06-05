package com.comicsshop.Filter;

public class FilterOptions {
    public static final int FILTER_OPTION_1 = 1;
    public static final int FILTER_OPTION_2 = 2;
    public static final int FILTER_OPTION_3 = 3;

    private boolean filterByAuthorName;
    private String authorName;
    private boolean filterByPrice;
    private boolean sortByPriceAscending;
    private boolean sortByPriceDescending;

    private int selectedFilter;
    private int value;

    public FilterOptions(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    // геттер и сеттер для selectedFilter
    public int getSelectedFilter() {
        return selectedFilter;
    }

    public void setSelectedFilter(int selectedFilter) {
        this.selectedFilter = selectedFilter;
    }

    // Конструктор и методы получения/установки значений

    public boolean isFilterByAuthorName() {
        return filterByAuthorName;
    }

    public void setFilterByAuthorName(boolean filterByAuthorName) {
        this.filterByAuthorName = filterByAuthorName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public boolean isFilterByPrice() {
        return filterByPrice;
    }

    public void setFilterByPrice(boolean filterByPrice) {
        this.filterByPrice = filterByPrice;
    }

    public void setSortByPriceAscending(boolean sortByPriceAscending) {
        this.sortByPriceAscending = sortByPriceAscending;
    }

    public boolean getSortByPriceDescending() {
        return sortByPriceDescending;
    }

    public void setSortByPriceDescending(boolean sortByPriceDescending) {
        this.sortByPriceDescending = sortByPriceDescending;
    }
}
