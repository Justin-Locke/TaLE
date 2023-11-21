package com.nashss.se.tale.dependency;

import com.nashss.se.tale.activity.*;
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
    DeleteComment provideDeleteComment();
}
