import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class PersonalPage extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivitiesToPage', 'addCommentsToPage', 'loginOrOut'], this);
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
            document.getElementById('loginButton').innerText = `Login`;
            document.getElementById('loginButton').addEventListener('click', this.createLoginButton());
        }
        const activities = await this.client.viewPersonalActivities();
        this.dataStore.set('activities', activities);
        const comments = await this.client.viewPersonalComments();
        this.dataStore.set('comments', comments);

        
    }

    mount() {
        document.getElementById('myActivityPosts').addEventListener('click', this.addActivitiesToPage);
        document.getElementById('myCommentPosts').addEventListener('click', this.addCommentsToPage);
        
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    addActivitiesToPage() {
        const activities = this.dataStore.get('activities');
        if (activities == null) {
            return;
        }

        const activitiesContainer = document.getElementById('activitiesContainer');

        activities.forEach(activity => {
            const activityDiv = document.createElement('div');
            activityDiv.classList.add('activity');

            const activityName = document.createElement('h3');
            activityName.textContent = activity.activityName;
            activityDiv.appendChild(activityName);
            
            activitiesContainer.appendChild(activityDiv);
            
            
        })
        document.getElementById('myActivityPosts').hidden=true;
        document.getElementById('myCommentPosts').hidden=false;
    }

    addCommentsToPage() {
        const comments = this.dataStore.get('comments');
        if (comments == null) {
            return;
        }

        const commentsContainer = document.getElementById('commentsContainer');

        comments.forEach(comment => {
            const commentDiv = document.createElement('div');
            commentDiv.classList.add('comment');

            const commentTitle = document.createElement('h3');
            commentTitle.textContent = comment.title;
            commentDiv.appendChild(commentTitle);

            commentsContainer.appendChild(commentDiv);
        })
        document.getElementById('myCommentPosts').hidden=true;
        document.getElementById('myActivityPosts').hidden=false;

    }

    redirectToViewActivity(activityId) {
        if (activityId != null) {
            window.location.href = '/viewActivity.html?activityId=${activityId}';
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
    const personalPage = new PersonalPage();
    personalPage.mount();
}

window.addEventListener('DOMContentLoaded', main);