package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.CommentModel;

import java.util.List;

public class GetPersonalCommentsResult {
    private final List<CommentModel> commentModelList;

    /**
     * Constructor for GetPersonalCommentsResult.
     * @param commentModelList list of CommentModels to create Result.
     */
    public GetPersonalCommentsResult(List<CommentModel> commentModelList) {
        this.commentModelList = commentModelList;
    }

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    @Override
    public String toString() {
        return "GetPersonalCommentsResult{" +
                "commentModelList=" + commentModelList +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private List<CommentModel> commentModelList;

        public Builder withCommentModelList(List<CommentModel> commentModelList) {
            this.commentModelList = commentModelList;
            return this;
        }

        public GetPersonalCommentsResult build() {
            return new GetPersonalCommentsResult(commentModelList);
        }
    }
}
