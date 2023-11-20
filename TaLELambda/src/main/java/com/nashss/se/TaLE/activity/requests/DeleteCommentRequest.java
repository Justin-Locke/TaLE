package com.nashss.se.TaLE.activity.requests;

public class DeleteCommentRequest {
    private final String commentId;
    private final String userId;

    public DeleteCommentRequest(String commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String commentId;
        private String userId;

        public Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public DeleteCommentRequest build() {
            return new DeleteCommentRequest(commentId, userId);
        }
    }
}
