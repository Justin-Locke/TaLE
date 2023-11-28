package com.nashss.se.tale.activity;

import com.nashss.se.tale.activity.requests.GetAllCitiesRequest;
import com.nashss.se.tale.activity.results.GetAllCitiesResult;
import com.nashss.se.tale.dynamodb.CitiesDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

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
        GetAllCitiesRequest request = new GetAllCitiesRequest.Builder().build();
        //When
        GetAllCitiesResult result = getAllCities.handleRequest(request);
        //Then
        assertNotNull(result);
    }
}