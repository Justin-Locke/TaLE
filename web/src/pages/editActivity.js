import TaLEClient from "../api/TaLEClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class EditActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity', 'addActivityToPage'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.addActivityToPage);
    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');

        const activity = await this.client.viewActivity(activityId);
        this.dataStore.set('activity', activity);
        this.dataStore.set('activityId', activity.activityId);
    }

    mount() {
        document.getElementById('submitUpdatedActivity').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async submit(evt) {
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        
        const activityId = this.dataStore.get('activityId');

        const createButton = document.getElementById('submitUpdatedActivity');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Updating..';

        const activityName = document.getElementById('activityName').value;
        const description = document.getElementById('activityDescription').value;
        const posterExperience = document.getElementById('posterExperience').value;

        const updatedActivity = await this.client.editActivity(activityId, activityName, description, posterExperience, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('updatedActivity', updatedActivity);
        this.redirectToViewActivity();
    }

    redirectToViewActivity() {
        const activityId = this.dataStore.get('activityId');
        if (activityId != null) {
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
        }
    }

    addActivityToPage() {
        const activity = this.dataStore.get('activity');
        if (activity != null) {
            document.getElementById('activityName').value = activity.activityName;
            document.getElementById('activityDescription').value = activity.description;
            document.getElementById('posterExperience').value = activity.posterExperience;
        }
    }
}

const main = async () => {
    const editActivity = new EditActivity();
    editActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);