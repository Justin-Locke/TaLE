package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.GetCommentRequest;
import com.nashss.se.tale.activity.results.GetCommentResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class GetCommentLambda
    extends LambdaActivityRunner<GetCommentRequest, GetCommentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<GetCommentRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<GetCommentRequest> input, Context context) {
        return super.runActivity(
            () -> {
                GetCommentRequest pathRequest = input.fromPath(path ->
                        GetCommentRequest.builder()
                                .withActivityId(path.get("activityId"))
                                .withCommentId(path.get("commentId"))
                                .build());
                return input.fromUserClaims(claims ->
                        GetCommentRequest.builder()
                                .withActivityId(pathRequest.getActivityId())
                                .withCommentId(pathRequest.getCommentId())
                                .withUserId(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideGetComment().handleRequest(request)
        );
    }
}
