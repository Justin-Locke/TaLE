package com.nashss.se.TaLE.dependency;

import com.nashss.se.TaLE.activity.CreateNewActivity;
import com.nashss.se.TaLE.activity.GetActivity;
import com.nashss.se.TaLE.activity.GetAllCities;
import com.nashss.se.TaLE.activity.GetCity;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    GetCity provideGetCity();
    GetAllCities provideGetAllCities();
    CreateNewActivity provideCreateNewActivity();
    GetActivity provideGetActivity();
}
