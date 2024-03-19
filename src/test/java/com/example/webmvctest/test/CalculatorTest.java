package com.example.webmvctest.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import static org.awaitility.Awaitility.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CalculatorTest {

    //@Mock
    @Spy
    Calculator calculator;


    @BeforeEach
    void init(){
        //openMocks(this);
        calculator= new Calculator();
    }


    @Test
    void calculatorTest(){
       // when(calculator.plus(3,4)).thenReturn(7);



        assertEquals(7,calculator.plus(3,4));

       // verify(calculator,times(1)).plus(3,4);
    }

}