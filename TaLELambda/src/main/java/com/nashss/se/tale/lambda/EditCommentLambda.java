package com.nashss.se.tale.lambda;
import com.nashss.se.tale.activity.requests.EditCommentRequest;
import com.nashss.se.tale.activity.results.EditCommentResult;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class EditCommentLambda
    extends LambdaActivityRunner<EditCommentRequest, EditCommentResult>
    implements RequestHandler<AuthenticatedLambdaRequest<EditCommentRequest>, LambdaResponse> {

    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditCommentRequest> input,
                                        Context context) {
        return super.runActivity(
            () -> {
                EditCommentRequest pathRequest = input.fromPath(path ->
                        EditCommentRequest.builder()
                                .withActivityId(path.get("activityId"))
                                .withCommentId(path.get("commentId"))
                                .build());
                EditCommentRequest bodyRequest = input.fromBody(EditCommentRequest.class);
                return input.fromUserClaims(claims ->
                        EditCommentRequest.builder()
                                .withActivityId(pathRequest.getActivityId())
                                .withCommentId(pathRequest.getCommentId())
                                .withUserId(claims.get("email"))
                                .withTitle(bodyRequest.getTitle())
                                .withMessage(bodyRequest.getMessage())
                                .build());
            },
            (request, serviceComponent) ->
                    serviceComponent.provideEditComment().handleRequest(request)
        );
    }
}
