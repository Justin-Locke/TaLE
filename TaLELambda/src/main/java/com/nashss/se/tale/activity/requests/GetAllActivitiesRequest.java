package com.nashss.se.tale.activity.requests;

import java.util.List;

public class GetAllActivitiesRequest {
    private List<String> activityIdList;

    /**
     * Constructor for GetAllActivitiesRequest.
     * @param activityIdList List of activityIds.
     */
    public GetAllActivitiesRequest(List<String> activityIdList) {
        this.activityIdList = activityIdList;
    }

    public List<String> getActivityIdList() {
        return activityIdList;
    }

    @Override
    public String toString() {
        return "GetAllActivitiesRequest{" +
                "activityIdList=" + activityIdList +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<String> activityIdList;

        public Builder withActivityIdList(List<String> activityIdList) {
            this.activityIdList = activityIdList;
            return this;
        }

        public GetAllActivitiesRequest build() {
            return new GetAllActivitiesRequest(activityIdList);
        }
    }
}
