
# TaLE Application Design

## 1. Problem Statement

TaLE, or "Travel a Local Experience," is an innovative app that empowers users to share and discover unique activities in various cities. 
With TaLE, individuals can post their favorite experiences, providing others with a glimpse of the fun and exciting things to do in a particular location.
Additionally, the app fosters interaction and engagement by allowing users to leave comments on shared activities, making it a dynamic platform for exploring and connecting with local experiences.

## 2. Top Questions to Resolve in Review

1. How should the table between Activities and Cities be set up? Should States contain cities while cities contain activities?
2. Should users be able to delete an activity they posted or just their comments on the activity?

## 3. Use Cases
1. As a [user], I want to create a new activity in a city.
2. As a [user], I want to view a list of activities in a city.
3. As a [user], I want to view a single activity.
4. As a [user], I want to add comments to an Activity.
5. As a [user], I want to delete a personal comment on an Activity.
6. As a [user], I want to view a single comment.
7. As a [user], I want to edit a personal comment.
8. As a [user], I want to view my posted Activities.
9. As a [user], I want to update my Activity (Website, Description).
10. As a [user], I want to view a list of my Comments.

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
- GetAllUserActivities
- UpdateActivity
- DeleteActivity
- AddActivityComment
- GetActivityComment
- GetAllUserComments
- UpdateActivityComment
- DeleteActivityComment

Stretch Goal:

I will store Activity and Comment data in separate DynamoDB tables.
I will store Cities and Activity Data in separate tables.

## 6. API
### 6.1 Public Models
```
// ActivityModel

String activityId
String userId
String activityName
String description
String website
String Location
LocalDate postedDate

List<Comments> comments

```

```
// CityModel

String cityId
String cityName
List<ActivityId> String

```

```
// CommentModel

String userId
String commentId
String title
String message
LocalDate datePosted

```

Optional Model:
```
// StateModel

Enum State
List<CityIds>

```

### 6.2 Endpoints

#### 6.2.0 Create Activity Endpoint
- Accepts ```POST``` requests to ```
  /cities/{cityId}/activities```
- UserId from Cognito.
- Accepts data to create a new Activity with a provided UserId, Activity Name, and an Overview. 
- Returns the new Activity, including an ActivityId assigned by the service.

#### 6.2.1 Get Single Activity Endpoint
- Accepts ```GET``` requests to ```/activities/{activityId}```
- Accepts an activityId and returns the corresponding ActivityModel.

#### 6.2.2 Get All Activities by Poster Endpoint
- Accepts ```GET``` requests to ```/activites```
- Take the userId from cognito.
- Accepts a userId and return the corresponding list of ActivityModels.

#### 6.2.3 Delete Activity Endpoint
- Accepts a ```DELETE``` request to ```/activities/{activityId}```
- Take the userId from cognito
- Accepts an ActivityID and returns the corresponding Deleted ActivityModel.

#### 6.2.4 Update Activity Endpoint
- Accepts a ```PUT``` requests to ```/activities/{activityId}```
- Take the userId from cognito
- Accepts data to update an Activity including an updated Overview, WebsiteAddress, etc... returns the updated Activity.
- Throws ```UnaughorizedUserException``` if attempted to be updated by an unauthorized User.

#### 6.2.5 Add Comment Endpoint
- Accepts ```POST``` request to ```/activities/{activityId}/comments```
- Takes ```UserId``` from cognito
- Accepts data to add a comment on an Activity. Returns the Comment.

#### 6.2.6 Update Comment Endpoint
- Accepts ```PUT``` request to ```/comments/{commentId}```
- Takes ```UserId``` from cognito
- Takes commentId.
- Accepts data to update comment. Returns updated Comment.
- Throws ```UnauthorizedUserException``` if attempted to be updated by an unauthorized user.

#### 6.2.7 Get All Poster Comments Endpoint
- Accepts ```GET``` request to ```/comments```
- Takes userId from Cognito.
- Returns list of Comments associated with User.

## 7.  Tables

### 7.1 `activities`
```
activityId // partition key, string
userId // String
commentIds // List of Strings of commentIds
Description // String
Website // String
Location // String
Date Posted // LocalDate
Poster Experience // String


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
title // String
message // String
userId // String
DatePosted // LocalDate
```

### 7.4 `ActivitiesByUser` GSI Table
```
userId // partition key, String
activityId // sort key, String
activityName // String
```

### 7.5 `CommentsByUser` GSI Table
```
userId // partition key, String
commentId // sort key, String
title // String
message // String
edited // Boolean
```

## 8. Page StoryBoard
https://jamboard.google.com/d/1prLky1BS2srtXpiuCZ2VfMakW7GIuqDa_St4iXmk0Hc/viewer?f=0