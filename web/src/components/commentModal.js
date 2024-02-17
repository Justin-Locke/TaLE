class CommentModal {
    constructor() {
        this.newCommentButton = document.getElementById('createCommentButton');
        this.commentModal = document.getElementById("commentModal");
        this.newCommentspan = document.getElementsByClassName("close")[0];

        this.setupEventListeners();
    }

    show() {
        this.commentModal.style.display = "block";
    }

    hide() {
        this.commentModal.style.display = "none";
        //Additional logic to reset form field and hide error messages
        document.getElementById('title').value = '';
        document.getElementById('message').value = '';
        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    }

    setupEventListeners() {
        this.newCommentButton.onclick = () => this.show();
        this.newCommentspan.onclick = () => this.hide();
        window.onclick = (event) => {
            if (event.target == this.commentModal) {
                this.hide();
            }
        };
    }
}

// Export the CommentModal class
export default CommentModal;