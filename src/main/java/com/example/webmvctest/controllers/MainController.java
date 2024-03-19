package com.example.webmvctest.controllers;


import com.example.webmvctest.model.Book;
import com.example.webmvctest.model.Dto;
import com.example.webmvctest.services.ServiceLayer;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MainController {


    private final ServiceLayer serviceLayer;


    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody @Valid Dto dto, BindingResult bindingResult){
       if(bindingResult.hasErrors()){
           log.atError();
           return ResponseEntity.badRequest().body("errors in valid");
       }
       else {
           log.info("log");
           serviceLayer.add(dto);
           return ResponseEntity.ok("user added");
       }
    }


    @GetMapping("/all")
    public List<Book> getAll(){
        return serviceLayer.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Book> findBook(@PathVariable int id){
        Book byId = serviceLayer.findById(id);
        if(byId==null){
            return ResponseEntity.ofNullable(byId);
        }
        return ResponseEntity.ok(byId);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<String> del(@PathVariable int id){
        serviceLayer.del(id);
        return ResponseEntity.ok("deleted "+id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Dto> update(@RequestBody Dto dto,@PathVariable int id){
        Book up = serviceLayer.up(dto, id);
        return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/longPage")
    public Book getTheLongest(){
        return serviceLayer.theBig();
    }


    @GetMapping("/AvgPage")
    public ResponseEntity<Double> getAVG(){
        return new ResponseEntity<>(serviceLayer.avg(),HttpStatus.ACCEPTED);
    }

}
