package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.EditCommentRequest;
import com.nashss.se.tale.activity.results.EditCommentResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EditCommentTest {
    @Mock
    CommentsDao commentsDao;

    EditComment editComment;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        editComment = new EditComment(commentsDao);
    }
    @Test
    void handleRequest_withValidFields_returnsCorrectResult() {
        //Given a valid request
        Comment oldComment = new Comment();
        oldComment.setCommentId("CM923");
        oldComment.setEdited(false);
        oldComment.setTitle("Sad Day");
        oldComment.setMessage("I'm so sad today.");
        oldComment.setDatePosted(LocalDate.now());
        oldComment.setActivityId("ACT1");
        oldComment.setUserId("JLy03");
        EditCommentRequest request = new EditCommentRequest.Builder()
                .withActivityId("ACT1")
                .withCommentId("CM923")
                .withUserId("JLy03")
                .withTitle("Happy Day")
                .withMessage("I'm so happy today.")
                .build();
        //When
        when(commentsDao.getComment(anyString(), anyString())).thenReturn(oldComment);
        EditCommentResult result = editComment.handleRequest(request);
        //Then
        assertNotNull(result);
        CommentModel model = result.getCommentModel();
        assertTrue(model.getEdited());
        assertEquals(oldComment.getCommentId(), model.getCommentId());
        assertEquals(oldComment.getActivityId(), model.getActivityId());
        assertEquals(oldComment.getDatePosted(), model.getDatePosted());
        assertEquals(oldComment.getUserId(), model.getUserId());
        assertNotEquals("Sad Day", model.getTitle());
        assertNotEquals("I'm so sad today.", model.getMessage());
    }
}