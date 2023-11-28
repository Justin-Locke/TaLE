package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.CreateNewActivityRequest;
import com.nashss.se.tale.activity.results.CreateNewActivityResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.ActivityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CreateNewActivityTest {
    @Mock
    ActivitiesDao activitiesDao;
    @Mock
    CitiesDao citiesDao;
    CreateNewActivity createNewActivity;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        createNewActivity = new CreateNewActivity(activitiesDao, citiesDao);

    }

    @Test
    void handleRequest_withValidFields_returnsCreateNewActivityModel() {
        //Given a valid request
        CreateNewActivityRequest request = CreateNewActivityRequest.builder()
                .withCityId("CITY")
                .withUserId("JL9")
                .withDescription("Description")
                .withActivityName("ACTIVITY")
                .withPosterExperience("MY THOUGHTS EXACTLY")
                .build();
        City cityToUpdate = new City();
        List<String> activityIdList = new ArrayList<>();
        activityIdList.add("ACO2490");
        cityToUpdate.setActivityList(activityIdList);
        //When
        when(citiesDao.getCityById(anyString())).thenReturn(cityToUpdate);
        CreateNewActivityResult result = createNewActivity.handleRequest(request);
        //Then
        assertNotNull(result, "Expected result to not be null");
        ActivityModel model = result.getActivityModel();
        assertNotNull(model.getActivityId());
        assertEquals(request.getUserId(), model.getUserId());
        assertNotNull(model.getDatePosted());
        assertEquals(false, model.getEdited());
        assertEquals(request.getActivityName(), model.getActivityName());
        assertEquals(request.getDescription(), model.getDescription());
        assertEquals(request.getPosterExperience(), model.getPosterExperience());
        assertEquals(2, cityToUpdate.getActivityList().size());
    }
}