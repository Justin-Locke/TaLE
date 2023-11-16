package com.nashss.se.TaLE.activity.results;

import com.nashss.se.TaLE.activity.requests.GetActivityRequest;
import com.nashss.se.TaLE.models.ActivityModel;

public class GetActivityResult {
    private ActivityModel activityModel;
    private GetActivityResult(ActivityModel activityModel) {
        this.activityModel = activityModel;
    }

    public ActivityModel getActivityModel() {
        return activityModel;
    }

    @Override
    public String toString() {
        return "GetActivityResult{" +
                "activityModel=" + activityModel +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ActivityModel activityModel;

        public Builder withActivityModel(ActivityModel activityModel) {
            this.activityModel = activityModel;
            return this;
        }

        public GetActivityResult build() {
            return new GetActivityResult(activityModel);
        }
    }
}
