package com.nashss.se.tale.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.metrics.MetricsPublisher;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public List<Activity> getAllPersonalActivities(String userId) {
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        DynamoDBQueryExpression<Activity> queryExpression = new DynamoDBQueryExpression<Activity>()
                .withIndexName("ActivitiesByUserIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Activity> activityList = mapper.query(Activity.class, queryExpression);
        return activityList;
    }
}
