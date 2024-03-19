package com.example.webmvctest.services;


import com.example.webmvctest.model.Book;
import com.example.webmvctest.model.Dto;
import com.example.webmvctest.repos.DaoLayer;
import com.example.webmvctest.repos.JpaBookRep;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ServiceLayer {

    private final DaoLayer<Book> daoLayer;
    private final JpaBookRep jpaBookRep;


    public Book add(Dto dto) {
        // return daoLayer.add(new Book(dto.getName(), dto.getAuthor(),dto.getPages()));

        return jpaBookRep.save(new Book(dto.getName(), dto.getAuthor(), dto.getPages()));
    }
    public List<Book> findAll() {
      //  return daoLayer.findAll();
        return jpaBookRep.findAll();
    }

    public Book findById(int id) {
        //return daoLayer.findById(id);
        return jpaBookRep.findById(id).get();
    }

    public void del(int id) {
        //daoLayer.delete(id);
        jpaBookRep.deleteById(id);
    }

    public Book up(Dto dto, int id) {
        Book byId = findById(id);
        byId.setName(dto.getName());
        byId.setAuthor(dto.getAuthor());
        byId.setPages(dto.getPages());
      //  return daoLayer.update(byId,id);
        return jpaBookRep.save(byId);
    }

    public Book theBig() {
      //  return daoLayer.big();
        return jpaBookRep.sortByPages();
    }

    public Double avg() {
        return jpaBookRep.avg();
    }
}
