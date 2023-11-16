package com.nashss.se.TaLE.activity.results;

import com.nashss.se.TaLE.models.ActivityModel;

public class CreateNewActivityResult {
    private final ActivityModel activityModel;

    private CreateNewActivityResult(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    public String toString() {
        return "CreateNewActivityResult{" +
                "activityModel=" + activityModel +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ActivityModel activityModel;

        public Builder withActivityModel(ActivityModel activityModel) {
            this.activityModel = activityModel;
            return this;
        }

        public CreateNewActivityResult build() {
            return new CreateNewActivityResult(activityModel);
        }
    }
}
