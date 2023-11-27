package com.nashss.se.tale.activity.requests;

public class GetPersonalCommentsRequest {
    private final String userId;

    public GetPersonalCommentsRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetPersonalCommentsRequest{" +
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

        public GetPersonalCommentsRequest build() {
            return new GetPersonalCommentsRequest(userId);
        }
    }
}
