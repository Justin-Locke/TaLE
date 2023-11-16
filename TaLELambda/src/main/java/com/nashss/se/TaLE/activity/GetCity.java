package com.nashss.se.TaLE.activity;

import com.nashss.se.TaLE.activity.requests.GetCityRequest;
import com.nashss.se.TaLE.activity.results.GetCityResult;
import com.nashss.se.TaLE.converters.ModelConverter;
import com.nashss.se.TaLE.dynamodb.CitiesDao;
import com.nashss.se.TaLE.dynamodb.models.City;
import com.nashss.se.TaLE.models.CityModel;

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
