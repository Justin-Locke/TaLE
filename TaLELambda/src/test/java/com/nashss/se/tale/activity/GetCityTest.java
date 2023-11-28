package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetCityRequest;
import com.nashss.se.tale.activity.results.GetCityResult;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GetCityTest {

    @Mock
    CitiesDao citiesDao;
    GetCity getCity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getCity = new GetCity(citiesDao);
    }

    @Test
    void handleRequest_withValidRequest_returnsResult() {
        //Given
        GetCityRequest request = new GetCityRequest.Builder()
                .withCityId("1345")
                .build();
        List<String> activityIds = new ArrayList<>();
        activityIds.add("1");
        activityIds.add("2");
        activityIds.add("3");
        City city = new City();
        city.setCityName("Nashville");
        city.setCityId("NV21");
        city.setActivityList(activityIds);
        //When
        when(citiesDao.getCityById(anyString())).thenReturn(city);
        GetCityResult result = getCity.handleRequest(request);
        //Then
        assertNotNull(result);
        CityModel model = result.getCityModel();
        assertEquals(activityIds, model.getActivityList());

    }
}