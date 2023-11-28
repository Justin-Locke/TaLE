package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.DeleteCommentRequest;
import com.nashss.se.tale.activity.results.DeleteCommentResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class DeleteCommentTest {
    @Mock
    CommentsDao commentsDao;

    DeleteComment deleteComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deleteComment = new DeleteComment(commentsDao);
    }
    @Test
    void handleRequest_withValidRequest_removesCommentFromTable() {
       //Given a valid request
        DeleteCommentRequest request = new DeleteCommentRequest.Builder()
                .withUserId("JL9")
                .withActivityId("ACT1")
                .withCommentId("CMT9")
                .build();
        String message = "This message is Deleted.";
        //When
        when(commentsDao.deleteComment(anyString(), anyString())).thenReturn(message);
        DeleteCommentResult result = deleteComment.handleRequest(request);
        //Then
        assertNotNull(result);
        assertEquals(message, result.getDeleteResult());
    }
}