package com.nashss.se.TaLE.dynamodb;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.nashss.se.TaLE.dynamodb.models.City;
import com.nashss.se.TaLE.metrics.MetricsPublisher;

import javax.inject.Inject;

public class CitiesDao {
    private final DynamoDBMapper dynamoDBMapper;
    private final MetricsPublisher metricsPublisher;

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

        if (city == null) {

        }

        return city;
    }

    public City saveCity(City newCity) {
        this.dynamoDBMapper.save(newCity);
        return newCity;
    }

}
