package com.example.webmvctest.repos;

import com.example.webmvctest.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBookRep extends JpaRepository<Book,Integer> {

    @Query(value = "select * from book order by pages desc limit 1",nativeQuery = true)
    Book sortByPages();

    Book findByName(String name);

    @Query(value = "select avg(pages) from book",nativeQuery = true)
    Double avg();
}
