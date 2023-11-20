package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.TaLE.dynamodb.models.Comment;
import com.nashss.se.TaLE.metrics.MetricsPublisher;

import javax.inject.Inject;

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
}
