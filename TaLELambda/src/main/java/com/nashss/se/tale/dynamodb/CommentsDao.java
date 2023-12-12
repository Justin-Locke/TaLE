package com.nashss.se.tale.dynamodb;
import com.nashss.se.tale.dynamodb.models.Comment;
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

public class CommentsDao {
    private DynamoDBMapper mapper;
    private MetricsPublisher metricsPublisher;

    /**
     * Constructor for CommentsDao.
     * @param mapper to talk to DynamoDB.
     * @param metricsPublisher to publish metrics.
     */
    @Inject
    public CommentsDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * Method to save Comment to comments table.
     * @param comment to be saved.
     * @return saved comment.
     */
    public Comment saveComment(Comment comment) {
        this.mapper.save(comment);
        return comment;
    }

    /**
     * Method to get a list of comments associated with an ActivityId.
     * @param activityId to find a list of comments.
     * @return a list of comments.
     */
    public List<Comment> getCommentsByActivityId(String activityId) {
        double startTimer = System.currentTimeMillis();
        Comment comment = new Comment();
        comment.setActivityId(activityId);
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment);
        PaginatedQueryList<Comment> commentList = mapper.query(Comment.class, queryExpression);
        double totalTime = System.currentTimeMillis() - startTimer;
        metricsPublisher.addMetric("GET_COMMENTS_BY_ACTIVITY_ID_PROCESSING_TIME", totalTime, StandardUnit.Milliseconds);

        return commentList;
    }

    /**
     * Method to get a single comment.
     * @param activityId is the primary key.
     * @param commentId is the sort key.
     * @return single comment.
     */
    public Comment getComment(String activityId, String commentId) {
        Comment comment = mapper.load(Comment.class, activityId, commentId);
        if (comment == null) {
            metricsPublisher.addCount(MetricsConstants.COMMENT_NULL_COUNT, 1);
        }

        metricsPublisher.addCount(MetricsConstants.CITY_NULL_COUNT, 0);
        return comment;
    }

    /**
     * Method to delete comment from DDB.
     * @param activityId is the primary key.
     * @param commentId is the sort key.
     * @return String to say comment was deleted.
     */
    public String deleteComment(String activityId, String commentId) {
        Comment commentToDelete = new Comment();
        commentToDelete.setCommentId(commentId);
        commentToDelete.setActivityId(activityId);
        mapper.delete(commentToDelete);

        return String.format("Comment %s successfully deleted.", commentId);
    }

    /**
     * Method to get personally posted comment from GSI table.
     * @param userId comments are associated with.
     * @return a list of users comments.
     */
    public List<Comment> getAllPersonalComments(String userId) {
        double startTime = System.currentTimeMillis();
        Map<String, AttributeValue> valueMap = new HashMap<>();
        valueMap.put(":userId", new AttributeValue().withS(userId));
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
                .withIndexName("CommentsByUserIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("userId = :userId")
                .withExpressionAttributeValues(valueMap);

        PaginatedQueryList<Comment> commentList = mapper.query(Comment.class, queryExpression);

        double totalTime = System.currentTimeMillis() - startTime;
        metricsPublisher.addMetric(MetricsConstants.PERSONAL_LOOKUP_TIME, totalTime, StandardUnit.Milliseconds);

        return commentList;
    }
}
