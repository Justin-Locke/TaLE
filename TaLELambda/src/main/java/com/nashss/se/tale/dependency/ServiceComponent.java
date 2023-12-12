package com.nashss.se.tale.dependency;

import com.nashss.se.tale.activity.CreateComment;
import com.nashss.se.tale.activity.CreateNewActivity;
import com.nashss.se.tale.activity.DeleteActivity;
import com.nashss.se.tale.activity.DeleteComment;
import com.nashss.se.tale.activity.EditActivity;
import com.nashss.se.tale.activity.EditComment;
import com.nashss.se.tale.activity.GetActivity;
import com.nashss.se.tale.activity.GetAllActivities;
import com.nashss.se.tale.activity.GetAllCities;
import com.nashss.se.tale.activity.GetCity;
import com.nashss.se.tale.activity.GetComment;
import com.nashss.se.tale.activity.GetCommentsForActivity;
import com.nashss.se.tale.activity.GetPersonalActivities;
import com.nashss.se.tale.activity.GetPersonalComments;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    /**
     * Method to instantiate Get City.
     * @return new GetCity().
     */
    GetCity provideGetCity();

    /**
     * Method to instantiate GetAllCities.
     * @return new GetAllCities().
     */
    GetAllCities provideGetAllCities();

    /**
     * Method to instantiate CreateNewActivity.
     * @return new CreateNewActivity().
     */
    CreateNewActivity provideCreateNewActivity();

    /**
     * Method to instantiate GetActivity.
     * @return new GetActivity().
     */
    GetActivity provideGetActivity();

    /**
     * Method to instantiate GetAllActivities.
     * @return new GetAllActivities().
     */
    GetAllActivities provideGetAllActivities();

    /**
     * Method to instantiate GetPersonalActivities.
     * @return new GetPersonalActivities().
     */
    GetPersonalActivities provideGetPersonalActivities();

    /**
     * Method to instantiate EditActivity.
     * @return new EditActivity().
     */
    EditActivity provideEditActivity();

    /**
     * Method to instantiate CreateComment.
     * @return new CreateComment().
     */
    CreateComment provideCreateComment();

    /**
     * Method to instantiate GetCommentsForActivity.
     * @return new GetCommentsForActivity(0.
     */
    GetCommentsForActivity provideGetCommentsForActivity();

    /**
     * Method to instantiate GetPersonalComments.
     * @return new GetPersonalComments().
     */
    GetPersonalComments provideGetPersonalComments();

    /**
     * Method to instantiate GetComment.
     * @return new GetComment().
     */
    GetComment provideGetComment();

    /**
     * Method to instantiate DeleteComment.
     * @return new DeleteComment().
     */
    DeleteComment provideDeleteComment();

    /**
     * Method to instantiate DeleteActivity.
     * @return new DeleteActivity().
     */
    DeleteActivity provideDeleteActivity();

    /**
     * Method to instantiate EditComment.
     * @return new EditComment().
     */
    EditComment provideEditComment();
}
