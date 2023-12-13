package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.GetCommentsForActivityRequest;
import com.nashss.se.tale.activity.results.GetCommentsForActivityResult;
import com.nashss.se.tale.converters.ModelConverter;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

public class GetCommentsForActivity {
    private Logger log = LogManager.getLogger();
    private CommentsDao commentsDao;

    /**
     * Constructor for GetCommentsForActivity using Dagger.
     * @param commentsDao to instantiate CommentsDao.
     */
    @Inject
    public GetCommentsForActivity(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    /**
     * Method to use CommentsDao to retrieve Comments from DDB.
     * @param request from Lambda to get an ActivityId.
     * @return a GetCommentsForActivityResult from a list of Comment Models.
     */
    public GetCommentsForActivityResult handleRequest(final GetCommentsForActivityRequest request) {
        log.info("GetCommentsForActivity Request ={}", request);
        List<Comment> commentList = commentsDao.getCommentsByActivityId(request.getActivityId());
        log.info("List of comments retrieved ={}", commentList);
        List<CommentModel> commentModelList =
                commentList.stream()
                .map(comment -> new ModelConverter().toCommentModel(comment))
                .collect(Collectors.toList());

        return GetCommentsForActivityResult.builder()
                .withCommentModelList(commentModelList)
                .build();
    }
}
