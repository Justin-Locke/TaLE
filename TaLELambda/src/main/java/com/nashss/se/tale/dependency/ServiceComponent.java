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
    GetPersonalActivities provideGetPersonalActivities();
    CreateComment provideCreateComment();
    GetCommentsForActivity provideGetCommentsForActivity();
    GetComment provideGetComment();
    DeleteComment provideDeleteComment();
    EditComment provideEditComment();
}
