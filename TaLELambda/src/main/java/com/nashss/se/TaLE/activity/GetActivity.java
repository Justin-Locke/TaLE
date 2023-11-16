package com.nashss.se.TaLE.activity;

import com.nashss.se.TaLE.activity.requests.GetActivityRequest;
import com.nashss.se.TaLE.activity.results.GetActivityResult;
import com.nashss.se.TaLE.converters.ModelConverter;
import com.nashss.se.TaLE.dynamodb.ActivitiesDao;
import com.nashss.se.TaLE.dynamodb.models.Activity;
import com.nashss.se.TaLE.models.ActivityModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetActivity {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;
    @Inject
    public GetActivity(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    public GetActivityResult handleRequest(final GetActivityRequest getActivityRequest) {
        String activityId = getActivityRequest.getActivityId();

        Activity activity = activitiesDao.getActivityById(activityId);

        ActivityModel activityModel = new ModelConverter().toActivityModel(activity);

        return GetActivityResult.builder()
                .withActivityModel(activityModel)
                .build();
    }
}
