package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetPersonalActivitiesRequest;
import com.nashss.se.tale.activity.results.GetPersonalActivitiesResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetPersonalActivitiesTest {

    @Mock
    ActivitiesDao activitiesDao;

    GetPersonalActivities getPersonalActivities;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getPersonalActivities = new GetPersonalActivities(activitiesDao);
    }

    @Test
    void handleRequest_withValidRequest_returnsCorrectModel() {
        //Given
        String userId = "197dlj3";
        GetPersonalActivitiesRequest request = new GetPersonalActivitiesRequest.Builder()
                .withUserId(userId)
                .build();
        Activity activity1 = new Activity();
        activity1.setUserId(userId);
        activity1.setActivityId("401");

        Activity activity2 = new Activity();
        activity2.setUserId(userId);
        activity2.setActivityId("802");

        List<Activity> activityList = new ArrayList<>();
        activityList.add(activity1);
        activityList.add(activity2);


        //When
        when(activitiesDao.getAllPersonalActivities(anyString())).thenReturn(activityList);
        GetPersonalActivitiesResult result = getPersonalActivities.handleRequest(request);

        //Then
        assertNotNull(result);
        List<ActivityModel> modelList = result.getActivityModelList();
        assertEquals(activityList.size(), modelList.size());
        assertEquals(activityList.get(0).getActivityId(), modelList.get(0).getActivityId());
        assertEquals(activityList.get(1).getActivityId(), modelList.get(1).getActivityId());

    }
}