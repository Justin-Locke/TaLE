package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.DeleteActivityRequest;
import com.nashss.se.tale.activity.results.DeleteActivityResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteActivity {
    private final Logger log = LogManager.getLogger();
    private final ActivitiesDao activitiesDao;

    /**
     * Constructor for DeleteActivity using Dagger.
     * @param activitiesDao to communicate with Activities Table.
     */
    @Inject
    public DeleteActivity(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    /**
     * Method to handle a DeleteActivityRequest.
     * @param request from LambdaBuild.
     * @return a String result of Deleted activity.
     */
    public DeleteActivityResult handleRequest(final DeleteActivityRequest request) {
        log.info("Request To Delete Activity {}", request);
        String activityId = request.getActivityId();
        String result = activitiesDao.deleteActivity(activityId);

        return new DeleteActivityResult.Builder()
                .withDeleteResult(result)
                .build();

    }
}
