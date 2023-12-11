package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetAllActivitiesRequest;
import com.nashss.se.tale.activity.results.GetAllActivitiesResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.ActivityModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;


public class GetAllActivities {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;
    private CitiesDao citiesDao;

    /**
     * Constructor for GetAllActivities using Dagger.
     * @param activitiesDao to instantiate ActivitiesDao for DDB.
     * @param citiesDao to instantiate CitiesDao for DDB.
     */
    @Inject
    public GetAllActivities(ActivitiesDao activitiesDao, CitiesDao citiesDao) {
        this.activitiesDao = activitiesDao;
        this.citiesDao = citiesDao;
    }

    /**
     * Method to use Request to Get All Activities for a single city.
     * @param request from Lambda containing a List of ActivityId's.
     * @return List of Activities.
     */
    public GetAllActivitiesResult handleRequest(final GetAllActivitiesRequest request) {
        String cityId = request.getCityId();

        City city = citiesDao.getCityById(cityId);
        List<String> activityIdList = city.getActivityList();

        List<Activity> activityList = new ArrayList<>();
        List<String> activityIdsToRemove = new ArrayList<>();
        for (String id: activityIdList) {
            Activity activity = activitiesDao.getActivityById(id);
            if (activity == null) {
                activityIdsToRemove.add(id);
            } else {
                activityList.add(activity);
            }
        }

        activityIdList.removeAll(activityIdsToRemove);

        city.setActivityList(activityIdList);
        citiesDao.saveCity(city);

        List<ActivityModel> activityModelList = new ArrayList<>();
        for (Activity activity: activityList) {
            activityModelList.add(new ModelConverter().toActivityModel(activity));
        }

        return GetAllActivitiesResult.builder()
                .withActivityModelList(activityModelList)
                .build();

    }
}
