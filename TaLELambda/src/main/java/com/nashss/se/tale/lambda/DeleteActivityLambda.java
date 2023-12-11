package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.DeleteActivityRequest;
import com.nashss.se.tale.activity.results.DeleteActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;


public class DeleteActivityLambda
    extends LambdaActivityRunner<DeleteActivityRequest, DeleteActivityResult>
    implements RequestHandler<AuthenticatedLambdaRequest<DeleteActivityRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteActivityRequest> input, Context context) {
        return super.runActivity(
            () -> {
                DeleteActivityRequest unauthenticatedRequest = input.fromPath(path ->
                            DeleteActivityRequest.builder()
                                    .withActivityId(path.get("activityId"))
                                    .build());
                return input.fromUserClaims(claims ->
                            DeleteActivityRequest.builder()
                                    .withActivityId(unauthenticatedRequest.getActivityId())
                                    .withUserId(claims.get("email"))
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideDeleteActivity().handleRequest(request)
        );
    }
}
