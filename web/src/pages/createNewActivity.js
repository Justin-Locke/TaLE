import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class CreateNewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewActivity);
        this.header = new Header(this.dataStore);

    }

    async clientLoaded() {
        const urlParams = new URLSearchParams(window.location.search);
        const cityId = urlParams.get('cityId');
        this.dataStore.set('cityId', cityId);
    }

    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async submit(evt) {
        const cityId = this.dataStore.get('cityId');
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const activityName = document.getElementById('activityName').value;
        const description = document.getElementById('description').value;
        const posterExperience = document.getElementById('posterExperience').value;

        const activity = await this.client.createNewActivity(cityId, activityName, description, posterExperience, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        console.log(activity + "is the Activity");
        this.dataStore.set('activity', activity);
        
    }

    redirectToViewActivity() {
        console.log("redirecting now");
        const activity = this.dataStore.get('activity');
        if (activity != null) {
            console.log("activiy is not null");
            window.location.href = `/viewActivity.html?activityId=${activity.activityId}`;
        }
        console.log("redirect finished");
        

    }
}

const main = async () => {
    const createNewActivity = new CreateNewActivity();
    createNewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);