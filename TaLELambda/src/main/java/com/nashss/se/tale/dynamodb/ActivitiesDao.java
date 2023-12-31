package com.nashss.se.tale.dynamodb;
import com.nashss.se.tale.dynamodb.indexes.DynamoDBIndexConstants;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.metrics.MetricsConstants;
import com.nashss.se.tale.metrics.MetricsPublisher;

import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

public class ActivitiesDao {
    private DynamoDBMapper mapper;
    private MetricsPublisher metricsPublisher;

    /**
     * Constructor for ActivitiesDao.
     * @param mapper to talk to DDB.
     * @param metricsPublisher to publish metrics.
     */
    @Inject
    public ActivitiesDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Method to save Activity to DDB Table.
     * @param activity to be saved.
     * @return saved Activity.
     */
    public Activity saveActivity(Activity activity) {
        this.mapper.save(activity);
        return activity;
    }

    /**
     * Method to delete a single activity.
     * @param activityId to be deleted.
     * @return String message.
     */
    public String deleteActivity(String activityId) {
        Activity activityToDelete = new Activity();
        activityToDelete.setActivityId(activityId);
        mapper.delete(activityToDelete);

        return "Activity deleted.";
    }

    /**
     * Method to get a single Activity.
     * @param activityId to get single Activity.
     * @return single Activity.
     */
    public Activity getActivityById(String activityId) {
        Activity activity = mapper.load(Activity.class, activityId);
        if (activity == null) {
            metricsPublisher.addCount(MetricsConstants.ACTIVITY_NULL_COUNT, 1);
        } else {
            metricsPublisher.addCount(MetricsConstants.ACTIVITY_NULL_COUNT, 0);
        }
        return activity;
    }

    /**
     * Method to return all personally posted Activities from user via GSI table.
     * @param userId to pull related Activities from GSI.
     * @return a List of Activity.
     */
    public List<Activity> getAllPersonalActivities(String userId) {
        double startTime = System.currentTimeMillis();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        DynamoDBQueryExpression<Activity> queryExpression = new DynamoDBQueryExpression<Activity>()
                .withIndexName(DynamoDBIndexConstants.ACTIVITIES_BY_USER_INDEX)
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Activity> activityList = mapper.query(Activity.class, queryExpression);
        double totalTime = System.currentTimeMillis() - startTime;
        metricsPublisher.addMetric(MetricsConstants.PERSONAL_LOOKUP_TIME, totalTime, StandardUnit.Milliseconds);
        return activityList;
    }
}
