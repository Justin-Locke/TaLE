package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.EditActivityRequest;
import com.nashss.se.tale.activity.results.EditActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EditActivityLambda
    extends LambdaActivityRunner<EditActivityRequest, EditActivityResult>
    implements RequestHandler<AuthenticatedLambdaRequest<EditActivityRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditActivityRequest> input, Context context) {
        return super.runActivity(
            () -> {
                EditActivityRequest pathRequest = input.fromPath(path ->
                        EditActivityRequest.builder()
                                .withActivityId(path.get("activityId"))
                                .build());
                EditActivityRequest bodyRequest = input.fromBody(EditActivityRequest.class);
                return input.fromUserClaims(claims ->
                        EditActivityRequest.builder()
                                .withActivityId(pathRequest.getActivityId())
                                .withUserId(claims.get("email"))
                                .withUpdatedActivityName(bodyRequest.getUpdatedActivityName())
                                .withUpdatedDescription(bodyRequest.getUpdatedDescription())
                                .withUpdatedPosterExperience(bodyRequest.getUpdatedPosterExperience())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideEditActivity().handleRequest(request)
        );
    }
}
