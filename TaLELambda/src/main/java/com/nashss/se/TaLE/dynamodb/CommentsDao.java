package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.TaLE.dynamodb.models.Comment;
import com.nashss.se.TaLE.metrics.MetricsPublisher;

import javax.inject.Inject;
import java.util.List;

public class CommentsDao {
    private DynamoDBMapper mapper;
    private MetricsPublisher metricsPublisher;

    @Inject
    public CommentsDao(DynamoDBMapper mapper, MetricsPublisher metricsPublisher) {
        this.mapper = mapper;
        this.metricsPublisher = metricsPublisher;
    }

    public Comment saveComment(Comment comment) {
        this.mapper.save(comment);
        return comment;
    }

    public List<Comment> getCommentsByActivityId(String activityId) {
        Comment comment = new Comment();
        comment.setActivityId(activityId);
        DynamoDBQueryExpression<Comment> queryExpression = new DynamoDBQueryExpression<Comment>()
                .withHashKeyValues(comment);
        PaginatedQueryList<Comment> commentList = mapper.query(Comment.class, queryExpression);
        return commentList;

    }
}
