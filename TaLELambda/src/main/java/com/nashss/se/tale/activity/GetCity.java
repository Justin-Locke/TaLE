package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetCityRequest;
import com.nashss.se.tale.activity.results.GetCityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.CityModel;

import javax.inject.Inject;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCity {

    private final Logger log = LogManager.getLogger();
    private final CitiesDao citiesDao;

    @Inject
    public GetCity(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }

    public GetCityResult handleRequest(final GetCityRequest getCityRequest) {
        log.info("Received GetCityRequest = {}.", getCityRequest);
        City city = citiesDao.getCityById(getCityRequest.getCityId());

        CityModel cityModel = new ModelConverter().toCityModel(city);

        return GetCityResult.builder()
                .withCityModel(cityModel)
                .build();
    }
}
