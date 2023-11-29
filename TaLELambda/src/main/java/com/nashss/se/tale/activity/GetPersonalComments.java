package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetPersonalCommentsRequest;
import com.nashss.se.tale.activity.results.GetPersonalCommentsResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class GetPersonalComments {
    private Logger log = LogManager.getLogger();
    private CommentsDao commentsDao;

    /**
     * Constructor for GetPersonalComments using Dagger.
     * @param commentsDao to instantiate CommentsDao for DDB.
     */
    @Inject
    public GetPersonalComments(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    /**
     * Method to Get User Comments from GSI using Comments Dao.
     * @param request containing UserId.
     * @return a List of Comments from User.
     */
    public GetPersonalCommentsResult handleRequest(final GetPersonalCommentsRequest request) {
        List<Comment> commentList = commentsDao.getAllPersonalComments(request.getUserId());

        List<CommentModel> commentModelList = new ArrayList<>();

        for (Comment comment : commentList) {
            if (comment == null) {
                continue;
            }
            CommentModel commentModel = new ModelConverter().toCommentModel(comment);
            commentModelList.add(commentModel);
        }

        return GetPersonalCommentsResult.builder()
                .withCommentModelList(commentModelList)
                .build();
    }
}
