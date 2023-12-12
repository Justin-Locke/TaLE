import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class PersonalComments extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCommentsToPage', 'redirectToViewActivity', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.authenticator = new Authenticator();
        this.dataStore.addChangeListener(this.addCommentsToPage);
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
            this.addCommentsToPage();
        }
        if (!userLoggedIn) {
            const loginButton = document.getElementById('loginButton');
            loginButton.innerText = `Login`;
            loginButton.addEventListener('click', this.createLoginButton());
        }
        const comments = await this.client.viewPersonalComments();
        this.dataStore.set('comments', comments);        
    }

    mount() {
        
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }


    addCommentsToPage() {
        const comments = this.dataStore.get('comments');
        if (comments == null) {
            return;
        }
        const outlineComments = document.createElement('div');

        const commentsContainer = document.getElementById('commentsContainer');

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('personal-comments');

            const commentTitle = document.createElement('h1');
            commentTitle.textContent = comment.title;
            commentTitle.addEventListener('click', () => this.redirectToViewActivity(comment.activityId));
            commentDiv.appendChild(commentTitle);

            const commentMessage = document.createElement('p');
            commentMessage.textContent = comment.message;
            commentDiv.appendChild(commentMessage);



            commentsContainer.appendChild(commentDiv);
        })

    }

    redirectToViewActivity(activityId) {
        if (activityId != null) {
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
        }
    }
    
    async loginOrOut() {
        const user = await this.client.getIdentity();
        if (user != null) {
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
    const personalComments = new PersonalComments();
    personalComments.mount();
}

window.addEventListener('DOMContentLoaded', main);