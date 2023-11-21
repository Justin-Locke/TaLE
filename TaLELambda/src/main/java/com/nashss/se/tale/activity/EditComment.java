package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.EditCommentRequest;
import com.nashss.se.tale.activity.results.EditCommentResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EditComment {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;
    @Inject
    public EditComment(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public EditCommentResult handleRequest(final EditCommentRequest request) {
        return null;
    }
}
