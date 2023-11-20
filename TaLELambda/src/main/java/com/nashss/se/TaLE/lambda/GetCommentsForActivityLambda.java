package com.nashss.se.TaLE.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.TaLE.activity.requests.GetCommentsForActivityRequest;
import com.nashss.se.TaLE.activity.results.GetCommentsForActivityResult;

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
