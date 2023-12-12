package com.nashss.se.tale.activity.requests;

public class GetCommentRequest {
    private String activityId;
    private String commentId;
    private String userId;

    /**
     * Constructor for GetCommentRequest.
     * @param activityId for Hash Key.
     * @param commentId for Range Key.
     * @param userId to determine user.
     */
    private GetCommentRequest(String activityId, String commentId, String userId) {
        this.activityId = activityId;
        this.commentId = commentId;
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetCommentRequest{" +
                "activityId='" + activityId + '\'' +
                ", commentId='" + commentId + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String activityId;
        private String commentId;
        private String userId;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetCommentRequest build() {
            return new GetCommentRequest(activityId, commentId, userId);
        }
    }
}
