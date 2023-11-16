package com.nashss.se.TaLE.lambda;
import com.nashss.se.TaLE.activity.requests.GetCityRequest;
import com.nashss.se.TaLE.activity.results.GetCityResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetCityLambda
    extends LambdaActivityRunner<GetCityRequest, GetCityResult>
        implements RequestHandler<LambdaRequest<GetCityRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetCityRequest> input, Context context) {
        return super.runActivity(
            () -> input.fromPath(path ->
                        GetCityRequest.builder()
                                .withCityId(path.get("cityId"))
                                .build()),
            (request, serviceComponent) ->
                        serviceComponent.provideGetCity().handleRequest(request)
        );
    }
}
