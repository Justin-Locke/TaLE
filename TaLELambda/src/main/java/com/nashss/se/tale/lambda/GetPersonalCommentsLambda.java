package com.nashss.se.tale.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.tale.activity.requests.GetPersonalCommentsRequest;
import com.nashss.se.tale.activity.results.GetPersonalCommentsResult;

public class GetPersonalCommentsLambda
    extends LambdaActivityRunner<GetPersonalCommentsRequest, GetPersonalCommentsResult>
    implements RequestHandler<AuthenticatedLambdaRequest<GetPersonalCommentsRequest>, LambdaResponse> {
        @Override
        public LambdaResponse handleRequest (AuthenticatedLambdaRequest < GetPersonalCommentsRequest > input, Context
        context) {
        return super.runActivity(
                () -> input.fromUserClaims(claims ->
                        GetPersonalCommentsRequest.builder()
                                .withUserId(claims.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetPersonalComments().handleRequest(request)
        );
    }
}
