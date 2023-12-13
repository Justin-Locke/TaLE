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
import javax.inject.Inject;


public class GetPersonalActivities {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;

    /**
     * Constructor for GetPersonalActivities using Dagger.
     * @param activitiesDao to instantiate ActivitiesDao for DDB methods.
     */
    @Inject
    public GetPersonalActivities(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    /**
     * Method to retrieve Personally Posted Activities.
     * @param request from Lambda containing user Id.
     * @return List of Activities from USER.
     */
    public GetPersonalActivitiesResult handleRequest(final GetPersonalActivitiesRequest request) {
        log.info("GetPersonalActivities Request ={}", request);
        List<Activity> activityList = activitiesDao.getAllPersonalActivities(request.getUserId());
        log.info("Personal Activity List ={}", activityList);
        List<ActivityModel> activityModelList = new ArrayList<>();

        for (Activity activity : activityList) {
            if (activity == null) {
                log.warn("Null Activity Found");
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
