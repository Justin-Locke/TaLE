package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetCommentRequest;
import com.nashss.se.tale.activity.results.GetCommentResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetCommentTest {

    @Mock
    CommentsDao commentsDao;

    GetComment getComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getComment = new GetComment(commentsDao);
    }
    @Test
    void handleRequest_withValidRequest_returnsResult() {
        //Given
        GetCommentRequest request = new GetCommentRequest.Builder()
                .withActivityId("1234")
                .withCommentId("212353KC")
                .withUserId("JL312498")
                .build();
        Comment comment = new Comment();
        comment.setActivityId("1234");
        comment.setCommentId("212353KC");
        comment.setUserId("JL312498");
        //When
        when(commentsDao.getComment(anyString(), anyString())).thenReturn(comment);
        GetCommentResult result = getComment.handleRequest(request);
        //Then
        assertNotNull(result);
        CommentModel model = result.getCommentModel();
        assertEquals(comment.getCommentId(), model.getCommentId());
        assertEquals(comment.getActivityId(), model.getActivityId());
        assertEquals(comment.getUserId(), model.getUserId());

    }
}