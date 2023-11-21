package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.CreateNewActivityRequest;
import com.nashss.se.tale.activity.results.CreateNewActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.ActivityModel;
import com.nashss.se.tale.utils.IdUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;

public class CreateNewActivity {
    private final Logger log = LogManager.getLogger();
    private final ActivitiesDao activitiesDao;
    private final CitiesDao citiesDao;

    @Inject
    public CreateNewActivity(ActivitiesDao activitiesDao, CitiesDao citiesDao) {
        this.activitiesDao = activitiesDao;
        this.citiesDao = citiesDao;
    }

    public CreateNewActivityResult handleRequest(final CreateNewActivityRequest request) {
        Activity newActivity = new Activity();
        newActivity.setActivityId(IdUtils.generateActivityId());
        newActivity.setUserId(request.getUserId());
        newActivity.setActivityName(request.getActivityName());
        newActivity.setDescription(request.getDescription());
        newActivity.setDatePosted(LocalDate.now());
        newActivity.setEdited(false);
        newActivity.setPosterExperience(request.getPosterExperience());

        activitiesDao.saveActivity(newActivity);

        City cityToUpdate = citiesDao.getCityById(request.getCityId());
        cityToUpdate.getActivityList().add(newActivity.getActivityId());
        citiesDao.saveCity(cityToUpdate);

        ActivityModel activityModel = new ModelConverter().toActivityModel(newActivity);
        return CreateNewActivityResult.builder()
                .withActivityModel(activityModel)
                .build();
    }
}
