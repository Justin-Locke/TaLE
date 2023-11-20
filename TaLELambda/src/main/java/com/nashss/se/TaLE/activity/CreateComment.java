package com.nashss.se.TaLE.activity;

import com.nashss.se.TaLE.activity.requests.CreateCommentRequest;
import com.nashss.se.TaLE.activity.results.CreateCommentResult;
import com.nashss.se.TaLE.converters.ModelConverter;
import com.nashss.se.TaLE.dynamodb.CommentsDao;
import com.nashss.se.TaLE.dynamodb.models.Comment;
import com.nashss.se.TaLE.models.CommentModel;
import com.nashss.se.TaLE.utils.IdUtils;
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
