package com.nashss.se.tale.activity.results;

public class DeleteActivityResult {
    private final String deleteResult;

    /**
     * Constructor for DeleteActivityResult.
     * @param deleteResult string with result message.
     */
    public DeleteActivityResult(String deleteResult) {
        this.deleteResult = deleteResult;
    }

    public String getDeleteResult() {
        return deleteResult;
    }

    @Override
    public String toString() {
        return "DeleteActivityResult{" +
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

        public DeleteActivityResult build() {
            return new DeleteActivityResult(deleteResult);
        }
    }
}
