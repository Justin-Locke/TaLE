package com.nashss.se.tale.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = EditCommentRequest.Builder.class)
public class EditCommentRequest {
    private String activityId;
    private String title;
    private String message;
    private String userId;
    private String commentId;

    /**
     * Constructor for EditCommentRequest.
     * @param activityId for Hash Key.
     * @param title Updated (or Not).
     * @param message Updated (or Not).
     * @param userId Verify User.
     * @param commentId for Range Key.
     */
    private EditCommentRequest(String activityId, String title, String message, String userId, String commentId) {
        this.activityId = activityId;
        this.title = title;
        this.message = message;
        this.userId = userId;
        this.commentId = commentId;
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

    public String getCommentId() {
        return commentId;
    }

    @Override
    public String toString() {
        return "EditCommentRequest{" +
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
        private String commentId;

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

        public Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public EditCommentRequest build() {
            return new EditCommentRequest(activityId, title, message, userId, commentId);
        }
    }
}
