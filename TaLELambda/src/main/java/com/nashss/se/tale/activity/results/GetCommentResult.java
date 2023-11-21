package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.CommentModel;

public class GetCommentResult {
    private final CommentModel commentModel;

    public GetCommentResult(CommentModel commentModel) {
        this.commentModel = commentModel;
    }

    public CommentModel getCommentModel() {
        return commentModel;
    }

    @Override
    public String toString() {
        return "GetCommentResult{" +
                "commentModel=" + commentModel +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private CommentModel commentModel;

        public Builder withCommentModel(CommentModel commentModel) {
            this.commentModel = commentModel;
            return this;
        }

        public GetCommentResult build() {
            return new GetCommentResult(commentModel);
        }
    }
}
