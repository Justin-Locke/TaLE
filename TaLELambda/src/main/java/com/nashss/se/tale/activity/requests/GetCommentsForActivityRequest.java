package com.nashss.se.tale.activity.requests;

public class GetCommentsForActivityRequest {
    private String activityId;

    /**
     * Constructor for GetCommentsForActivityRequest.
     * @param activityId to create Request.
     */
    private GetCommentsForActivityRequest(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    @Override
    public String toString() {
        return "GetCommentsForActivityRequest{" +
                "activityId='" + activityId + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String activityId;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public GetCommentsForActivityRequest build() {
            return new GetCommentsForActivityRequest(activityId);
        }
    }
}
