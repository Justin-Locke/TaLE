package com.nashss.se.TaLE.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.TaLE.activity.requests.GetAllCitiesRequest;
import com.nashss.se.TaLE.activity.results.GetAllCitiesResult;

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
