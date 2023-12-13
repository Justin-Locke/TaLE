package com.nashss.se.tale.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.metrics.MetricsConstants;
import com.nashss.se.tale.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class CitiesDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher metricsPublisher;
    @Mock
    PaginatedQueryList<City> paginatedQueryList;
    @Mock
    PaginatedScanList<City> paginatedScanList;
    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<City>> queryCaptor;

    @Captor
    ArgumentCaptor<DynamoDBScanExpression> scanCaptor;
    CitiesDao citiesDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        citiesDao = new CitiesDao(mapper, metricsPublisher);
        when(mapper.query(eq(City.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);

        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
        when(mapper.scan(eq(City.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);
        when(paginatedScanList.toArray()).thenReturn(new Object[0]);
    }

    @Test
    void getCityById_withValidID_returnsExpectedCity() {
        //Given a city
        String cityId = "A37207";
        List<String> activityList = new ArrayList<>();
        activityList.add("ACT14590");
        activityList.add("ACT89303");

        City city = new City();
        city.setCityName("Nashville");
        city.setCityId(cityId);
        city.setActivityList(activityList);

        //When
        when(mapper.load(City.class, cityId)).thenReturn(city);
        City result = citiesDao.getCityById(cityId);

        //Then
        assertNotNull(result);
        System.out.println(city);
        assertEquals(city, result);
        verify(mapper, times(1)).load(any(), anyString());
        verify(metricsPublisher, times(1)).addCount(MetricsConstants.CITY_NULL_COUNT, 0);
    }

    @Test
    void getCityById_withInvalidId_returnsNull() {
        //Given
        String cityId = "e2e";
        //When
        when(mapper.load(City.class, cityId)).thenReturn(null);
        City result = citiesDao.getCityById(cityId);
        //Then
        assertEquals(null, result);
        verify(metricsPublisher, times(1)).addCount(MetricsConstants.CITY_NULL_COUNT, 1);
    }

    @Test
    void getAllCities_scanTable_returnsAllTableItems() {
        //Given
        //When
        when(mapper.scan(eq(City.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanList);
        List<City> results = citiesDao.getAllCities();

        //Then
        assertNotNull(results);
        assertEquals(paginatedScanList, results);
        verify(mapper, times(1)).scan(eq(City.class), scanCaptor.capture());
        verify(metricsPublisher, times(1)).addMetric(anyString(), anyDouble(), any());
    }
}
