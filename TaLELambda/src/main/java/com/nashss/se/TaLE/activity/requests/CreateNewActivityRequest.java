package com.nashss.se.TaLE.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.time.LocalDate;
@JsonDeserialize(builder = CreateNewActivityRequest.Builder.class)
public class CreateNewActivityRequest {

    private String cityId;
    private String userId;
    private String activityName;
    private String description;
    private String posterExperience;

    public CreateNewActivityRequest(String cityId, String userId, String activityName, String description, String posterExperience) {
        this.cityId = cityId;
        this.userId = userId;
        this.activityName = activityName;
        this.description = description;
        this.posterExperience = posterExperience;
    }

    public String getCityId() {
        return cityId;
    }

    public String getUserId() {
        return userId;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterExperience() {
        return posterExperience;
    }

    @Override
    public String toString() {
        return "CreateNewActivityRequest{" +
                "userId='" + userId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", description='" + description + '\'' +
                ", posterExperience='" + posterExperience + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String cityId;
        private String userId;
        private String activityName;
        private String description;
        private String posterExperience;

        public Builder withCityId(String cityId) {
            this.cityId = cityId;
            return this;
        }
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withActivityName(String activityName) {
            this.activityName = activityName;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withPosterExperience(String posterExperience) {
            this.posterExperience = posterExperience;
            return this;
        }

        public CreateNewActivityRequest build() {
            return new CreateNewActivityRequest(cityId, userId, activityName, description, posterExperience);
        }
    }
}
