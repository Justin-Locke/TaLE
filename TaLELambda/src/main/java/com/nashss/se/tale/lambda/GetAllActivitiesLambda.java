package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.GetAllActivitiesRequest;
import com.nashss.se.tale.activity.results.GetAllActivitiesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAllActivitiesLambda
        extends LambdaActivityRunner<GetAllActivitiesRequest, GetAllActivitiesResult>
        implements RequestHandler<LambdaRequest<GetAllActivitiesRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAllActivitiesRequest> input, Context context) {
        return super.runActivity(() -> {
                GetAllActivitiesRequest getAllActivitiesRequest = input.fromBody(GetAllActivitiesRequest.class);
            return GetAllActivitiesRequest.builder()
                .withActivityIdList(getAllActivitiesRequest.getActivityIdList())
                .build();
        },
            (request, serviceComponent) ->
                    serviceComponent.provideGetAllActivities().handleRequest(request)
        );
    }
}
