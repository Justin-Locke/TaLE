package com.nashss.se.TaLE.activity.requests;

public class GetCityRequest {
    private final String cityId;
    public GetCityRequest(String cityId) {
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

    //Builder
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
