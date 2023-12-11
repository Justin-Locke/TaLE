package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.EditActivityRequest;
import com.nashss.se.tale.activity.results.EditActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.exceptions.EmptyFieldInRequestException;
import com.nashss.se.tale.exceptions.InvalidUserException;
import com.nashss.se.tale.models.ActivityModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EditActivity {
    private final Logger log = LogManager.getLogger();
    private final ActivitiesDao activitiesDao;

    /**
     * Constructor for EditActivity using Dagger.
     * @param activitiesDao to instantiate ActivitiesDao for DDB.
     */
    @Inject
    public EditActivity(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    /**
     * Method to handle an update to an Activity.
     * @param request from Lambda to update Activity.
     * @return updated Activity Model.
     */
    public EditActivityResult handleRequest(final EditActivityRequest request) {
        if (request.getUpdatedActivityName().isEmpty()) {
            throw new EmptyFieldInRequestException("Your activity must have a name");
        }

        Activity activityToUpdate = activitiesDao.getActivityById(request.getActivityId());

        if (!(activityToUpdate.getDescription().isEmpty()) && request.getUpdatedDescription().isEmpty()) {
            throw new EmptyFieldInRequestException("You can update your description but you cannot delete it.");
        }

        if (!(activityToUpdate.getPosterExperience() == null || activityToUpdate.getPosterExperience().isEmpty()) && request.getUpdatedPosterExperience().isEmpty()) {
            throw new EmptyFieldInRequestException("You can update your experience but you cannot delete it.");
        }

        if (!activityToUpdate.getUserId().equals(request.getUserId())) {
            throw new InvalidUserException("You do not have access to modify this activity");
        }


        activityToUpdate.setActivityName(request.getUpdatedActivityName());
        activityToUpdate.setDescription(request.getUpdatedDescription());
        activityToUpdate.setPosterExperience(request.getUpdatedPosterExperience());
        activityToUpdate.setEdited(true);

        activitiesDao.saveActivity(activityToUpdate);

        ActivityModel activityModel = new ModelConverter().toActivityModel(activityToUpdate);

        return EditActivityResult.builder()
                .withActivityModel(activityModel)
                .build();
    }
}
