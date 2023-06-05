package com.comicsshop.Cart;

public class CartItem {
    private String title;
    private double price;
    private int quantity;
    private long orderId;

    public CartItem(long orderId, String title, String author, double price, int quantity) {
        this.orderId = orderId; // Добавьте эту строку, чтобы присвоить значение orderId
        this.title = title;
        this.price = price;
        this.quantity = quantity;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
