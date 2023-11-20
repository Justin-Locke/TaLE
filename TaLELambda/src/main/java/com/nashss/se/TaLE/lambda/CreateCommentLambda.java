package com.nashss.se.TaLE.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.nashss.se.TaLE.activity.requests.CreateCommentRequest;
import com.nashss.se.TaLE.activity.results.CreateCommentResult;

public class CreateCommentLambda
    extends LambdaActivityRunner<CreateCommentRequest, CreateCommentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<CreateCommentRequest>, LambdaResponse> {
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<CreateCommentRequest> input, Context context) {
        return super.runActivity(
                () -> {
                    CreateCommentRequest unauthenticatedRequest = input.fromPath(path ->
                            CreateCommentRequest.builder()
                                    .withActivityId(path.get("activityId"))
                                    .build());
                    CreateCommentRequest createCommentRequest = input.fromBody(CreateCommentRequest.class);
                    return input.fromUserClaims(claims ->
                            CreateCommentRequest.builder()
                                    .withActivityId(unauthenticatedRequest.getActivityId())
                                    .withUserId(claims.get("email"))
                                    .withTitle(createCommentRequest.getTitle())
                                    .withMessage(createCommentRequest.getMessage())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateComment().handleRequest(request)
        );
    }
}
