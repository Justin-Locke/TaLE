package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.TaLE.dynamodb.models.Activity;
import com.nashss.se.TaLE.metrics.MetricsPublisher;

import javax.inject.Inject;

public class ActivitiesDao {
    private final DynamoDBMapper mapper;
    private final MetricsPublisher metricsPublisher;
    @Inject
    public ActivitiesDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Activity saveActivity(Activity activity) {
        this.mapper.save(activity);
        return activity;
    }
}
