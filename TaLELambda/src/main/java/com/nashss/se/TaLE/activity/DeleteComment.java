package com.nashss.se.TaLE.activity;

import com.nashss.se.TaLE.activity.requests.DeleteCommentRequest;
import com.nashss.se.TaLE.activity.results.DeleteCommentResult;
import com.nashss.se.TaLE.dynamodb.CommentsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteComment {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;

    @Inject
    public DeleteComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public DeleteCommentResult handleRequest(final DeleteCommentRequest request) {
        String commentId = request.getCommentId();
        String activityId = request.getActivityId();

        String result = commentsDao.deleteComment(commentId, activityId);
        return new DeleteCommentResult.Builder()
                .withDeleteResult(result)
                .build();
    }
}