package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.CreateCommentRequest;
import com.nashss.se.tale.activity.results.CreateCommentResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.exceptions.EmptyFieldInRequestException;
import com.nashss.se.tale.models.CommentModel;
import com.nashss.se.tale.utils.IdUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import javax.inject.Inject;

public class CreateComment {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;

    /**
     * Constructor for CreateComment using Dagger.
     * @param commentsDao to instantiate CommentsDao for DDB.
     */
    @Inject
    public CreateComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    /**
     * Method to Create Comment from Request.
     * @param request from Lambda.
     * @return the Created Comment.
     */
    public CreateCommentResult handleRequest(final CreateCommentRequest request) {
        if (request.getTitle().isEmpty() || request.getMessage().isEmpty()) {
            log.warn("Request title or message is empty");
            throw new EmptyFieldInRequestException("Comments must contain both a Title and a Message.");
        }

        Comment newComment = new Comment();
        newComment.setCommentId(IdUtils.generateCommentId());
        newComment.setActivityId(request.getActivityId());
        newComment.setUserId(request.getUserId());
        newComment.setTitle(request.getTitle());
        newComment.setMessage(request.getMessage());
        newComment.setDatePosted(LocalDate.now());
        newComment.setEdited(false);

        commentsDao.saveComment(newComment);
        log.info("Saved comment");
        CommentModel commentModel = new ModelConverter().toCommentModel(newComment);

        return CreateCommentResult.builder()
                .withCommentModel(commentModel)
                .build();
    }
}
