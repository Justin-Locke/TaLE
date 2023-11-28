package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetAllActivitiesRequest;
import com.nashss.se.tale.activity.results.GetAllActivitiesResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GetAllActivities {
    private Logger log = LogManager.getLogger();
    private ActivitiesDao activitiesDao;

    @Inject
    public GetAllActivities(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    public GetAllActivitiesResult handleRequest(final GetAllActivitiesRequest request) {
        List<String> activityIdList = request.getActivityIdList();
        List<Activity> activityList = new ArrayList<>();

//        ExecutorService executor = Executors.newCachedThreadPool();

        for (String id: activityIdList) {
            activityList.add(activitiesDao.getActivityById(id));
//            try {
//                Future<Activity> activity = executor.submit(() -> activitiesDao.getActivityById(id));
//                activityList.add(activity.get());
//
//            } catch (InterruptedException | ExecutionException e) {
//                e.getCause();
//            }
        }

        List<ActivityModel> activityModelList = new ArrayList<>();
        for (Activity activity: activityList) {
            activityModelList.add(new ModelConverter().toActivityModel(activity));
//            try {
//                Future<ActivityModel> model = executor.submit(() -> new ModelConverter().toActivityModel(activity));
//                activityModelList.add(model.get());
//            } catch (InterruptedException | ExecutionException e) {
//                e.getCause();
//            }
        }
//        executor.shutdown();

        return GetAllActivitiesResult.builder()
                .withActivityModelList(activityModelList)
                .build();

    }
}
