package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.CreateCommentRequest;
import com.nashss.se.tale.activity.results.CreateCommentResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.models.CommentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CreateCommentTest {
    @Mock
    CommentsDao commentsDao;

    CreateComment createComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        createComment = new CreateComment(commentsDao);
    }

    @Test
    void handleRequest_withValidRequest_returnsCreateCommentResult() {
        //Given a valid request
        CreateCommentRequest request = new CreateCommentRequest.Builder()
                .withActivityId("ACT1")
                .withUserId("USR1")
                .withTitle("Good Soup")
                .withMessage("It's good soup.")
                .build();

        //When
        CreateCommentResult result = createComment.handleRequest(request);


        assertNotNull(result);
        CommentModel model = result.getCommentModel();
        assertEquals(false, model.getEdited());
        assertNotNull(model.getDatePosted());
        assertEquals(request.getActivityId(), model.getActivityId());
        assertEquals(request.getUserId(), model.getUserId());
        assertEquals(request.getTitle(), model.getTitle());
        assertEquals(request.getMessage(), model.getMessage());
    }
}