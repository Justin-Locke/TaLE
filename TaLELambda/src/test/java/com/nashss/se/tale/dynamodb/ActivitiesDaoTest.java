package com.nashss.se.tale.dynamodb;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.metrics.MetricsConstants;
import com.nashss.se.tale.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

    Activity ACTIVITY_1;
    Activity ACTIVITY_2;
    Activity ACTIVITY_3;
    String VALID_USER_ID = "1234";
    String VALID_USER_ID2 = "2345";



    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        activitiesDao = new ActivitiesDao(mapper, metricsPublisher);

        when(mapper.query(eq(Activity.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);

        ACTIVITY_1 = new Activity();
        ACTIVITY_1.setActivityId("A1111CT");
        ACTIVITY_1.setUserId(VALID_USER_ID);
        ACTIVITY_2 = new Activity();
        ACTIVITY_2.setActivityId("A234912tyY");
        ACTIVITY_2.setUserId(VALID_USER_ID2);
        ACTIVITY_3 = new Activity();
        ACTIVITY_3.setActivityId("A850238");
        ACTIVITY_3.setUserId(VALID_USER_ID);

    }

    @Test
    void saveActivity_withValidFields_createsAndSavesActivity() {
        //Given
        Activity activity = new Activity();
        activity.setActivityName("Justin's  Test");

        Activity result = activitiesDao.saveActivity(activity);

        assertEquals(activity, result);
        verify(mapper, times(1)).save(any());
    }

    @Test
    void getActivityById_withValidId_returnsActivity() {
        //Given
        // A Valid ActivityID
        //When
        when(mapper.load(any(), anyString())).thenReturn(ACTIVITY_1);
        Activity result = activitiesDao.getActivityById("1234");
        assertEquals(ACTIVITY_1, result);
        verify(metricsPublisher, times(1)).addCount(MetricsConstants.ACTIVITY_NULL_COUNT, 0);
    }

    @Test
    void getActivityById_nullActivityReturned_addsMetricCountAndReturnsNullActivity() {
        //Given
        //When
        when(mapper.load(any(), anyString())).thenReturn(null);
        Activity result = activitiesDao.getActivityById("04f");
        //Then
        assertEquals(null, result);
        verify(metricsPublisher, times(1)).addCount(MetricsConstants.ACTIVITY_NULL_COUNT, 1);
    }

    @Test
    void getAllPersonalActivities_withValidUserId_returnsAllActivitiesForUser() {
        //Given
        //When
        when(mapper.query(any(), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);
        List<Activity> results = activitiesDao.getAllPersonalActivities(VALID_USER_ID);

        //Then
        assertNotNull(results);
        assertEquals(paginatedQueryList, results);
        verify(metricsPublisher, times(1)).addMetric(anyString(), anyDouble(), any());
    }

    @Test
    void deleteActivity_withValidActivityId_removesActivityFromDatabase() {
        //Given
        String activityId = "act1";
        //When
        activitiesDao.deleteActivity(activityId);
        //Then
        verify(mapper, times(1)).delete(any());
    }

}
