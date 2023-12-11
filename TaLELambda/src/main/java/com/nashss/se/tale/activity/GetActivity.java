package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetActivityRequest;
import com.nashss.se.tale.activity.results.GetActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetActivity {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;

    /**
     * Constructor for GetActivity using Dagger.
     * @param activitiesDao to instantiate ActivitiesDao.
     */
    @Inject
    public GetActivity(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    /**
     * Method to get a Single Activity.
     * @param getActivityRequest from Lambda containing the ActivityId.
     * @return single Activity.
     */
    public GetActivityResult handleRequest(final GetActivityRequest getActivityRequest) {
        String activityId = getActivityRequest.getActivityId();

        Activity activity = activitiesDao.getActivityById(activityId);


        ActivityModel activityModel = new ModelConverter().toActivityModel(activity);

        return GetActivityResult.builder()
                .withActivityModel(activityModel)
                .build();
    }
}
