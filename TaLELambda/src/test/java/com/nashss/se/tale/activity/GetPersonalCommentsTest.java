package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetPersonalCommentsRequest;
import com.nashss.se.tale.activity.results.GetPersonalCommentsResult;
import com.nashss.se.tale.dynamodb.CommentsDao;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.CommentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetPersonalCommentsTest {
    @Mock
    CommentsDao commentsDao;

    GetPersonalComments getPersonalComments;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getPersonalComments = new GetPersonalComments(commentsDao);
    }

    @Test
    void handleRequest_withValidRequest_returnsProperModel() {
        //Given
        String userId = "JL53872";
        GetPersonalCommentsRequest request = new GetPersonalCommentsRequest.Builder()
                .withUserId(userId)
                .build();

        Comment comment1 = new Comment();
        comment1.setUserId(userId);
        comment1.setActivityId("4301");

        Comment comment2 = new Comment();
        comment2.setUserId(userId);
        comment2.setActivityId("40829");
        List<Comment> commentList = new ArrayList<>();
        commentList.add(comment1);
        commentList.add(comment2);

        //When
        when(commentsDao.getAllPersonalComments(anyString())).thenReturn(commentList);
        GetPersonalCommentsResult result = getPersonalComments.handleRequest(request);

        //Then
        assertNotNull(result);
        List<CommentModel> commentModelList = result.getCommentModelList();
        assertEquals(commentList.size(), commentModelList.size());
        assertEquals(commentList.get(0).getActivityId(), commentModelList.get(0).getActivityId());
        assertEquals(commentList.get(1).getActivityId(), commentModelList.get(1).getActivityId());

    }


}