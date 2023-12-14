package com.nashss.se.tale.dynamodb.models;
import com.nashss.se.tale.converters.LocalDateConverter;
import com.nashss.se.tale.dynamodb.indexes.DynamoDBIndexConstants;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

import java.time.LocalDate;
import java.util.Objects;

@DynamoDBTable(tableName = "comments")
public class Comment {
    private String activityId;
    private String commentId;
    private String title;
    private String message;
    private String userId;
    private LocalDate datePosted;
    private Boolean edited;


    @DynamoDBHashKey(attributeName = "activityId")
    @DynamoDBIndexRangeKey(globalSecondaryIndexName = DynamoDBIndexConstants.COMMENTS_BY_USER_INDEX)
    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    @DynamoDBRangeKey(attributeName = "commentId")
    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DynamoDBAttribute(attributeName = "message")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @DynamoDBIndexHashKey(globalSecondaryIndexName = DynamoDBIndexConstants.COMMENTS_BY_USER_INDEX,
            attributeName = "userId")
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(activityId, comment.activityId) &&
                Objects.equals(commentId, comment.commentId) &&
                Objects.equals(title, comment.title) &&
                Objects.equals(message, comment.message) &&
                Objects.equals(userId, comment.userId) &&
                Objects.equals(datePosted, comment.datePosted) &&
                Objects.equals(edited, comment.edited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, commentId, title, message, userId, datePosted, edited);
    }
}
