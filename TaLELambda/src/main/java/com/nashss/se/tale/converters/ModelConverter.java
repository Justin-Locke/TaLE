package com.nashss.se.tale.converters;
import com.nashss.se.tale.dynamodb.models.Activity;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.dynamodb.models.Comment;
import com.nashss.se.tale.models.ActivityModel;
import com.nashss.se.tale.models.CityModel;
import com.nashss.se.tale.models.CommentModel;

public class ModelConverter {

    /**
     * Method to convert City to CityModel.
     * @param city to be converted.
     * @return built CityModel.
     */
    public CityModel toCityModel(City city) {
        return CityModel.builder()
                .withCityId(city.getCityId())
                .withCityName(city.getCityName())
                .withActivityList(city.getActivityList())
                .build();
    }

    /**
     * Method to convert Activity to ActivityModel.
     * @param activity to convert.
     * @return Activity model.
     */
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

    /**
     * Method to use CommentModel Builder to build a CommentModel from a Comment.
     * @param comment to use in builder.
     * @return built CommentModel.
     */
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
