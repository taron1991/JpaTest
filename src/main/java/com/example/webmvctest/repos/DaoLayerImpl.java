package com.example.webmvctest.repos;

import com.example.webmvctest.model.Book;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DaoLayerImpl implements DaoLayer<Book> {

    public static int count = 8;
    public final List<Book> bookList = new ArrayList<>();

    {
        Collections.addAll(bookList, new Book(1, "Roman", "Nechaev", 234),
                new Book(2, "Killers", "Nechaev", 23),
                new Book(3, "Fate", "Carter", 65),
                new Book(4, "Fingers", "Lolev", 530),
                new Book(5, "Lover", "Forester", 90),
                new Book(6, "Ver", "Nechaev", 14),
                new Book(7, "Winds", "Claus", 123),
                new Book(8, "JHP", "Nechaev", 902));


    }


    @Override
    public Book add(Book book) {
        book.setId(++count);
        bookList.add(book);
        return book;
    }

    @Override
    public Book findById(int id) {
        return bookList.stream().filter(book -> book.getId() == id).findAny().get();
    }

    @Override
    public Book update(Book book, int id) {
        return bookList.set(id,book);
    }

    @Override
    public void delete(int id) {
        bookList.removeIf(book -> book.getId()==id);
    }

    @Override
    public List<Book> findAll() {
        return bookList;
    }

    @Override
    public Book big() {
        return bookList.stream().max(Comparator.comparing(Book::getPages)).stream().findAny().get();
    }
}
