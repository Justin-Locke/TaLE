package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.GetCommentsForActivityRequest;
import com.nashss.se.tale.activity.results.GetCommentsForActivityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetCommentsForActivityLambda
    extends LambdaActivityRunner<GetCommentsForActivityRequest, GetCommentsForActivityResult>
    implements RequestHandler<LambdaRequest<GetCommentsForActivityRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetCommentsForActivityRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
            GetCommentsForActivityRequest.builder()
                    .withActivityId(path.get("activityId"))
                    .build()),
            (request, serviceComponent) ->
                    serviceComponent.provideGetCommentsForActivity().handleRequest(request)
            );
    }
}
