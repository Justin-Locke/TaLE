package com.nashss.se.tale.converters;

import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.ActivityModel;
import com.nashss.se.tale.models.CityModel;
import com.nashss.se.tale.models.CommentModel;

public class ModelConverter {

    public CityModel toCityModel(City city) {
        return CityModel.builder()
                .withCityId(city.getCityId())
                .withCityName(city.getCityName())
                .withActivityList(city.getActivityList())
                .build();
    }

    public ActivityModel toActivityModel(Activity activity) {
        return ActivityModel.builder()
                .withActivityId(activity.getActivityId())
                .withUserId(activity.getUserId())
                .withActivityName(activity.getActivityName())
                .withDescription(activity.getDescription())
                .withDatePosted(activity.getDatePosted())
                .withEdited(activity.getEdited())
                .withPosterExperience(activity.getPosterExperience())
                .build();
    }

    public CommentModel toCommentModel(Comment comment) {
        return CommentModel.builder()
                .withActivityId(comment.getActivityId())
                .withCommentId(comment.getCommentId())
                .withDatePosted(comment.getDatePosted())
                .withEdited(comment.getEdited())
                .withMessage(comment.getMessage())
                .withTitle(comment.getTitle())
                .withUserId(comment.getUserId())
                .build();
    }
}