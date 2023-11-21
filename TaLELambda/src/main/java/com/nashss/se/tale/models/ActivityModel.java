package com.nashss.se.tale.models;

import java.time.LocalDate;
import java.util.Objects;

public class ActivityModel {
    private String activityId;
    private String userId;
    private String activityName;
    private String description;
    private LocalDate datePosted;
    private Boolean edited;
    private String posterExperience;

    public ActivityModel(String activityId, String userId,
                         String activityName, String description,
                         LocalDate datePosted, Boolean edited,
                         String posterExperience) {
        this.activityId = activityId;
        this.userId = userId;
        this.activityName = activityName;
        this.description = description;
        this.datePosted = datePosted;
        this.edited = edited;
        this.posterExperience = posterExperience;
    }

    public String getActivityId() {
        return activityId;
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

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public Boolean getEdited() {
        return edited;
    }

    public String getPosterExperience() {
        return posterExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ActivityModel that = (ActivityModel) o;
        return Objects.equals(activityId, that.activityId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(activityName, that.activityName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(datePosted, that.datePosted) &&
                Objects.equals(edited, that.edited) &&
                Objects.equals(posterExperience, that.posterExperience);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, userId, activityName,
                description, datePosted, edited,
                posterExperience);
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String activityId;
        private String userId;
        private String activityName;
        private String description;
        private LocalDate datePosted;
        private Boolean edited;
        private String posterExperience;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
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

        public Builder withDatePosted(LocalDate datePosted) {
            this.datePosted = datePosted;
            return this;
        }

        public Builder withEdited(Boolean edited) {
            this.edited = edited;
            return this;
        }

        public Builder withPosterExperience(String posterExperience) {
            this.posterExperience = posterExperience;
            return this;
        }

        public ActivityModel build() {
            return new ActivityModel(activityId, userId,
                    activityName, description, datePosted,
                    edited, posterExperience);
        }

    }
}
