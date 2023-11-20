package com.nashss.se.TaLE.activity.results;

import com.nashss.se.TaLE.models.CommentModel;

import java.util.List;

public class GetCommentsForActivityResult {
    private final List<CommentModel> commentModelList;
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
