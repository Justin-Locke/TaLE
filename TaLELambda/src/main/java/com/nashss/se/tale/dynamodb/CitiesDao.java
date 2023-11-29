package com.nashss.se.tale.dynamodb;
import com.nashss.se.tale.dynamodb.models.City;
import com.nashss.se.tale.metrics.MetricsPublisher;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;

import java.util.List;
import javax.inject.Inject;


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

    /**
     * Method to get single city.
     * @param id of City.
     * @return single City.
     */
    public City getCityById(String id) {
        City city = this.dynamoDBMapper.load(City.class, id);

        return city;
    }

    /**
     * Method to save City to DDB.
     * @param city to save.
     * @return saved City.
     */
    public City saveCity(City city) {
        this.dynamoDBMapper.save(city);
        return city;
    }

    /**
     * Method to return all cities from cities table.
     * @return List of City.
     */
    public List<City> getAllCities() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<City> cityList = dynamoDBMapper.scan(City.class, scanExpression);
        System.out.println("cityList = {}" + cityList);
        return cityList;
    }

}
