package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.GetAllCitiesRequest;
import com.nashss.se.tale.activity.results.GetAllCitiesResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetAllCitiesLambda extends LambdaActivityRunner<GetAllCitiesRequest, GetAllCitiesResult>
    implements RequestHandler<LambdaRequest<GetAllCitiesRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetAllCitiesRequest> input, Context context) {
        return super.runActivity(
            () -> GetAllCitiesRequest.builder().build(),
            (request, serviceComponent) ->
                        serviceComponent.provideGetAllCities().handleRequest(request)
        );
    }
}
