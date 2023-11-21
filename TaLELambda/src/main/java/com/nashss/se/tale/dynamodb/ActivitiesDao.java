package com.nashss.se.tale.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.metrics.MetricsPublisher;

import javax.inject.Inject;

public class ActivitiesDao {
    private DynamoDBMapper mapper;
    private MetricsPublisher metricsPublisher;
    @Inject
    public ActivitiesDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Activity saveActivity(Activity activity) {
        this.mapper.save(activity);
        return activity;
    }

    public Activity getActivityById(String activityId) {
        Activity activity = mapper.load(Activity.class, activityId);
        return activity;
    }
}
