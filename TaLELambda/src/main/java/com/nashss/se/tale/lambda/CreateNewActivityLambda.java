package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.CreateNewActivityRequest;
import com.nashss.se.tale.activity.results.CreateNewActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateNewActivityLambda
    extends LambdaActivityRunner<CreateNewActivityRequest, CreateNewActivityResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateNewActivityRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateNewActivityRequest> input, Context context) {
        return super.runActivity(
            () -> {
                CreateNewActivityRequest unauthenticatedRequest = input.fromPath(path ->
                            CreateNewActivityRequest.builder()
                                    .withCityId(path.get("cityId"))
                                    .build());
                CreateNewActivityRequest createNewActivityRequest = input.fromBody(CreateNewActivityRequest.class);
                return input.fromUserClaims(claims ->
                            CreateNewActivityRequest.builder()
                                    .withCityId(unauthenticatedRequest.getCityId())
                                    .withUserId(claims.get("email"))
                                    .withActivityName(createNewActivityRequest.getActivityName())
                                    .withDescription(createNewActivityRequest.getDescription())
                                    .withPosterExperience(createNewActivityRequest.getPosterExperience())
                                    .build());
            },
            (request, serviceComponent) ->
                        serviceComponent.provideCreateNewActivity().handleRequest(request)
        );
    }
}
