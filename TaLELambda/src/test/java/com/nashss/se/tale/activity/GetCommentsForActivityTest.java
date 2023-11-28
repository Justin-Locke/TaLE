package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetCommentsForActivityRequest;
import com.nashss.se.tale.activity.results.GetCommentsForActivityResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetCommentsForActivityTest {

    @Mock
    CommentsDao commentsDao;
    GetCommentsForActivity getCommentsForActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getCommentsForActivity = new GetCommentsForActivity(commentsDao);
    }
    @Test
    void handleRequest_withValidRequest_returnsResult() {
        //Given
        GetCommentsForActivityRequest request = new GetCommentsForActivityRequest.Builder()
                .withActivityId("2123")
                .build();
        List<Comment> commentList = new ArrayList<>();
        Comment comment1 = new Comment();
        comment1.setActivityId("2123");
        Comment comment2 = new Comment();
        comment2.setActivityId("2123");
        commentList.add(comment1);
        commentList.add(comment2);

        //When
        when(commentsDao.getCommentsByActivityId(anyString())).thenReturn(commentList);
        GetCommentsForActivityResult result = getCommentsForActivity.handleRequest(request);
        //Then
        assertNotNull(result);
        List<CommentModel> modelList = result.getCommentModelList();
        assertEquals(comment1.getActivityId(), modelList.get(0).getActivityId());
    }
}