package com.nashss.se.TaLE.activity.results;

import com.nashss.se.TaLE.models.CityModel;

public class GetCityResult {
    private final CityModel city;
    private GetCityResult(CityModel city) {
        this.city = city;
    }

    public CityModel getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "GetCityResult{" +
                "city=" + city +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private CityModel city;
        public Builder withCityModel(CityModel city) {
            this.city = city;
            return this;
        }

        public GetCityResult build() {
            return new GetCityResult(city);
        }
    }
}
