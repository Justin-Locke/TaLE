package com.nashss.se.tale.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
@JsonDeserialize(builder = CreateCommentRequest.Builder.class)
public class CreateCommentRequest {
    private String activityId;
    private String title;
    private String message;
    private String userId;

    /**
     * Constructor for CreateCommentRequest.
     * @param activityId associated with new Comment.
     * @param title for new Comment.
     * @param message for new Comment.
     * @param userId to associate comment with User.
     */
    private CreateCommentRequest(String activityId, String title, String message, String userId) {
        this.activityId = activityId;
        this.title = title;
        this.message = message;
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "CreateCommentRequest{" +
                "activityId='" + activityId + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String activityId;
        private String title;
        private String message;
        private String userId;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateCommentRequest build() {
            return new CreateCommentRequest(activityId, title, message, userId);
        }
    }
}
