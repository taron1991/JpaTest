package com.example.webmvctest.webMvcTest;

import com.example.webmvctest.model.Book;
import com.example.webmvctest.model.Dto;
import com.example.webmvctest.repos.JpaBookRep;
import com.example.webmvctest.services.ServiceLayer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//@WebMvcTest(MainController.class)
@SpringBootTest           //Загружает полный контекст Spring приложения, включая все компоненты, сервисы, репозитории и т.д.Подходит для интеграционного тестирования, когда требуется загрузить все компоненты приложения.
@AutoConfigureMockMvc //создает бин MockMvc
class MainControllerTest {


    @MockBean
    public ServiceLayer serviceLayer;

    @MockBean
    public JpaBookRep jpaBookRep;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void testAddBook() throws Exception {
        Dto dto = new Dto("New Book", "New Author", 100);
        Book book = new Book(9, "New Book", "New Author", 100);

        given(serviceLayer.add(dto)).willReturn(book);

        mockMvc.perform(post("/api/add")
                        .contentType(MediaType.APPLICATION_JSON)//Header! Этот метод позволяет указать тип данных, который отправляется в теле запроса, чтобы обеспечить правильную обработку данных контроллером во время тестирования.
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("user added")); //content() -> Access to response body assertions.


        verify(serviceLayer, times(1)).add(dto);// был ли вызван метод  add()  у заглушки  serviceLayer
        // ровно один раз с определенными аргументами  dto .
    }

    @Test
    public void testGetAll() throws Exception {
        List<Book> bookList = Arrays.asList(
                new Book(1, "Roman", "Nechaev", 234),
                new Book(2, "Killers", "Nechaev", 23),
                new Book(3, "Fate", "Carter", 65),
                new Book(4, "Fingers", "Lolev", 530),
                new Book(5, "Lover", "Forester", 90),
                new Book(6, "Ver", "Nechaev", 14),
                new Book(7, "Winds", "Claus", 123),
                new Book(8, "JHP", "Nechaev", 902)
        );

        given(serviceLayer.findAll()).willReturn(bookList);

        mockMvc.perform(get("/api/all"))
                .andExpect(status().is2xxSuccessful())
                //.andExpect(jsonPath("$[1].name").value("Killers"))
                .andExpect(content().json(objectMapper.writeValueAsString(bookList)));
        //jsonPath() доступ к телу ответа!!!
        //отсчет идет с нуля!! $[0]
    }

    @Test
    public void testFindBook() throws Exception {
        int id = 1;
        Book book = new Book(1, "Roman", "Nechaev", 234);

        given(serviceLayer.findById(id)).willReturn(book);

        mockMvc.perform(get("/api/id/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)))
                .andExpect(jsonPath("$.name").value("Roman"));
        //можно проверять через метод json() целиком проверяет json
        // либо jsonPath() проверяем на совпадения каие-то поля
    }

    @Test
    public void testDel() throws Exception {
        int id = 1;

        Mockito.doNothing().when(serviceLayer).del(id);

        mockMvc.perform(delete("/api/del/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().string("deleted " + id));
    }

    @Test
    public void testUpdate() throws Exception {
        int id = 1;
        Dto dto = new Dto("Updated Book", "Updated Author", 200);

        given(serviceLayer.up(dto, id)).willReturn(new Book(id, "Updated Book", "Updated Author", 200));

        mockMvc.perform(put("/api/update/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isAccepted())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(dto)));
    }


    @Test
    public void testThelongestPage() throws Exception {

        Book book = new Book(8, "JHP", "Nechaev", 902);

        given(serviceLayer.theBig()).willReturn(book);


        mockMvc.perform(get("/api/longPage"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(book)));



    }

}