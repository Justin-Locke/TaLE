package com.nashss.se.TaLE.activity.results;

import com.nashss.se.TaLE.models.CommentModel;

public class CreateCommentResult {
    private final CommentModel commentModel;
    private CreateCommentResult(CommentModel commentModel) {
        this.commentModel = commentModel;
    }

    public CommentModel getCommentModel() {
        return commentModel;
    }

    @Override
    public String toString() {
        return "CreateCommentResult{" +
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

        public CreateCommentResult build() {
            return new CreateCommentResult(commentModel);
        }
    }
}
