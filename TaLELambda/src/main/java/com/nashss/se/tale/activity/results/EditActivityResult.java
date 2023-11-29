package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.ActivityModel;

public class EditActivityResult {
    private final ActivityModel activityModel;

    /**
     * Constructor for EditActivityResult.
     * @param activityModel to create Result.
     */
    public EditActivityResult(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    public String toString() {
        return "EditActivityResult{" +
                "activityModel=" + activityModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ActivityModel activityModel;

        public Builder withActivityModel(ActivityModel activityModel) {
            this.activityModel = activityModel;
            return this;
        }

        public EditActivityResult build() {
            return new EditActivityResult(activityModel);
        }
    }
}
