package com.nashss.se.tale.models;

import java.util.List;
import java.util.Objects;

public class CityModel {
    private final String cityId;
    private final String cityName;
    private final List<String> activityList;

    /**
     * Constructor for CityModel.
     * @param cityId Id of the City.
     * @param cityName name of the City.
     * @param activityList List of activity Id's associated with city.
     */
    private CityModel(String cityId, String cityName, List<String> activityList) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.activityList = activityList;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public List<String> getActivityList() {
        return activityList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CityModel cityModel = (CityModel) o;
        return Objects.equals(cityId, cityModel.cityId) &&
                Objects.equals(cityName, cityModel.cityName) &&
                Objects.equals(activityList, cityModel.activityList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, cityName, activityList);
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String cityId;
        private String cityName;
        private List<String> activityList;

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }

        public Builder withCityName(String cityName) {
            this.cityName = cityName;
            return this;
        }

        public Builder withActivityList(List<String> activityList) {
            this.activityList = activityList;
            return this;
        }

        public CityModel build() {
            return new CityModel(cityId, cityName, activityList);
        }
    }
}
