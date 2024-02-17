class EditCommentModal {
    constructor(comment) {
        this.editCommentModal = document.getElementById('editCommentModal');
        this.editCommentButton = document.getElementById('editCommentButton');
        this.editCommentspan = document.getElementsByClassName("close")[1];

        this.comment = comment;

        this.setupEventlisteners();
        this.populateModalFields();
    }

    show() {
        this.editCommentModal.style.display = "block";
    }

    hide() {
        this.editCommentModal.style.display = "none";
        // Additional logic to reset form field and hide error messages
        const errorMessageDisplay = document.getElementById('edit-error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    }

    setupEventlisteners() {
        this.editCommentButton.onclick = () => this.show();
        this.editCommentspan.onclick = () => this.hide();
        window.onclick = (event) => {
            if (event.target == this.editCommentModal) {
                this.hide();
            }
        };
    }

    populateModalFields() {
        if (this.comment) {
            document.getElementById('commentTitle').value = comment.title;
            document.getElementById('commentMessage').value = comment.message;
            document.getElementById('commentId').value = comment.commentId;
        }
    }
}

// Export the EditCommentModal class
export default EditCommentModal;