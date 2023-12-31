package com.nashss.se.tale.activity.results;

import com.nashss.se.tale.models.CommentModel;

import java.util.List;

public class GetCommentsForActivityResult {
    private final List<CommentModel> commentModelList;

    /**
     * Constructor for GetCommentsForActivityResult.
     * @param commentModelList to create Result.
     */
    public GetCommentsForActivityResult(List<CommentModel> commentModelList) {
        this.commentModelList = commentModelList;
    }

    public List<CommentModel> getCommentModelList() {
        return commentModelList;
    }

    @Override
    public String toString() {
        return "GetCommentsForActivityResult{" +
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

        public GetCommentsForActivityResult build() {
            return new GetCommentsForActivityResult(commentModelList);
        }
    }
}
