package com.nashss.se.tale.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.tale.activity.requests.GetPersonalActivitiesRequest;
import com.nashss.se.tale.activity.results.GetPersonalActivitiesResult;

public class GetPersonalActivitiesLambda
    extends LambdaActivityRunner<GetPersonalActivitiesRequest, GetPersonalActivitiesResult>
    implements RequestHandler<AuthenticatedLambdaRequest<GetPersonalActivitiesRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetPersonalActivitiesRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    return input.fromUserClaims(claims ->
                            GetPersonalActivitiesRequest.builder()
                                    .withUserId(claims.get("email"))
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideGetPersonalActivities().handleRequest(request)
        );
    }
}