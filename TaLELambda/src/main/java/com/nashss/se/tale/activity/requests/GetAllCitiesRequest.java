package com.nashss.se.tale.activity.requests;

public class GetAllCitiesRequest {
    /**
     * Constructor for GetAllCitiesRequest.
     */
    private GetAllCitiesRequest() {

    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public GetAllCitiesRequest build() {
            return new GetAllCitiesRequest();
        }
    }
}
