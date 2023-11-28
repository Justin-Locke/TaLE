package com.nashss.se.tale.converters;

import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.ActivityModel;
import com.nashss.se.tale.models.CityModel;
import com.nashss.se.tale.models.CommentModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelConverterTest {

    ModelConverter modelConverter;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        modelConverter = new ModelConverter();
    }
    @Test
    void toCityModel_withCity_returnsModel() {
        //Given
        List<String> activityIds = new ArrayList<>();
        activityIds.add("1");
        activityIds.add("2");
        activityIds.add("3");
        City city = new City();
        city.setCityId("NV12367");
        city.setCityName("Nashville");
        city.setActivityList(activityIds);
        //When
        CityModel model = modelConverter.toCityModel(city);
        //Then
        assertEquals(activityIds, model.getActivityList());
        assertEquals(city.getCityId(), model.getCityId());
        assertEquals(city.getCityName(), model.getCityName());
    }

    @Test
    void toActivityModel_withActivity_returnsModel() {
        //Given
        Activity activity = new Activity();
        activity.setActivityId("1");
        activity.setUserId("JL792");
        activity.setEdited(false);
        activity.setDatePosted(LocalDate.now());
        activity.setActivityName("Fun House");
        activity.setDescription("It is a house of fun.");
        activity.setPosterExperience("We had a fun time being in this house.");
        //When
        ActivityModel model = modelConverter.toActivityModel(activity);
        //Then
        assertEquals(activity.getActivityId(), model.getActivityId());
        assertEquals(activity.getActivityName(), model.getActivityName());
        assertEquals(activity.getDatePosted(), model.getDatePosted());
        assertEquals(activity.getEdited(), model.getEdited());
        assertEquals(activity.getUserId(), model.getUserId());
        assertEquals(activity.getDescription(), model.getDescription());
        assertEquals(activity.getPosterExperience(), model.getPosterExperience());

    }

    @Test
    void toCommentModel_withComment_returnsComment() {
        //Given
        Comment comment = new Comment();
        comment.setCommentId("2459");
        comment.setActivityId("Act34");
        comment.setUserId("JL34002");
        comment.setTitle("Fun");
        comment.setMessage("This is a fun house.");
        comment.setDatePosted(LocalDate.now());
        comment.setEdited(false);
        //When
        CommentModel model = modelConverter.toCommentModel(comment);
        //Then
        assertEquals(comment.getCommentId(), model.getCommentId());
        assertEquals(comment.getActivityId(), model.getActivityId());
        assertEquals(comment.getUserId(), model.getUserId());
        assertEquals(comment.getTitle(), model.getTitle());
        assertEquals(comment.getMessage(), model.getMessage());
        assertEquals(comment.getDatePosted(), model.getDatePosted());
        assertEquals(comment.getEdited(), model.getEdited());
    }
}