package com.nashss.se.tale.activity;
import com.nashss.se.tale.activity.requests.EditActivityRequest;
import com.nashss.se.tale.activity.results.EditActivityResult;
import com.nashss.se.tale.dynamodb.ActivitiesDao;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.exceptions.EmptyFieldInRequestException;
import com.nashss.se.tale.exceptions.InvalidUserException;
import com.nashss.se.tale.models.ActivityModel;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class EditActivityTest {
    @Mock
    ActivitiesDao activitiesDao;

    EditActivity editActivity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        editActivity = new EditActivity(activitiesDao);
    }

    @Test
    void handleRequest_withValidFields_returnsUpdatedResult() {
        //Given A Valid Request
        String oldActivityId = "5544J";
        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "Bouncy Place";
        String oldActivityExperience = "So Much FUNNNN!!";
        Boolean oldActivityEditStatus = false;
        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("Sky Park")
                .withUpdatedDescription("Indoor Trampoline Park")
                .withUpdatedPosterExperience("The whole family came and celebrated a Birthday!")
                .withUserId("J$44")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);
        EditActivityResult result = editActivity.handleRequest(request);
        //Then
        assertNotNull(result);
        ActivityModel model = result.getActivityModel();
        assertTrue(model.getEdited());
        assertEquals(oldActivity.getActivityId(), model.getActivityId());
        assertEquals(oldActivity.getUserId(), model.getUserId());
        assertNotEquals(oldActivityName, model.getActivityName());
        assertNotEquals(oldActivityDescription, model.getDescription());
        assertNotEquals(oldActivityExperience, model.getPosterExperience());
        assertNotEquals(oldActivityEditStatus, model.getEdited());
    }

    @Test
    void handleRequest_withEmptyActivityNameInRequest_throwsEmptyFieldInRequestException() {
        //Given A Valid Request
        String oldActivityId = "5544J";
        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "Bouncy Place";
        String oldActivityExperience = "So Much FUNNNN!!";
        Boolean oldActivityEditStatus = false;
        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("")
                .withUpdatedDescription("Indoor Trampoline Park")
                .withUpdatedPosterExperience("The whole family came and celebrated a Birthday!")
                .withUserId("J$44")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);
        //Then
        assertThrows(EmptyFieldInRequestException.class,
                () -> editActivity.handleRequest(request));
    }

    @Test
    void handleRequest_withEmptyDescriptionInRequest_throwsEmptyFieldInRequestException() {
        //Given A Valid Request
        String oldActivityId = "5544J";
        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "Bouncy Place";
        String oldActivityExperience = "So Much FUNNNN!!";
        Boolean oldActivityEditStatus = false;
        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("Sky Park")
                .withUpdatedDescription("")
                .withUpdatedPosterExperience("The whole family came and celebrated a Birthday!")
                .withUserId("J$44")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);
        //Then
        assertThrows(EmptyFieldInRequestException.class,
                () -> editActivity.handleRequest(request));
    }

    @Test
    void handleRequest_withEmptyDescriptionInRequestAndOldActivity_properlyUpdates() {
//Given A Valid Request
        String oldActivityId = "5544J";

        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "";
        String oldActivityExperience = "So Much FUNNNN!!";
        Boolean oldActivityEditStatus = false;
        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("Sky Park")
                .withUpdatedDescription("")
                .withUpdatedPosterExperience("The whole family came and celebrated a Birthday!")
                .withUserId("J$44")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);
        EditActivityResult result = editActivity.handleRequest(request);
        //Then
        assertNotNull(result);
        ActivityModel model = result.getActivityModel();
        assertTrue(model.getEdited());
        assertEquals(oldActivity.getActivityId(), model.getActivityId());
        assertEquals(oldActivity.getUserId(), model.getUserId());
        assertNotEquals(oldActivityName, model.getActivityName());
        assertEquals(oldActivityDescription, model.getDescription());
        assertNotEquals(oldActivityExperience, model.getPosterExperience());
        assertNotEquals(oldActivityEditStatus, model.getEdited());
    }

    @Test
    void handleRequest_withEmptyPosterExperienceInRequest_throwsEmptyFieldInRequestException() {
        //Given A Valid Request
        String oldActivityId = "5544J";
        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "Bouncy Place";
        String oldActivityExperience = "So Much FUNNNN!!";
        Boolean oldActivityEditStatus = false;
        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("Sky Park")
                .withUpdatedDescription("Indoor Trampoline Park")
                .withUpdatedPosterExperience("")
                .withUserId("J$44")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);
        //Then
        assertThrows(EmptyFieldInRequestException.class,
                () -> editActivity.handleRequest(request));
    }

    @Test
    void handleRequest_withEmptyPosterExperienceInRequestAndOldActivity_properlyUpdates() {
        //Given A Valid Request
        String oldActivityId = "5544J";
        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "Bouncy Place";
        String oldActivityExperience = "";
        Boolean oldActivityEditStatus = false;
        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("Sky Park")
                .withUpdatedDescription("Indoor Trampoline Park")
                .withUpdatedPosterExperience("")
                .withUserId("J$44")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);
        EditActivityResult result = editActivity.handleRequest(request);
        //Then
        assertNotNull(result);
        ActivityModel model = result.getActivityModel();
        assertTrue(model.getEdited());
        assertEquals(oldActivity.getActivityId(), model.getActivityId());
        assertEquals(oldActivity.getUserId(), model.getUserId());
        assertNotEquals(oldActivityName, model.getActivityName());
        assertNotEquals(oldActivityDescription, model.getDescription());
        assertEquals(oldActivityExperience, model.getPosterExperience());
        assertNotEquals(oldActivityEditStatus, model.getEdited());
    }

    @Test
    void handleRequest_withIncorrectUserId_throwsInvalidUserException() {
        //Given A Valid Request
        String oldActivityId = "5544J";
        String oldActivityName = "Trampoline Park";
        String oldActivityDescription = "Bouncy Place";
        String oldActivityExperience = "So Much FUNNNN!!";
        Boolean oldActivityEditStatus = false;

        Activity oldActivity = new Activity();
        oldActivity.setActivityId(oldActivityId);
        oldActivity.setActivityName(oldActivityName);
        oldActivity.setDescription(oldActivityDescription);
        oldActivity.setPosterExperience(oldActivityExperience);
        oldActivity.setUserId("J$44");
        oldActivity.setDatePosted(LocalDate.now());
        oldActivity.setEdited(oldActivityEditStatus);
        EditActivityRequest request = new EditActivityRequest.Builder()
                .withActivityId("5544J")
                .withUpdatedActivityName("Sky Park")
                .withUpdatedDescription("Indoor Trampoline Park")
                .withUpdatedPosterExperience("The whole family came and celebrated a Birthday!")
                .withUserId("J88Bills")
                .build();
        //When
        when(activitiesDao.getActivityById(anyString())).thenReturn(oldActivity);

        //Then
        assertThrows(InvalidUserException.class,
                () -> editActivity.handleRequest(request));
    }




}
