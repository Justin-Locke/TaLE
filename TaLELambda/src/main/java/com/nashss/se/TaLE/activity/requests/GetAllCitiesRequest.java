package com.nashss.se.TaLE.activity.requests;

public class GetAllCitiesRequest {
    public GetAllCitiesRequest() {

    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        public GetAllCitiesRequest build() {
            return new GetAllCitiesRequest();
        }
    }
}
