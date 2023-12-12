import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';

class CreateComment extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
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
            document.getElementById('personalPage').style.display = 'none';
            document.getElementById('loginButton').innerText = `Login`;
            document.getElementById('loginButton').addEventListener('click', this.createLoginButton());
        }
        const urlParams =  new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');
        this.dataStore.set('activityId', activityId);


    }

    mount() {
        document.getElementById('create').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async submit(evt) {
        const acitivityId = this.dataStore.get('activityId');
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('create');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const title = document.getElementById('title').value;
        const message = document.getElementById('message').value;

        const comment = await this.client.createComment(acitivityId, title, message, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('comment', comment);
        this.redirectToViewActivity();
        
    }

    redirectToViewActivity() {
        const activityId = this.dataStore.get('activityId');
        if (activityId != null) {
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
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
    const createComment = new CreateComment();
    createComment.mount();
};

window.addEventListener('DOMContentLoaded', main);
