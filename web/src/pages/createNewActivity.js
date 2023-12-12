import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';

class CreateNewActivity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.redirectToViewActivity);
        this.header = new Header(this.dataStore);
        this.footer = new Footer();
        this.authenticator = new Authenticator();
        this.loadingSpinner = new LoadingSpinner();
        this.dataStore.addChangeListener(this.loadingSpinner.hideLoadingSpinner);

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
        const cityId = urlParams.get('cityId');
        this.dataStore.set('cityId', cityId);

        
    }

    mount() {
        this.header.addHeaderToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        this.clientLoaded(); 
        document.getElementById('create').addEventListener('click', this.submit);

    }

    async submit(evt) {
        this.loadingSpinner.showLoadingSpinner(message = "Creating your activity...");
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
        this.dataStore.set('activity', activity);
        
    }

    redirectToViewActivity() {
        const activity = this.dataStore.get('activity');
        if (activity != null) {
            window.location.href = `/viewActivity.html?activityId=${activity.activityId}`;
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
    const createNewActivity = new CreateNewActivity();
    createNewActivity.mount();
};

window.addEventListener('DOMContentLoaded', main);