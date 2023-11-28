package com.nashss.se.tale.activity.requests;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

@JsonDeserialize(builder = EditActivityRequest.Builder.class)
public class EditActivityRequest {
    private String activityId;
    private String userId;
    private String updatedActivityName;
    private String updatedDescription;
    private String updatedPosterExperience;

    public EditActivityRequest(String activityId, String userId,
                               String updatedActivityName, String updatedDescription,
                               String updatedPosterExperience) {
        this.activityId = activityId;
        this.userId = userId;
        this.updatedActivityName = updatedActivityName;
        this.updatedDescription = updatedDescription;
        this.updatedPosterExperience = updatedPosterExperience;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUpdatedActivityName() {
        return updatedActivityName;
    }

    public String getUpdatedDescription() {
        return updatedDescription;
    }

    public String getUpdatedPosterExperience() {
        return updatedPosterExperience;
    }

    @Override
    public String toString() {
        return "EditActivityRequest{" +
                "activityId='" + activityId + '\'' +
                ", userId='" + userId + '\'' +
                ", updatedActivityName='" + updatedActivityName + '\'' +
                ", updatedDescription='" + updatedDescription + '\'' +
                ", updatePosterExperience='" + updatedPosterExperience + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String activityId;
        private String userId;
        private String updatedActivityName;
        private String updatedDescription;
        private String updatedPosterExperience;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withUpdatedActivityName(String updatedActivityName) {
            this.updatedActivityName = updatedActivityName;
            return this;
        }

        public Builder withUpdatedDescription(String updatedDescription) {
            this.updatedDescription = updatedDescription;
            return this;
        }

        public Builder withUpdatedPosterExperience(String updatedPosterExperience) {
            this.updatedPosterExperience = updatedPosterExperience;
            return this;
        }

        public EditActivityRequest build() {
            return new EditActivityRequest(activityId, userId,
                    updatedActivityName, updatedDescription, updatedPosterExperience);
        }
    }
}
