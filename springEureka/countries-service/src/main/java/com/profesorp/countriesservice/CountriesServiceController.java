package com.profesorp.countriesservice;


import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.profesorp.countriesservice.entities.Countries;

//to map url request
@RestController
public class CountriesServiceController {
    HashMap<Integer, Integer> timePort=new HashMap<>();

    @Autowired
    private CountriesRepository countriesRepository;

    @Autowired
    private Environment environment;

    // when  we get /country_name in url
    @GetMapping("/{country}")

    public Countries getCountry(@PathVariable String country) {

        Countries  countryBean = countriesRepository.findById(country).orElseThrow(() -> new NotFoundException("Country: "+country+" not found"));

        //System.out.println("Capital value is" +countryBean.getCapital());
        int port= Integer.parseInt(environment.getProperty("local.server.port")) ;
        countryBean.setPort(port);
        int time=timePort.getOrDefault(port, 0);
        if (time>=0)
        {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return countryBean;	
    }	

    @GetMapping("/time/{time}")
    public int getTime(@PathVariable int time) {
        int port=Integer.parseInt(environment.getProperty("local.server.port")) ;
        timePort.put(port, time);
        return time;
    }	


   
    @PostMapping("/countries")
    public Countries createCountry(@RequestBody Countries country) {

        Countries countryBean = countriesRepository.save(country);
        return countryBean; 
    }

    @DeleteMapping("/countries/{country}")
    public void deleteCountry(@PathVariable String country) {
        countriesRepository.deleteById(country);
    }

    @PutMapping("/countries/{country}")
    public void UpdateCountry(@PathVariable Countries country) {
        countriesRepository.save(country);
    }


}
