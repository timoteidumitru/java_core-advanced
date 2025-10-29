package com.javaPlayground.algorithms.book;

import lombok.Getter;

@Getter
public class Book {

    private int id;
    private String title;
    private String author;
    private String price;
    private int year;

    public Book(int id, String title, String author, String price, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", price='" + price + '\'' +
                ", year=" + year +
                '}';
    }
}
