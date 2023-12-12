import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';

class EditComment extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'submit', 'redirectToViewActivity', 'addCommentToPage', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.footer = new Footer();
        this.dataStore.addChangeListener(this.addCommentToPage);
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
        const urlParams =  new URLSearchParams(window.location.search);
        const activityId = urlParams.get('activityId');
        this.dataStore.set('activityId', activityId);
        const commentId = urlParams.get('commentId');
        this.dataStore.set('commentId', commentId);
        const comment = await this.client.viewComment(activityId, commentId);
        this.dataStore.set('comment', comment);

       
    }

    mount() {
        document.getElementById('submitUpdatedComment').addEventListener('click', this.submit);
        this.header.addHeaderToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    async submit(evt) {
        this.loadingSpinner.showLoadingSpinner();
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = ``;
        errorMessageDisplay.classList.add('hidden');
        
        const activityId = this.dataStore.get('activityId');
        const commentId = this.dataStore.get('commentId');
        
        const createButton = document.getElementById('submitUpdatedComment');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const title = document.getElementById('commentTitle').value;
        const message = document.getElementById('commentMessage').value;

        const updatedComment = await this.client.editComment(activityId, commentId, title, message, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
        });
        this.dataStore.set('updatedComment', updatedComment);
        this.redirectToViewActivity();
        
    }


    redirectToViewActivity() {
        const activityId = this.dataStore.get('activityId');
        if (activityId != null) {
            window.location.href = `/viewActivity.html?activityId=${activityId}`;
        }        
    }

    addCommentToPage() {
        const comment = this.dataStore.get('comment');
        if (comment != null) {
            document.getElementById('commentTitle').value = comment.title;
            document.getElementById('commentMessage').value = comment.message;
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
    const editComment = new EditComment();
    editComment.mount();
};

window.addEventListener('DOMContentLoaded', main);
