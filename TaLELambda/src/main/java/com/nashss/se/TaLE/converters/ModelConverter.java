package com.nashss.se.TaLE.converters;

import com.nashss.se.TaLE.dynamodb.models.Activity;
import com.nashss.se.TaLE.dynamodb.models.City;
import com.nashss.se.TaLE.models.ActivityModel;
import com.nashss.se.TaLE.models.CityModel;

public class ModelConverter {

    public CityModel toCityModel(City city) {
        return CityModel.builder()
                .withCityId(city.getCityId())
                .withCityName(city.getCityName())
                .withActivityList(city.getActivityList())
                .build();
    }

    public ActivityModel toActivityModel(Activity activity) {
        return ActivityModel.builder()
                .withActivityId(activity.getActivityId())
                .withUserId(activity.getUserId())
                .withActivityName(activity.getActivityName())
                .withDescription(activity.getDescription())
                .withDatePosted(activity.getDatePosted())
                .withEdited(activity.getEdited())
                .withPosterExperience(activity.getPosterExperience())
                .build();
    }
}
