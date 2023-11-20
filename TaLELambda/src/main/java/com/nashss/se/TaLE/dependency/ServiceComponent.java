package com.nashss.se.TaLE.dependency;

import com.nashss.se.TaLE.activity.*;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    GetCity provideGetCity();
    GetAllCities provideGetAllCities();
    CreateNewActivity provideCreateNewActivity();
    GetActivity provideGetActivity();
    GetAllActivities provideGetAllActivities();
    CreateComment provideCreateComment();
    GetCommentsForActivity provideGetCommentsForActivity();
}
