package com.nashss.se.TaLE.activity.requests;

public class GetActivityRequest {
    private String activityId;

    public GetActivityRequest(String activityId) {
        this.activityId = activityId;
    }

    public String getActivityId() {
        return activityId;
    }

    @Override
    public String toString() {
        return "GetActivityRequest{" +
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

        public GetActivityRequest build() {
            return new GetActivityRequest(activityId);
        }
    }
}
