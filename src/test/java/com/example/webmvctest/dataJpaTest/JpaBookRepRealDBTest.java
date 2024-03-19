package com.example.webmvctest.dataJpaTest;

import com.example.webmvctest.model.Book;
import com.example.webmvctest.repos.JpaBookRep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //происходит работа с настоящей базой данных НО! все изменения обратно откатываются!
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//реальная бд!!!!!!!!!! если встроенная то не нужно писать
class JpaBookRepRealDBTest {

    @Autowired
    JpaBookRep jpaBookRep;

    @Test
    void testAllBooks(){
        List<Book> bookList = jpaBookRep.findAll();

        assertNotNull(bookList);
        assertThat("zaza").isEqualTo(jpaBookRep.findById(1).get().getAuthor());

    }

    @Test
    void testDeleteWithRealDB(){

        jpaBookRep.deleteById(10);

        assertThat(jpaBookRep.findByName("ddf")).isNull();

    }



    @Test
    void testLongPageWithRealDB(){

        Book book = new Book("Aro","ss",11);
        Book book2 = new Book("s","sfdss",316);
        jpaBookRep.save(book);
        jpaBookRep.save(book2);



        assertEquals(book2,jpaBookRep.sortByPages());
    }


    @Test
    void testUpdatePageWithRealDB(){

        Book book1 = jpaBookRep.findByName("ddf");
        book1.setPages(34);
        book1.setAuthor("sdf");
        book1.setName("dsf");


        assertNotEquals("ddf",jpaBookRep.findByName(book1.getName()));
    }


    @Test
    void testFindByIdRealDB(){
        assertThat("ddf").isEqualTo(jpaBookRep.findByName("ddf").getName());
    }

    @Test
    void shoudReturnAvgBookPage() {

        assertThat(28.3).isEqualTo(jpaBookRep.avg());

    }

}