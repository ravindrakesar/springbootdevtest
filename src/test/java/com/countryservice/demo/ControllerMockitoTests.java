package com.countryservice.demo;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.controllers.CountryController;
import com.countryservice.demo.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTests.class})
public class ControllerMockitoTests {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    List<Country> mycountries;
    Country country;

    @Test
    @Order(1)
    public void test_getAllCountries(){
        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1,"India","New Delhi"));
        mycountries.add(new Country(2,"UK","London"));
        mycountries.add(new Country(3,"France","Paris"));

        when(countryService.getAllCountries()).thenReturn(mycountries);//Mocking
        ResponseEntity<List<Country>> res= countryController.getCountries();

        assertEquals(HttpStatus.FOUND,res.getStatusCode());
        assertEquals(3,res.getBody().size());
    }

    @Test
    @Order(2)
    public void test_getCountryById(){

        country = new Country(4,"India","NewDelhi");
        int countryID=4;

        when(countryService.getCountryById(countryID)).thenReturn(country);//Mocking
        ResponseEntity<Country> res=countryController.getCountryById(countryID);

        assertEquals(HttpStatus.FOUND,res.getStatusCode());
        assertEquals(countryID,res.getBody().getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName(){
        country = new Country(4,"India","NewDelhi");
        String countryName="India";

        when(countryService.getCountryByName(countryName)).thenReturn(country);//Mocking
        ResponseEntity<Country> res=countryController.getCountryByName(countryName);

        assertEquals(HttpStatus.FOUND,res.getStatusCode());
        assertEquals(countryName,res.getBody().getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry(){
        country = new Country(5,"Afghanistan","Kabul");

        when(countryService.addCountry(country)).thenReturn(country);
        ResponseEntity<Country> res=countryController.addCountry(country);

        assertEquals(HttpStatus.CREATED,res.getStatusCode());
        assertEquals(country,res.getBody());


    }

    @Test
    @Order(5)
    public void test_updateCountry(){
        country = new Country(5,"Japan","Tokyo");
        int countryID=3;

        when(countryService.getCountryById(countryID)).thenReturn(country);
        when(countryService.updateCountry(country)).thenReturn(country);

        ResponseEntity<Country> res=countryController.updateCountry(countryID,country);
        assertEquals(HttpStatus.OK,res.getStatusCode());
        assertEquals(5,res.getBody().getId());
        assertEquals("Japan",res.getBody().getCountryName());
        assertEquals("Tokyo",res.getBody().getCountryCapital());

    }

    @Test
    @Order(6)
    public void test_deleteCountry(){
        country = new Country(5,"Japan","Tokyo");
        int countryID=3;

        when(countryService.getCountryById(countryID)).thenReturn(country);
        ResponseEntity<Country> res=countryController.deleteCountry(countryID);

        assertEquals(HttpStatus.OK,res.getStatusCode());
    }


}
