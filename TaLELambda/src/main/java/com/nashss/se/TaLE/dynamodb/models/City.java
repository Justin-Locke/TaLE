package com.nashss.se.TaLE.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "cities")
public class City {
    private String cityId;
    private List<String> activityId;
    private String cityName;

    @DynamoDBHashKey(attributeName = "cityId")
    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    @DynamoDBAttribute(attributeName = "activityIdList")
    public List<String> getActivityId() {
        return activityId;
    }

    public void setActivityId(List<String> activityId) {
        this.activityId = activityId;
    }

    @DynamoDBAttribute(attributeName = "cityName")
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City city)) return false;
        return Objects.equals(cityId, city.cityId) &&
                Objects.equals(activityId, city.activityId) &&
                Objects.equals(cityName, city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, activityId, cityName);
    }
}
