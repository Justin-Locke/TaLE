package com.nashss.se.tale.activity.requests;

import java.util.List;

public class GetAllActivitiesRequest {
    private String cityId;

    /**
     * Constructor for GetAllActivitiesRequest.
     * @param cityId to get all activities from.
     */
    public GetAllActivitiesRequest(String cityId) {
        this.cityId = cityId;
    }

    public String getCityId() {
        return cityId;
    }

    @Override
    public String toString() {
        return "GetAllActivitiesRequest{" +
                "cityId='" + cityId + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String cityId;

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public GetAllActivitiesRequest build() {
            return new GetAllActivitiesRequest(cityId);
        }
    }
}
