package com.nashss.se.tale.activity.requests;

public class DeleteActivityRequest {
    private final String activityId;
    private final String userId;

    /**
     * Constructor for DeleteActivityRequest.
     * @param activityId to find the activity to delete.
     * @param userId to verify user.
     */
    private DeleteActivityRequest(String activityId, String userId) {
        this.activityId = activityId;
        this.userId = userId;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getUserId() {
        return userId;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String activityId;
        private String userId;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }


        public DeleteActivityRequest build() {
            return new DeleteActivityRequest(activityId, userId);
        }
    }
}
