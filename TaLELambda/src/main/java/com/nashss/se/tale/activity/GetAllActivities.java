package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetAllActivitiesRequest;
import com.nashss.se.tale.activity.results.GetAllActivitiesResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;


public class GetAllActivities {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;

    /**
     * Constructor for GetAllActivities using Dagger.
     * @param activitiesDao to instantiate ActivitiesDao for DDB.
     */
    @Inject
    public GetAllActivities(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    /**
     * Method to use Request to Get All Activities for a single city.
     * @param request from Lambda containing a List of ActivityId's.
     * @return List of Activities.
     */
    public GetAllActivitiesResult handleRequest(final GetAllActivitiesRequest request) {
        List<String> activityIdList = request.getActivityIdList();
        List<Activity> activityList = new ArrayList<>();

        for (String id: activityIdList) {
            activityList.add(activitiesDao.getActivityById(id));
        }

        List<ActivityModel> activityModelList = new ArrayList<>();
        for (Activity activity: activityList) {
            activityModelList.add(new ModelConverter().toActivityModel(activity));
        }

        return GetAllActivitiesResult.builder()
                .withActivityModelList(activityModelList)
                .build();

    }
}
