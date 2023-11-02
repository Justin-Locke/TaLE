
# TaLE Application Design

## 1. Problem Statement

TaLE is an application to provide travelers with information on activities in a given city.

## 2. Top Questions to Resolve in Review

1. How should the table between Activities and Cities be set up? Should States contain cities while cities contain activities?
2. Should users be able to delete an activity they posted or just their comments on the activity?

## 3. Use Cases
1. As a [user], I want to create(POST) a new activity in a city.
2.  As a [user], I want to view(GET) a list of activities in a city.
3. As a [user], I want to view(GET) a single activity.
4.  As a [user], I want to add(PUT) comments in an Activity.
5. As a [user], I want to delete(DEL) a personal comments on an Activity.
6.  As a [user], I want to view(GET) my posted Activities.
7. As a [user], I want to update(POST) my Activity (Website, Hours etc...).
8. As a [user], I want to view a list of my Comments.
9. As a [user], I want to view my profile.
10. As a [user], I want to view another user profile.

### 3.1 Stretch Use Cases:
1. Add[Admin] functionality.
2. As an [Admin], I want to be able to remove any comment.
3. As an [Admin] I want to be able to remove an Activity.
4. Ratings for Activities.
5. A place on homepage to show the top rated activities.
6. No duplicate Activities
7. Sort Activities by Popularity.
## 4. Project Scope

### 4.1 In Scope
- Creating, Retrieving, Updating, Deleting an Activity.
- Retrieving all Activities and Comments a user has posted.
- Viewing User Profiles?

### 4.2 Out of Scope
- The 3.1 Stretch Goals
- Not allow vulgar language
- Notifications of other users interacting with personally posted Activities.

## 5 Proposed Architecture Overview

I will use API Gateway and AWS Lambda to create the following endpoints:
- CreateActivity
- GetActivity
- GetActivities
- UpdateActivity
- AddActivityComment
- UpdateActivityComment
- RemoveActivityComment
- GetUserProfile
- UpdateUserProfile

Stretch Goal:

I will store Activity and Profile data in separate DynamoDB tables.
I will store Cities and Activity Data in separate tables.

## 6. API
### 6.1 Public Models
```
// ActivityModel

String activityId
String activityName
String website
List<Comments> comments

```

```
// CityModel

String cityId
String cityName
List<ActivityId> String

```

```
// UserModel

String userId
String userName
List<ActivityId>
List<Comments>

```

```
// CommentModel

String posterId
String posterUserName
String message

```

Optional Model:
```
// StateModel

Enum State
List<City>

```

### 6.2 Endpoints

#### 6.2.0 Create Activity Endpoint
- Accepts ```POST``` requests to ```
  /cityId/activities```
- Accepts data to create a new Activity with a provided UserId, Activity Name, and an Overview. Returns the new Activity, including an ActivityId assigned by the service.

#### 6.2.1 Get Single Activity Endpoint
- Accepts ```GET``` requests to ```/cityId/activities/:activityId```
- Take the ```activityId``` from the request
- Accepts an activityId and returns the corresponding ActivityModel.

#### 6.2.2 Get All Activities by Poster Endpoint
- Accepts ```GET``` requests to ```/profile/:```
- Take the userId from cognito.
- Accepts a userId and return the corresponding list of ActivityModels.

#### 6.2.3 Delete Activity Endpoint
- Accepts a ```DELETE``` request to ```/cityId/activities/:activityId```
- Take the userId from cognito
- Accepts an ActivityID and returns the corresponding Deleted ActivityModel.

#### 6.2.4 Update Activity Endpoint
- Accepts a ```PUT``` requests to ```/cityId/activities/:activityId```
- Take the userId from cognito
- Accepts data to update an Activity including an updated Overview, WebsiteAddress, etc... returns the updated Activity.
- Throws ```UnaughorizedUserException``` if attempted to be updated by an unauthorized User.

#### 6.2.5 Add Comment Endpoint
- Accepts ```POST``` request to ```/:cityId/activities/:activityId```
- Takes ```UserId``` from cognito
- Accepts data to add a comment on an Activity. Returns the Comment.

#### 6.2.6 Update Comment Endpoint
- Accepts ```PUT``` request to ```/:cityId/activities/:activityId```
- Takes ```UserId``` from cognito
- Takes commentId from comment?
- Accepts data to update comment. Returns updated Comment.
- Throws ```UnauthorizedUserException``` if attempted to be updated by an unauthorized user.

#### 6.2.7 Get User Profile Endpoint
- Accepts ```GET``` request to ```/profiles/:userId```
- Accepts a ```UserId``` and return the corresponding UserModel.

#### 6.2.8 Create User Profile Endpoint
- Accepts ```GET``` request to /profiles```
## 7.  Tables

### 7.1 `activities`
```
activityId // partition key, string
userId // String
commentIds // List of Strings of commentIds

```

### 7.2 `cities`
```
cityId // partition key, String
activityId // String
cityName // String
```

### 7.3 `comments`
```
commentId // partition key, String
message // String
userId // String
```

### 7.4 `ActivitiesByUser` GSI Table
```
userId // partition key, String
activityId // sort key, String
```

### 7.5 `CommentsByUser` GSI Table
```
userId // partition key, String
commentId // sort key, String
```

## 8. Page StoryBoard
https://jamboard.google.com/d/1prLky1BS2srtXpiuCZ2VfMakW7GIuqDa_St4iXmk0Hc/viewer?f=0