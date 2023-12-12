package com.nashss.se.tale.models;

import java.time.LocalDate;
import java.util.Objects;

public class CommentModel {
    private String activityId;
    private String commentId;
    private String title;
    private String message;
    private String userId;
    private LocalDate datePosted;
    private Boolean edited;

    /**
     * Constructor for Comment Model.
     * @param activityId from comment.
     * @param commentId from comment.
     * @param title from comment.
     * @param message from comment.
     * @param userId from comment.
     * @param datePosted from comment.
     * @param edited from comment.
     */
    private CommentModel(String activityId, String commentId,
                        String title, String message,
                        String userId, LocalDate datePosted,
                        Boolean edited) {
        this.activityId = activityId;
        this.commentId = commentId;
        this.title = title;
        this.message = message;
        this.userId = userId;
        this.datePosted = datePosted;
        this.edited = edited;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUserId() {
        return userId;
    }

    public LocalDate getDatePosted() {
        return datePosted;
    }

    public Boolean getEdited() {
        return edited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentModel that = (CommentModel) o;
        return Objects.equals(activityId, that.activityId) &&
                Objects.equals(commentId, that.commentId) &&
                Objects.equals(title, that.title) &&
                Objects.equals(message, that.message) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(datePosted, that.datePosted) &&
                Objects.equals(edited, that.edited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(activityId, commentId,
                title, message, userId, datePosted, edited);
    }

    // CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String activityId;
        private String commentId;
        private String title;
        private String message;
        private String userId;
        private LocalDate datePosted;
        private Boolean edited;

        public Builder withActivityId(String activityId) {
            this.activityId = activityId;
            return this;
        }

        public Builder withCommentId(String commentId) {
            this.commentId = commentId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withDatePosted(LocalDate datePosted) {
            this.datePosted = datePosted;
            return this;
        }

        public Builder withEdited(Boolean edited) {
            this.edited = edited;
            return this;
        }

        public CommentModel build() {
            return new CommentModel(activityId, commentId, title,
                    message, userId, datePosted, edited);
        }
    }


}
