package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.CreateCommentRequest;
import com.nashss.se.tale.activity.results.CreateCommentResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;
import com.nashss.se.tale.utils.IdUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.time.LocalDate;

public class CreateComment {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;
    @Inject
    public CreateComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public CreateCommentResult handleRequest(final CreateCommentRequest request) {
        Comment newComment = new Comment();
        newComment.setCommentId(IdUtils.generateCommentId());
        newComment.setActivityId(request.getActivityId());
        newComment.setUserId(request.getUserId());
        newComment.setTitle(request.getTitle());
        newComment.setMessage(request.getMessage());
        newComment.setDatePosted(LocalDate.now());
        newComment.setEdited(false);

        commentsDao.saveComment(newComment);

        CommentModel commentModel = new ModelConverter().toCommentModel(newComment);

        return CreateCommentResult.builder()
                .withCommentModel(commentModel)
                .build();
    }
}
