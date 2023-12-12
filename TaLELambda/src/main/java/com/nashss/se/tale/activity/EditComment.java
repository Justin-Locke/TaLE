package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.EditCommentRequest;
import com.nashss.se.tale.activity.results.EditCommentResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.exceptions.EmptyFieldInRequestException;
import com.nashss.se.tale.exceptions.InvalidUserException;
import com.nashss.se.tale.models.CommentModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EditComment {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;

    /**
     * Constructor for EditComment using Dagger.
     * @param commentsDao to instantiate CommentsDao for DDB.
     */
    @Inject
    public EditComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    /**
     * Method to Update an existing comment.
     * @param request containing updated Title, updated Message for Comment.
     * @return Updated Comment with Edited field set to "true".
     */
    public EditCommentResult handleRequest(final EditCommentRequest request) {
        if (request.getTitle().isEmpty()) {
            log.warn("Request Title is Empty");
            throw new EmptyFieldInRequestException("Your comment must contain a title.");
        }
        if (request.getMessage().isEmpty()) {
            log.warn("Request Message is Empty");
            throw new EmptyFieldInRequestException("Your comment has no message content.");
        }

        Comment commentToUpdate = commentsDao.getComment(request.getActivityId(), request.getCommentId());
        log.info("Comment to update ={}", commentToUpdate);
        if (!commentToUpdate.getUserId().equals(request.getUserId())) {
            log.error("INVALID USER MAKING AUTHENTICATED REQUEST");
            throw new InvalidUserException("You are not allowed to edit this comment...How did you get here?");
        }

        commentToUpdate.setTitle(request.getTitle());
        commentToUpdate.setMessage(request.getMessage());
        commentToUpdate.setEdited(true);

        commentsDao.saveComment(commentToUpdate);
        log.info("Saved updated Comment ={}", commentToUpdate);
        CommentModel commentModel = new ModelConverter().toCommentModel(commentToUpdate);

        return EditCommentResult.builder()
                .withCommentModel(commentModel)
                .build();
    }
}
