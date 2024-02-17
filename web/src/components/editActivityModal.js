class EditActivityModal {
    constructor(activity) {
        this.editActivityModal = document.getElementById('editActivityModal');
        this.editActivityButton = document.getElementById('editActivityButton');
        this.span = document.getElementsByClassName("close")[2];

        this.activity = activity;

        this.setupEventlisteners();
        this.populateModalFields();
    }

    show() {
        this.editActivityModal.style.display = "block";
    }

    hide() {
        this.editActivityModal.style.display = "none";
        // Additional logic to reset form field and hide error messages
        const errorMessageDisplay = document.getElementById('edit-error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');
    }

    setupEventlisteners() {
        this.editActivityButton.onclick = () => this.show();
        this.span.onclick = () => this.hide();
        window.onclick = (event) => {
            if (event.target == this.editActivityModal) {
                this.hide();
            }
        };
    }

    populateModalFields() {
        if (this.activity) {
            document.getElementById('editActivityName').value = this.activity.activityName;
            document.getElementById('editActivityDescription').value = this.activity.description;
            document.getElementById('editPosterExperience').value = this.activity.posterExperience;
        }
    }
}

// Export the EditActivityModal class
export default EditActivityModal;