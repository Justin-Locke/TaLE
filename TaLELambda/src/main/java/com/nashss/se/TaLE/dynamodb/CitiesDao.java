package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.nashss.se.TaLE.dynamodb.models.City;
import com.nashss.se.TaLE.metrics.MetricsPublisher;

import javax.inject.Inject;
import java.util.List;

public class CitiesDao {
    private DynamoDBMapper dynamoDBMapper;
    private MetricsPublisher metricsPublisher;

    /**
     * Instantiates a CitiesDao object.
     * @param dynamoDBMapper used to interact with the cities table.
     * @param metricsPublisher used to publish metrics to CloudWatch.
     */
    @Inject
    public CitiesDao(DynamoDBMapper dynamoDBMapper, MetricsPublisher metricsPublisher) {
        this.dynamoDBMapper = dynamoDBMapper;
        this.metricsPublisher = metricsPublisher;
    }

    public City getCityById(String id) {
        City city = this.dynamoDBMapper.load(City.class, id);

        return city;
    }

    /**
     *
     * @param city
     * @return
     */
    public City saveCity(City city) {
        this.dynamoDBMapper.save(city);
        return city;
    }

    public List<City> getAllCities() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<City> cityList = dynamoDBMapper.scan(City.class, scanExpression);
        System.out.println("cityList = {}" + cityList);
        return cityList;
    }

}
