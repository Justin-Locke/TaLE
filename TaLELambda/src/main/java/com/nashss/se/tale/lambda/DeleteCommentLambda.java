package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.DeleteCommentRequest;
import com.nashss.se.tale.activity.results.DeleteCommentResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class DeleteCommentLambda
    extends LambdaActivityRunner<DeleteCommentRequest, DeleteCommentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<DeleteCommentRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<DeleteCommentRequest> input, Context context) {
        return super.runActivity(
            () -> {
                DeleteCommentRequest unauthenticatedRequest = input.fromPath(path ->
                        DeleteCommentRequest.builder()
                                .withCommentId(path.get("commentId"))
                                .withActivityId(path.get("activityId"))
                                .build());
                return input.fromUserClaims(claims ->
                        DeleteCommentRequest.builder()
                                .withCommentId(unauthenticatedRequest.getCommentId())
                                .withActivityId(unauthenticatedRequest.getActivityId())
                                .withUserId(claims.get("email"))
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideDeleteComment().handleRequest(request)
        );
    }
}
