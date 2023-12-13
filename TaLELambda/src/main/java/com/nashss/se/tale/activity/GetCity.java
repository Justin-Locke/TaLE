package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetCityRequest;
import com.nashss.se.tale.activity.results.GetCityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.CityModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetCity {

    private final Logger log = LogManager.getLogger();
    private final CitiesDao citiesDao;

    /**
     * Constructor for GetCity using Dagger.
     * @param citiesDao to instantiate a CitiesDao to talk to DDB.
     */
    @Inject
    public GetCity(CitiesDao citiesDao) {
        this.citiesDao = citiesDao;
    }

    /**
     * Method to Get City from GetCityRequest.
     * @param getCityRequest is the built request from Lambda.
     * @return a GetCityResult with retrieved City converted to Model.
     */
    public GetCityResult handleRequest(final GetCityRequest getCityRequest) {
        log.info("Received GetCityRequest = {}.", getCityRequest);
        City city = citiesDao.getCityById(getCityRequest.getCityId());
        log.info("City Retrieved ={}", city);
        CityModel cityModel = new ModelConverter().toCityModel(city);

        return GetCityResult.builder()
                .withCityModel(cityModel)
                .build();
    }
}
