package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.nashss.se.TaLE.dynamodb.models.City;
import com.nashss.se.TaLE.metrics.MetricsPublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class CitiesDaoTest {
    @Mock
    DynamoDBMapper mapper;
    @Mock
    MetricsPublisher metricsPublisher;
    @Mock
    PaginatedQueryList<City> paginatedQueryList;
    @Captor
    ArgumentCaptor<DynamoDBQueryExpression<City>> queryCaptor;

    CitiesDao citiesDao;

    @BeforeEach
    public void setup() {
        initMocks(this);
        citiesDao = new CitiesDao(mapper, metricsPublisher);
//        when(mapper.query(eq(City.class), any(DynamoDBQueryExpression.class))).thenReturn(paginatedQueryList);
//
//        when(paginatedQueryList.toArray()).thenReturn(new Object[0]);
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
        city.setActivityId(activityList);

        //When
        when(mapper.load(City.class, cityId)).thenReturn(city);
        City result = citiesDao.getCityById(cityId);

        //Then
        assertNotNull(result);
        System.out.println(city);
        assertEquals(city, result);
        verify(mapper).load(City.class, cityId);
    }
}
