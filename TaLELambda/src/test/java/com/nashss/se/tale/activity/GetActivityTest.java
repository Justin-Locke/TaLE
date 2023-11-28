package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetActivityRequest;
import com.nashss.se.tale.activity.results.GetActivityResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.models.ActivityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetActivityTest {
    @Mock
    ActivitiesDao activitiesDao;
    GetActivity getActivity;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getActivity = new GetActivity(activitiesDao);
    }
    @Test
    void handleRequest_withValidRequest_returnsExpectedResult() {
        //Given
        GetActivityRequest request = new GetActivityRequest.Builder()
                .withActivityId("ACT1245")
                .build();
        Activity activity = new Activity();
        activity.setActivityId("ACT1245");

        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(activity);
        GetActivityResult result = getActivity.handleRequest(request);

        //Then
        assertNotNull(result);
        ActivityModel model = result.getActivityModel();
        assertEquals(request.getActivityId(), model.getActivityId());

    }
}