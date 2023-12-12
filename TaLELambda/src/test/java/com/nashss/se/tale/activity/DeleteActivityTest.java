package com.nashss.se.tale.activity;


import com.nashss.se.tale.activity.requests.DeleteActivityRequest;
import com.nashss.se.tale.activity.results.DeleteActivityResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DeleteActivityTest {
    @Mock
    ActivitiesDao activitiesDao;

    DeleteActivity deleteActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deleteActivity = new DeleteActivity(activitiesDao);
    }

    @Test
    void handleRequest_withValidRequest_removesActivityFromTable() {
        //Given a valid request
        DeleteActivityRequest request = new DeleteActivityRequest.Builder()
                .withActivityId("JLP9")
                .withUserId("0923")
                .build();

        String message = "This activity is Deleted.";

        //When
        when(activitiesDao.deleteActivity((anyString()))).thenReturn(message);
        DeleteActivityResult result = deleteActivity.handleRequest(request);
        //Then
        assertNotNull(result);
        assertEquals(message, result.getDeleteResult());
        verify(activitiesDao, times(1)).deleteActivity(any());
    }

}