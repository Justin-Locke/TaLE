package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetAllCitiesRequest;
import com.nashss.se.tale.activity.results.GetAllCitiesResult;
import com.nashss.se.tale.dynamodb.CitiesDao;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.models.CityModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class GetAllCitiesTest {
    @Mock
    CitiesDao citiesDao;
    GetAllCities getAllCities;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getAllCities = new GetAllCities(citiesDao);
    }
    @Test
    void handleRequest_forAllCities_returnsNotNull() {
        //Given
        List<String> activityIds = new ArrayList<>();
        activityIds.add("1");
        activityIds.add("2");
        activityIds.add("3");
        City city = new City();
        city.setCityName("Nashville");
        city.setCityId("N459V");
        city.setActivityList(activityIds);
        GetAllCitiesRequest request = new GetAllCitiesRequest.Builder().build();
        //When
        when(citiesDao.getAllCities()).thenReturn(List.of(city));
        GetAllCitiesResult result = getAllCities.handleRequest(request);
        //Then
        assertNotNull(result);
        List<CityModel> modelList = result.getCityModelList();
        assertEquals(1, modelList.size());
        assertEquals(city.getCityId(), modelList.get(0).getCityId());
        assertEquals(city.getCityName(), modelList.get(0).getCityName());
        assertEquals(city.getActivityList(), modelList.get(0).getActivityList());

    }
}