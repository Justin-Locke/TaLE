class ActivityModal {
    constructor() {
        this.newActivityButton = document.getElementById('createNewActivityButton');
        this.activityModal = document.getElementById('activityModal');
        this.span = document.getElementsByClassName("close")[0];

        this.setupEventlisteners();
    }

    show() {
        this.activityModal.style.display = "block";
    }

    hide() {
        this.activityModal.style.display = "none";
        // Additional logic to reset form field and hide error messages
        document.getElementById('activityName').value = '';
            document.getElementById('description').value = '';
            document.getElementById('posterExperience').value = '';
            const errorMessageDisplay = document.getElementById('error-message');
            errorMessageDisplay.innerText = '';
            errorMessageDisplay.classList.add('hidden');
    }

    setupEventlisteners() {
        this.newActivityButton.onclick = () => this.show();
        this.span.onclick = () => this.hide();
        window.onclick = (event) => {
            if (event.target == this.activityModal) {
                this.hide();
            }
        };

        document.getElementById('posterExperience').addEventListener("keypress", (event) => {
            if (event.key === "Enter") {
                document.getElementById("postNewActivityButton").click();
            }
        });
    }
}

// Export the ActivityModal class
export default ActivityModal;