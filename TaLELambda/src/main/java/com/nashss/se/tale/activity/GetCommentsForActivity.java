package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetCommentsForActivityRequest;
import com.nashss.se.tale.activity.results.GetCommentsForActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class GetCommentsForActivity {
    private Logger log = LogManager.getLogger();
    private CommentsDao commentsDao;

    @Inject

    public GetCommentsForActivity(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public GetCommentsForActivityResult handleRequest(final GetCommentsForActivityRequest request) {
        List<Comment> commentList = commentsDao.getCommentsByActivityId(request.getActivityId());

        List<CommentModel> commentModelList =
                commentList.stream()
                .map(comment -> new ModelConverter().toCommentModel(comment))
                .collect(Collectors.toList());

        return GetCommentsForActivityResult.builder()
                .withCommentModelList(commentModelList)
                .build();
    }
}
