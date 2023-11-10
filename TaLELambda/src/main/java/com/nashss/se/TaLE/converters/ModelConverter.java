package com.nashss.se.TaLE.converters;

import com.nashss.se.TaLE.dynamodb.models.City;
import com.nashss.se.TaLE.models.CityModel;

public class ModelConverter {

    public CityModel toCityModel(City city) {
        return CityModel.builder()
                .withCityId(city.getCityId())
                .withCityName(city.getCityName())
                .withActivityList(city.getActivityList())
                .build();
    }
}
