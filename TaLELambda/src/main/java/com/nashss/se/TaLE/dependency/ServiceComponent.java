package com.nashss.se.TaLE.dependency;

import com.nashss.se.TaLE.activity.GetAllCities;
import com.nashss.se.TaLE.activity.GetCity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    GetCity provideGetCity();
    GetAllCities provideGetAllCities();
}
