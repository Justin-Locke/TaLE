package com.nashss.se.TaLE.activity;

import com.nashss.se.TaLE.activity.requests.GetCommentsForActivityRequest;
import com.nashss.se.TaLE.activity.results.GetCommentsForActivityResult;
import com.nashss.se.TaLE.converters.ModelConverter;
import com.nashss.se.TaLE.dynamodb.CommentsDao;
import com.nashss.se.TaLE.dynamodb.models.Comment;
import com.nashss.se.TaLE.models.CommentModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

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
                .toList();

        return GetCommentsForActivityResult.builder()
                .withCommentModelList(commentModelList)
                .build();
    }
}
