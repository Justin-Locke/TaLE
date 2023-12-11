package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetAllActivitiesRequest;
import com.nashss.se.tale.activity.results.GetAllActivitiesResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetAllActivitiesTest {
    @Mock
    ActivitiesDao activitiesDao;
    @Mock
    CitiesDao citiesDao;
    GetAllActivities getAllActivities;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getAllActivities = new GetAllActivities(activitiesDao, citiesDao);
    }
    @Test
    void handleRequest_withValidRequest_returnsResult() {
        //Given
        String cityId = "235";
        City myCity = new City();
        myCity.setCityId(cityId);
        List<String> activityIds = new ArrayList<>();
        activityIds.add("1");
        activityIds.add("2");
        activityIds.add("3");
        myCity.setActivityList(activityIds);

        GetAllActivitiesRequest request = new GetAllActivitiesRequest.Builder()
                .withCityId(cityId)
                .build();

        Activity activity1 = new Activity();
        activity1.setActivityId("1");
        Activity activity2 = new Activity();
        activity2.setActivityId("2");
        Activity activity3 = new Activity();
        activity3.setActivityId("3");


        //When
        when(citiesDao.getCityById(anyString())).thenReturn(myCity);
        when(activitiesDao.getActivityById("1")).thenReturn(activity1);
        when(activitiesDao.getActivityById("2")).thenReturn(activity2);
        when(activitiesDao.getActivityById("3")).thenReturn(activity3);

        GetAllActivitiesResult result = getAllActivities.handleRequest(request);
        //Then
        assertNotNull(result);
        assertEquals(3, result.getActivityModelList().size());

    }
}