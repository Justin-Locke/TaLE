package com.nashss.se.tale.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ActivitiesDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher metricsPublisher;

    @Mock
    PaginatedQueryList<Activity> paginatedQueryList;

    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<Activity>> queryCaptor;

    ActivitiesDao activitiesDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        activitiesDao = new ActivitiesDao(mapper, metricsPublisher);

        when(mapper.query(eq(Activity.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
    }

    @Test
    void createNewActivity_withValidFields_createsAndSavesActivity() {
        //Given
        Activity activity = new Activity();
        activity.setActivityName("Justin's  Test");

        Activity result = activitiesDao.saveActivity(activity);

        assertEquals(activity, result);
        verify(mapper, times(1)).save(any());
    }
}
