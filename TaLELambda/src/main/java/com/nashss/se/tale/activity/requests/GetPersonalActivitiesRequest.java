package com.nashss.se.tale.activity.requests;

public class GetPersonalActivitiesRequest {
    private final String userId;

    /**
     * Constructor for GetPersonalActivitiesRequest.
     * @param userId to find users posted Activities.
     */
    private GetPersonalActivitiesRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetPersonalActivitiesRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String userId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetPersonalActivitiesRequest build() {
            return new GetPersonalActivitiesRequest(userId);
        }
    }
}
