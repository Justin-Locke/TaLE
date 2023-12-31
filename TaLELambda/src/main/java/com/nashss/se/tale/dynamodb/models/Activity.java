package com.nashss.se.tale.dynamodb.models;
import com.nashss.se.tale.converters.LocalDateConverter;
import com.nashss.se.tale.dynamodb.indexes.DynamoDBIndexConstants;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.time.LocalDate;
import java.util.Objects;

@DynamoDBTable(tableName = "activities")
public class Activity {
    private String activityId;
    private String userId;
    private String activityName;
    private String description;
    private LocalDate datePosted;
    private Boolean edited;
    private String posterExperience;

    @DynamoDBHashKey(attributeName = "activityId")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = DynamoDBIndexConstants.ACTIVITIES_BY_USER_INDEX)
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = DynamoDBIndexConstants.ACTIVITIES_BY_USER_INDEX,
            attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @DynamoDBAttribute(attributeName = "activityName")
    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @DynamoDBAttribute(attributeName = "datePosted")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    public LocalDate getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDate datePosted) {
        this.datePosted = datePosted;
    }

    @DynamoDBAttribute(attributeName = "edited")
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    public Boolean getEdited() {
        return edited;
    }

    public void setEdited(Boolean edited) {
        this.edited = edited;
    }

    @DynamoDBAttribute(attributeName = "posterExperience")
    public String getPosterExperience() {
        return posterExperience;
    }

    public void setPosterExperience(String posterExperience) {
        this.posterExperience = posterExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Activity activity = (Activity) o;
        return Objects.equals(activityId, activity.activityId) &&
                Objects.equals(userId, activity.userId) &&
                Objects.equals(activityName, activity.activityName) &&
                Objects.equals(description, activity.description) &&
                Objects.equals(datePosted, activity.datePosted) &&
                Objects.equals(edited, activity.edited) &&
                Objects.equals(posterExperience, activity.posterExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, userId, activityName, description, datePosted, edited, posterExperience);
    }
}


