package com.nashss.se.tale.activity.requests;

public class GetCityRequest {
    private final String cityId;

    /**
     * Constructor for GetCityRequest.
     * @param cityId of City to retrieve.
     */
    private GetCityRequest(String cityId) {
        this.cityId = cityId;
    }
    public String getCityId() {
        return cityId;
    }

    @Override
    public String toString() {
        return "GetCityRequest{" +
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

        public GetCityRequest build() {
            return new GetCityRequest(cityId);
        }
    }
}
