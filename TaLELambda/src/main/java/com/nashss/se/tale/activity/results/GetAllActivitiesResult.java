package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.ActivityModel;

import java.util.List;

public class GetAllActivitiesResult {
    private final List<ActivityModel> activityModelList;

    /**
     * Constructor for GetAllActivitiesResult.
     * @param activityModelList to create Result.
     */
    public GetAllActivitiesResult(List<ActivityModel> activityModelList) {
        this.activityModelList = activityModelList;
    }

    public List<ActivityModel> getActivityModelList() {
        return activityModelList;
    }

    @Override
    public String toString() {
        return "GetAllActivitiesResult{" +
                "activityModelList=" + activityModelList +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<ActivityModel> activityModelList;

        public Builder withActivityModelList(List<ActivityModel> activityModelList) {
            this.activityModelList = activityModelList;
            return this;
        }

        public GetAllActivitiesResult build() {
            return new GetAllActivitiesResult(activityModelList);
        }
    }
}
