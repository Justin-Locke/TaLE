package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetAllCitiesRequest;
import com.nashss.se.tale.activity.results.GetAllCitiesResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.CityModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetAllCities {

    private final Logger log = LogManager.getLogger();
    private final CitiesDao citiesDao;

    /**
     * Constructor for GetAllCities using Dagger.
     * @param citiesDao to instantiate CitiesDao for DDB.
     */
    @Inject
    public GetAllCities(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }

    /**
     * Method to Get All Cities from Cities Table.
     * @param getAllCitiesRequest an Empty request to fetch all item from citiesDao.
     * @return a List of cities.
     */
    public GetAllCitiesResult handleRequest(final GetAllCitiesRequest getAllCitiesRequest) {
        log.info("Received GetAllCitiesRequest = {}.", getAllCitiesRequest);
        List<City> cities = citiesDao.getAllCities();
        log.info("List of Cities ={}", cities);
        List<CityModel> cityModelList = new ArrayList<>();

        for (City city : cities) {
            cityModelList.add(new ModelConverter().toCityModel(city));
        }

        return GetAllCitiesResult.builder()
                .withCityModelList(cityModelList)
                .build();
    }
}
