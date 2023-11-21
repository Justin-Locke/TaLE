package com.nashss.se.tale.activity.requests;

public class DeleteCommentRequest {
    private final String commentId;
    private final String userId;
    private final String activityId;

    public DeleteCommentRequest(String commentId, String userId, String activityId) {
        this.commentId = commentId;
        this.userId = userId;
        this.activityId = activityId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String commentId;
        private String userId;
        private String activityId;

        public Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public DeleteCommentRequest build() {
            return new DeleteCommentRequest(commentId, userId, activityId);
        }
    }
}
