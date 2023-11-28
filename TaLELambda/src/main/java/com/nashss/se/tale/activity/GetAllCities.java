package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetAllCitiesRequest;
import com.nashss.se.tale.activity.results.GetAllCitiesResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.CityModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetAllCities {

    private final Logger log = LogManager.getLogger();
    private final CitiesDao citiesDao;

    @Inject
    public GetAllCities(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }

    public GetAllCitiesResult handleRequest(final GetAllCitiesRequest getAllCitiesRequest) {
        log.info("Received GetAllCitiesRequest = {}.", getAllCitiesRequest);
        List<City> cities = citiesDao.getAllCities();
        System.out.println("List of cities = " + cities);

        List<CityModel> cityModelList = new ArrayList<>();

        //ExecutorService executor = Executors.newCachedThreadPool();
        for (City city : cities) {
                  //CityModel response = executor.submit(() -> new ModelConverter().toCityModel(city)).get();
                  //cityModelList.add(response);
            cityModelList.add(new ModelConverter().toCityModel(city));
        }

        //executor.shutdown();

        return GetAllCitiesResult.builder()
                .withCityModelList(cityModelList)
                .build();
    }
}
