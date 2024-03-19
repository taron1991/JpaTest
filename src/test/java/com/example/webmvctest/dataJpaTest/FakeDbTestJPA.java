package com.example.webmvctest.dataJpaTest;

import com.example.webmvctest.model.Book;
import com.example.webmvctest.repos.JpaBookRep;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest//происходит работа с фейковой базой данных H2! все изменения обратно откатываются!после тестов
@ExtendWith(SpringExtension.class)
public class FakeDbTestJPA {

    @Autowired // we use not real db!saves info in H2(pom.xml)
    private TestEntityManager entityManager;

    @Autowired
    JpaBookRep jpaBookRep;


    @Test
    void shoudReturnSavedBook() {
        Book book = new Book("Ala", "a", 12);
        //entityManager.persist(book);// также сохраняет в тестовую БД(H2)!!!!!
        jpaBookRep.save(book);// сохраняет в тестовую БД(H2)!!!!!
        assertEquals("Ala", jpaBookRep.findByName(book.getName()).getName());
        assertThat("Ala").isEqualTo(jpaBookRep.findByName(book.getName()).getName());
    }

    @Test
    void shoudReturnBookById() {
        Book book = new Book("Ala", "a", 12);
        Book book1 = new Book("Ala1", "b", 34);

        entityManager.persist(book);
        entityManager.persist(book1);

        assertThat(book).isEqualTo(jpaBookRep.findByName(book.getName()));

    }

    @Test
    void shoudReturnAllBooks() {
        Book book = new Book("Ala", "a", 12);
        Book book1 = new Book("Ala1", "b", 34);
        Book book2 = new Book("Ala2", "c", 82);
        Book book4 = new Book("Ala3", "d", 34);


        entityManager.persist(book);
        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book4);

        assertThat(4).isEqualTo(jpaBookRep.findAll().size());
    }

    @Test
    void shoudDeleteBookById() {
        Book book = new Book("Ala", "a", 12);
        Book book1 = new Book("Ala1", "b", 34);
        Book book2 = new Book("Ala2", "c", 82);

        entityManager.persist(book);
        entityManager.persist(book1);
        entityManager.persist(book2);


        entityManager.remove(book2);//удаляет только их persistence context!
        Book book3 = entityManager.find(Book.class, book2.getId());//убеждаемся что действительно обьекта нет в бд

        assertThat(book3).isNull();
    }

    @Test
    void shoudUpdateBookById() {
        Book book2 = new Book("Ala2", "c", 82);
        Book book3 = new Book("Alas", "Zoser", 82);
        jpaBookRep.save(book2);
        jpaBookRep.save(book3);


        Book book = jpaBookRep.findByName(book3.getName());
        book.setName("Ashot");
        book.setPages(23);
        book.setAuthor("lolo");
        jpaBookRep.save(book);


        assertThat("Alas").isNotEqualTo(book3.getName());

    }


    @Test
    void shoudReturnTheLongestBookPage() {
        Book book2 = new Book("Ala2", "c", 82);
        Book book3 = new Book("Alas", "Zoser", 92);


        jpaBookRep.save(book2);
        jpaBookRep.save(book3);

        assertThat(book3).isEqualTo(jpaBookRep.sortByPages());
    }


    @Test
    void shoudReturnAvgBookPage() {
        Book book = new Book("Ala", "a", 10);
        Book book1 = new Book("Ala1", "b", 30);
        Book book2 = new Book("Ala2", "c", 45);

        entityManager.persist(book);
        entityManager.persist(book1);
        entityManager.persist(book2);


        assertThat(28.333333333333332).isEqualTo(jpaBookRep.avg());
    }

    //sum/min/max
}
