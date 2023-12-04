import TaLEClient from "../api/TaLEClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from "../api/authenticator";

class EditActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity', 'addActivityToPage', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.dataStore.addChangeListener(this.addActivityToPage);
        this.authenticator = new Authenticator();
    }

    async clientLoaded() {
        const userLoggedIn = await this.authenticator.isUserLoggedIn();
        if (userLoggedIn) {
            const user = await this.client.getIdentity();
            const personalBttn = document.getElementById('personalPage');
            personalBttn.classList.remove('subnavbtn.hidden');
            personalBttn.classList.add('subnavbtn');
            personalBttn.removeAttribute('hidden');
            document.getElementById('loginButton').innerText = `Logout: ${user.name}`;
            document.getElementById('loginButton').addEventListener('click', this.createLogoutButton(user));
        }
        if (!userLoggedIn) {
            document.getElementById('loginButton').innerText = `Login`;
            document.getElementById('loginButton').addEventListener('click', this.createLoginButton());
        }
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

    async loginOrOut() {
        const userLoggedIn = await this.authenticator.isUserLoggedIn();
        if (userLoggedIn) {
            return this.client.logout;
        } else {
            return this.client.login;
        }

    }

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.getElementById('loginButton');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;

    }
}

const main = async () => {
    const editActivity = new EditActivity();
    editActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);