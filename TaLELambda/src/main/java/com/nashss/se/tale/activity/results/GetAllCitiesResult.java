package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.CityModel;

import java.util.ArrayList;
import java.util.List;

public class GetAllCitiesResult {
    private final List<CityModel> cityModelList;

    /**
     * Constructor for GetAllCitiesResult.
     * @param cityModelList list of City Models to create Result.
     */
    public GetAllCitiesResult(List<CityModel> cityModelList) {
        this.cityModelList = cityModelList;
    }

    public List<CityModel> getCityModelList() {
        return cityModelList;
    }

    @Override
    public String toString() {
        return "GetAllCitiesResult{" +
                "cityModelList=" + cityModelList +
                '}';
    }
    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<CityModel> cityModelList;

        public Builder withCityModelList(List<CityModel> cityModelList) {
            this.cityModelList = new ArrayList<>(cityModelList);
            return this;
        }
        public GetAllCitiesResult build() {
            return new GetAllCitiesResult(cityModelList);
        }
    }
}
