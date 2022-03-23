package com.countryservice.demo;

import com.countryservice.demo.beans.Country;
import com.countryservice.demo.repositories.CountryRepository;
import com.countryservice.demo.services.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTest.class})
public class ServiceMockitoTest {

    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    CountryService countryService;

    public List<Country> mycountries;

    @Test
    @Order(1)
    public void test_getAllCountries(){

        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1, "Pakistan", "Lahore"));
        mycountries.add(new Country(2, "China", "Beijing"));
        mycountries.add(new Country(3, "Australia", "Sydney"));

        when(countryRepository.findAll()).thenReturn(mycountries);//Mocking
        assertEquals(3,countryService.getAllCountries().size() );
    }

    @Test
    @Order(2)
    public void test_getCountryById(){

        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1, "Pakistan", "Lahore"));
        mycountries.add(new Country(2, "China", "Beijing"));
        mycountries.add(new Country(3, "Australia", "Sydney"));

        int countryId=2;
        when(countryRepository.findAll()).thenReturn(mycountries);//Mocking

        assertEquals(countryId, countryService.getCountryById(countryId).getId());
    }

    @Test
    @Order(3)
    public void test_getCountryByName(){

        mycountries = new ArrayList<Country>();
        mycountries.add(new Country(1, "Pakistan", "Lahore"));
        mycountries.add(new Country(2, "China", "Beijing"));
        mycountries.add(new Country(3, "Australia", "Sydney"));

        String countryName="Australia";
        when(countryRepository.findAll()).thenReturn(mycountries);//Mocking

        assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());
    }

    @Test
    @Order(4)
    public void test_addCountry(){
        Country country=new Country(5,"Germany","Berlin");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country,countryService.addCountry(country));
    }

    @Test
    @Order(5)
    public void test_UpdateCountry(){
        Country country=new Country(6,"Ukraine","Kyiv");

        when(countryRepository.save(country)).thenReturn(country);
        assertEquals(country,countryService.updateCountry(country));
    }

    @Test
    @Order(6)
    public void test_deleteCountry(){
        Country country=new Country(6,"Ukraine","Kyiv");
        countryService.deleteCountry(country);
        verify(countryRepository,times(1)).delete(country);
    }
}
