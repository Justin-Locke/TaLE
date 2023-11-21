package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetPersonalActivitiesRequest;
import com.nashss.se.tale.activity.results.GetPersonalActivitiesResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GetPersonalActivities {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;

    public GetPersonalActivities(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    public GetPersonalActivitiesResult handleRequest(final GetPersonalActivitiesRequest request) {
        List<Activity> activityList = activitiesDao.getAllPersonalActivities(request.getUserId());

        List<ActivityModel> activityModelList = new ArrayList<>();

        for (Activity activity : activityList) {
            if (activity == null) {
                continue;
            }
            ActivityModel activityModel = new ModelConverter().toActivityModel(activity);
            activityModelList.add(activityModel);
        }

        return GetPersonalActivitiesResult.builder()
                .withActivityModelList(activityModelList)
                .build();
    }
}
