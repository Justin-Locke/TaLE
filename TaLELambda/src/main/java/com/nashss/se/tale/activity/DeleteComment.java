package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.DeleteCommentRequest;
import com.nashss.se.tale.activity.results.DeleteCommentResult;
import com.nashss.se.tale.dynamodb.CommentsDao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteComment {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;

    /**
     * Constructor for DeleteComment using Dagger.
     * @param commentsDao to instantiate the CommentsDao.
     */
    @Inject
    public DeleteComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    /**
     * Method to turn a DeleteCommentRequest to a DeleteCommentResult.
     * @param request to be handled from Lambda.
     * @return a DeleteCommentResult built from the result of the CommentsDao.
     */
    public DeleteCommentResult handleRequest(final DeleteCommentRequest request) {
        String commentId = request.getCommentId();
        String activityId = request.getActivityId();

        String result = commentsDao.deleteComment(activityId, commentId);
        return new DeleteCommentResult.Builder()
                .withDeleteResult(result)
                .build();
    }
}
