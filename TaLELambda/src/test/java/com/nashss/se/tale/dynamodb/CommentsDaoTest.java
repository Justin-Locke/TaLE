package com.nashss.se.tale.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.metrics.MetricsConstants;
import com.nashss.se.tale.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CommentsDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher metricsPublisher;

    @Mock
    PaginatedQueryList<Comment> paginatedQueryList;

    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<Comment>> queryCaptor;

    CommentsDao commentsDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        commentsDao = new CommentsDao(mapper, metricsPublisher);

        when(mapper.query(eq(Comment.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);
        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
    }

    @Test
    void saveComment_validComment_isSaved() {
        //Given
        Comment comment = new Comment();
        comment.setTitle("First");
        //When
        Comment result = commentsDao.saveComment(comment);
        //Then
        assertEquals(comment, result);
        verify(mapper, times(1)).save(any());
    }

    @Test
    void getComment_withValidActivityIdAndValidCommentId_returnsComment() {
        //Given
        Comment comment = new Comment();
        comment.setActivityId("1");
        comment.setCommentId("abc");

        //When
        when(mapper.load(any(), anyString(), anyString())).thenReturn(comment);
        Comment result = commentsDao.getComment("1", "abc");

        assertEquals(comment, result);
        verify(mapper, times(1)).load(any(), anyString(), anyString());
        verify(metricsPublisher, times(1)).addCount(MetricsConstants.COMMENT_NULL_COUNT, 0);
    }

    @Test
    void getComment_withInvalidRequest_returnsNull() {
        //Given
        //When
        when(mapper.load(any(),anyString(),anyString())).thenReturn(null);
        Comment result = commentsDao.getComment("4", "124");
        //Then
        assertEquals(null, result);
        verify(metricsPublisher, times(1)).addCount(MetricsConstants.COMMENT_NULL_COUNT, 1);
    }

    @Test
    void getAllPersonalComments_withValidUserId_callsGSI() {
        //Given valid userId
        String userId = "J23";
        //When
        List<Comment> result = commentsDao.getAllPersonalComments(userId);
        //Then
        assertEquals(paginatedQueryList, result);
        verify(metricsPublisher, times(1)).addMetric(anyString(), anyDouble(), any());


    }

    @Test
    void deleteComment_withValidFields_removesFromTable() {
        //Given
        String activityId = "act1";
        String commentId = "Ct94";
        //When
        commentsDao.deleteComment(activityId, commentId);
        //Then
        verify(mapper, times(1)).delete(any());
    }

    @Test
    void getCommentsByActivityId_withValidId_returnsListOfComments() {
        //Given
        String activityId = "act1";
        //When
        List<Comment> result = commentsDao.getCommentsByActivityId(activityId);

        //Then
        assertNotNull(result);
        assertEquals(paginatedQueryList, result);
    }

}
