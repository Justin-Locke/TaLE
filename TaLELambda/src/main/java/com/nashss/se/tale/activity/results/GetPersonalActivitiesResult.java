package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.ActivityModel;

import java.util.List;

public class GetPersonalActivitiesResult {
    private final List<ActivityModel> activityModelList;

    /**
     * Constructor for GetPersonalActivitiesResult.
     * @param activityModelList list of ActivityModels to create Result.
     */
    public GetPersonalActivitiesResult(List<ActivityModel> activityModelList) {
        this.activityModelList = activityModelList;
    }

    public List<ActivityModel> getActivityModelList() {
        return activityModelList;
    }

    @Override
    public String toString() {
        return "GetPersonalActivitiesResult{" +
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

        public GetPersonalActivitiesResult build() {
            return new GetPersonalActivitiesResult(activityModelList);
        }
    }
}
