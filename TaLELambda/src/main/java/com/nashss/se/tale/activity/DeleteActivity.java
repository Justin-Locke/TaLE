package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.DeleteActivityRequest;
import com.nashss.se.tale.activity.results.DeleteActivityResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;

public class DeleteActivity {
    private final Logger log = LogManager.getLogger();
    private final ActivitiesDao activitiesDao;
    private final CitiesDao citiesDao;

    /**
     * Constructor for DeleteActivity using Dagger.
     * @param activitiesDao to communicate with Activities Table.
     * @param citiesDao to communicate with Cities Table.
     */
    @Inject
    public DeleteActivity(ActivitiesDao activitiesDao, CitiesDao citiesDao) {
        this.activitiesDao = activitiesDao;
        this.citiesDao = citiesDao;
    }

    /**
     * Method to handle a DeleteActivityRequest.
     * @param request from LambdaBuild.
     * @return a String result of Deleted activity.
     */
    public DeleteActivityResult handleRequest(final DeleteActivityRequest request) {
        String activityId = request.getActivityId();
        String userId = request.getUserId();

        String result = activitiesDao.deleteActivity(activityId);

        return new DeleteActivityResult.Builder()
                .withDeleteResult(result)
                .build();

    }
}
