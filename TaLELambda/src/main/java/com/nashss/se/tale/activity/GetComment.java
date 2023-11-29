package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetCommentRequest;
import com.nashss.se.tale.activity.results.GetCommentResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetComment {
    private Logger log = LogManager.getLogger();
    private CommentsDao commentsDao;

    /**
     * Constructor for GetComment using Dagger.
     * @param commentsDao to instantiate CommentDao for DDB access.
     */
    @Inject
    public GetComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    /**
     * Method to Get a single comment from Request.
     * @param request containing and ActivityID and a CommentId.
     * @return a Comment.
     */
    public GetCommentResult handleRequest(final GetCommentRequest request) {
        Comment comment = commentsDao.getComment(request.getActivityId(), request.getCommentId());

        CommentModel commentModel = new ModelConverter().toCommentModel(comment);

        return GetCommentResult.builder()
                .withCommentModel(commentModel)
                .build();
    }
}
