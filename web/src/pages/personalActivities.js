import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";

class PersonalActivities extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivitiesToPage', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.authenticator = new Authenticator();
        this.dataStore.addChangeListener(this.addActivitiesToPage);
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

        
    }

    mount() {
        
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
    const personalActivities = new PersonalActivities();
    personalActivities.mount();
}

window.addEventListener('DOMContentLoaded', main);