package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.CreateNewActivityRequest;
import com.nashss.se.tale.activity.results.CreateNewActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.exceptions.EmptyFieldInRequestException;
import com.nashss.se.tale.models.ActivityModel;
import com.nashss.se.tale.utils.IdUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import javax.inject.Inject;


public class CreateNewActivity {
    private final Logger log = LogManager.getLogger();
    private final ActivitiesDao activitiesDao;
    private final CitiesDao citiesDao;

    /**
     * Constructor for CreateNewActivity using Dagger.
     * @param activitiesDao to talk to Activities DDB.
     * @param citiesDao to talk to Cities DDB.
     */
    @Inject
    public CreateNewActivity(ActivitiesDao activitiesDao, CitiesDao citiesDao) {
        this.activitiesDao = activitiesDao;
        this.citiesDao = citiesDao;
    }

    /**
     * Method to Create a New Activity from Request.
     * @param request containing UserId, ActivityName, Description, and Poster Experience for New Activity.
     * @return created Activity.
     */
    public CreateNewActivityResult handleRequest(final CreateNewActivityRequest request) {
        String activityName = request.getActivityName();
        String activityDescription = request.getDescription();
        String activityPosterExperience = request.getPosterExperience();

        if (activityName.isEmpty()) {
            log.warn("Request Activity Name is Empty");
            throw new EmptyFieldInRequestException("Your activity must contain a name");
        }

        if (activityDescription.isEmpty() && activityPosterExperience.isEmpty()) {
            log.warn("Request Description and Request Poster Experience are Empty");
            throw new EmptyFieldInRequestException("You must fill out at least ONE" +
                    " of these fields. Description/Experience");
        }


        Activity newActivity = new Activity();
        newActivity.setActivityId(IdUtils.generateActivityId());
        newActivity.setUserId(request.getUserId());
        newActivity.setActivityName(activityName);
        newActivity.setDescription(activityDescription);
        newActivity.setDatePosted(LocalDate.now());
        newActivity.setEdited(false);
        newActivity.setPosterExperience(activityPosterExperience);

        activitiesDao.saveActivity(newActivity);
        log.info("Saved Activity");

        City cityToUpdate = citiesDao.getCityById(request.getCityId());
        cityToUpdate.getActivityList().add(newActivity.getActivityId());
        citiesDao.saveCity(cityToUpdate);
        log.info("Added Activity Id to City {}", cityToUpdate);

        ActivityModel activityModel = new ModelConverter().toActivityModel(newActivity);
        return CreateNewActivityResult.builder()
                .withActivityModel(activityModel)
                .build();
    }
}
