package com.nashss.se.TaLE.activity.results;

public class DeleteCommentResult {
    private final String deleteResult;
    public DeleteCommentResult(String deleteResult) {
        this.deleteResult = deleteResult;
    }

    public String getDeleteResult() {
        return deleteResult;
    }

    @Override
    public String toString() {
        return "DeleteCommentResult{" +
                "deleteResult='" + deleteResult + '\'' +
                '}';
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }
    public static class Builder {
        private String deleteResult;
        public Builder withDeleteResult(String deleteResult) {
            this.deleteResult = deleteResult;
            return this;
        }

        public DeleteCommentResult build() {
            return new DeleteCommentResult(deleteResult);
        }
    }
}
