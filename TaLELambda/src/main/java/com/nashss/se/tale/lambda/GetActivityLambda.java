package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.GetActivityRequest;
import com.nashss.se.tale.activity.results.GetActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetActivityLambda
        extends LambdaActivityRunner<GetActivityRequest, GetActivityResult>
        implements RequestHandler<LambdaRequest<GetActivityRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetActivityRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                        GetActivityRequest.builder()
                                .withActivityId(path.get("activityId"))
                                .build()),
            (request, serviceComponent) ->
                        serviceComponent.provideGetActivity().handleRequest(request)
        );
    }
}
