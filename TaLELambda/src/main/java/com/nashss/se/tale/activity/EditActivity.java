package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.EditActivityRequest;
import com.nashss.se.tale.activity.results.EditActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EditActivity {
    private final Logger log = LogManager.getLogger();
    private final ActivitiesDao activitiesDao;

    @Inject

    public EditActivity(ActivitiesDao activitiesDao) {
        this.activitiesDao = activitiesDao;
    }

    public EditActivityResult handleRequest(final EditActivityRequest request) {
        Activity activityToUpdate = activitiesDao.getActivityById(request.getActivityId());

        if (request.getUpdatedActivityName() != null) {
            activityToUpdate.setActivityName(request.getUpdatedActivityName());
        }
        if (request.getUpdatedDescription() != null) {
            activityToUpdate.setDescription(request.getUpdatedDescription());
        }
        if (request.getUpdatedPosterExperience() != null) {
            activityToUpdate.setPosterExperience(request.getUpdatedPosterExperience());
        }

        activityToUpdate.setEdited(true);

        activitiesDao.saveActivity(activityToUpdate);

        ActivityModel activityModel = new ModelConverter().toActivityModel(activityToUpdate);

        return EditActivityResult.builder()
                .withActivityModel(activityModel)
                .build();
    }
}
