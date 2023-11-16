package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.TaLE.dynamodb.models.Activity;
import com.nashss.se.TaLE.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class ActivitiesDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher metricsPublisher;

    ActivitiesDao activitiesDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        activitiesDao = new ActivitiesDao(mapper, metricsPublisher);
    }

    @Test
    void createNewActivity_withValidFields_createsAndSavesActivity() {
        //Given
        Activity activity = new Activity();
        activity.setActivityName("Justin's  Test");

        Activity result = activitiesDao.saveActivity(activity);

        verify(mapper, times(1));
        assertEquals(activity, result);
    }
}
