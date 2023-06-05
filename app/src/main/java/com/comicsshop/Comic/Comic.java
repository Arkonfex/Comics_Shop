package com.comicsshop.Comic;

import com.comicsshop.Filter.FilterOptions;

public class Comic {
    private FilterOptions filterOption;
    private long comicId;
    private String title;
    private long authorId;
    private long publisherId;
    private String country;
    private int releaseYear;
    private double price;
    private String authorName;
    private String authorLastName;
    private String publisherName;

    public Comic(long comicId, String title, long authorId, long publisherId, String country, int releaseYear, double price, String authorName, String authorLastName, String publisherName) {
        this.comicId = comicId;
        this.title = title;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.country = country;
        this.releaseYear = releaseYear;
        this.price = price;
        this.authorName = authorName;
        this.authorLastName = authorLastName;
        this.publisherName = publisherName;
    }

    public FilterOptions getFilterOption() {
        return filterOption;
    }

    public void setFilterOption(FilterOptions filterOption) {
        this.filterOption = filterOption;
    }

    public long getComicId() {
        return comicId;
    }

    public void setComicId(long comicId) {
        this.comicId = comicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(long publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherCountry() {
        return country;
    }

    public void setPublisherCountry(String country) {
        this.country = country;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
