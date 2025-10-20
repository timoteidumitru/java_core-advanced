package com.javaPlayground.algorithms.book;

import java.util.*;
import java.util.stream.Collectors;

public class Runner {
    public static void main(String[] args) {
        List<Book> books = getAllBooks();

        //print title of books published after 2010
//        publishedAfter(books, 2010);
        //calculate the total price of all books
//        totalPrice(books);
        //find the book with the highest price
//        highersPriceBook(books);
        //generate a list with books priced less than $45
        booksPriceLowerThen(books, 45);
    }

    public static void booksPriceLowerThen(List<Book> books, int target){
        Map<String, String> priceLessThen = books.stream().filter(book ->
                        Integer.parseInt(book.getPrice().replaceAll("\\$", "")) < target)
                .collect(Collectors.toMap(Book::getTitle, Book::getPrice));

        System.out.println("Book price lesser the: " + target);
        System.out.println(priceLessThen);
    }

    public static void highersPriceBook(List<Book> books){
        List<Book> highestPriceBook = books.stream().max(Comparator.comparingInt(book ->
                    Integer.parseInt(book.getPrice().replaceAll("\\$", ""))))
                            .stream().toList();

        System.out.println("Book with highest price: " + highestPriceBook);
    }

    public static void totalPrice(List<Book> books){
        List<String> prices = books.stream()
                .map(e -> e.getPrice().replace("$", "")).toList();
        Integer totalPrice = prices.stream().map(Integer::parseInt).reduce(0, Integer::sum);

        System.out.println("Total price for all books is: $" +totalPrice);
    }

    public static void publishedAfter(List<Book> books, int year){
        Map<String, Integer> collect = books.stream().filter(book -> book.getYear() >= year)
                .collect(Collectors.toMap(Book::getTitle, Book::getYear));

        System.out.println("Books published after " + year + ":");
        System.out.println(collect);
    }

    public static List<Book> getAllBooks(){
        return new ArrayList<>() {{
            add(new Book(1, "Effective Java", "Joshua Bloch", "$45", 2018));
            add(new Book(2, "Clean Code", "Robert C. Martin", "$50", 2008));
            add(new Book(3, "The Pragmatic Programmer", "Andy Hunt", "$55", 1999));
            add(new Book(4, "Design Patterns", "Erich Gamma", "$60", 1994));
            add(new Book(5, "Refactoring", "Martin Fowler", "$65", 1999));
            add(new Book(6, "Head First Design Patterns", "Eric Freeman", "$47", 2004));
            add(new Book(7, "Java Concurrency in Practice", "Brian Goetz", "$40", 2006));
            add(new Book(8, "Introduction to Algorithms", "Thomas H. Cormen", "$90", 2009));
            add(new Book(9, "Spring in Action", "Craig Walls", "$75", 2016));
            add(new Book(10, "Java: The Complete Reference", "Herbert Schildt", "$80", 2020));
            add(new Book(11, "Clean Architecture", "Robert C. Martin", "$55", 2017));
            add(new Book(12, "Test-Driven Development", "Kent Beck", "$35", 2003));
        }};
    }
}
