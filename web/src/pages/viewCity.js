import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';

class ViewCity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCityToPage', 'redirectToCreateNewActivity', 'addActivitiesToPage', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCityToPage);
        this.dataStore.addChangeListener(this.addActivitiesToPage);
        this.authenticator = new Authenticator();;

        this.header = new Header(this.dataStore);
        console.log("ViewCity constructor");
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
            document.getElementById('createNewActivityButton').removeAttribute("hidden");
        }
        if (!userLoggedIn) {
            document.getElementById('loginButton').innerText = `Login`;
            document.getElementById('loginButton').addEventListener('click', this.createLoginButton());
        }
        const urlParams = new URLSearchParams(window.location.search);
        const cityId = urlParams.get('cityId');
        this.dataStore.set('cityId', cityId);

        const city = await this.client.viewCity(cityId);
        this.dataStore.set('city', city);
        
        const activityIdList = city.activityList;
        const allActivities = await this.client.viewAllActivitiesForCity(cityId);
        // const allActivities = [];
        // for (const activityId of activityIdList) {
        //     const activity = await this.client.viewActivity(activityId);
        //     allActivities.push(activity);
        // }
        this.dataStore.set('allActivities', allActivities);
    }
    

    mount() {
        this.header.addHeaderToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
        document.getElementById('createNewActivityButton').addEventListener('click', this.redirectToCreateNewActivity);
    }

    async submitNewActivity(evt) {
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

    addCityToPage() {
        const city = this.dataStore.get('city');
        if (city == null) {
            return;
        }

        document.getElementById('cityName').innerText = city.cityName;
        
    }

    addActivitiesToPage() {
        const activityList = this.dataStore.get('allActivities');

        const activitiesContainer = document.getElementById('activitiesContainer');
        if (activityList == null) {
            console.log("ActivityList is null");
            return;
        }

        activityList.forEach(activity => {
            const activityDiv = document.createElement('div');
            activityDiv.classList.add('activity');

            const activityName = document.createElement('h3');
            activityName.textContent = activity.activityName;
            activityName.addEventListener('click', () => {this.redirectToViewActivity(activity)});
            activityDiv.appendChild(activityName);
        
            const line = document.createElement('hr');
            activityDiv.appendChild(line);
            activitiesContainer.appendChild(activityDiv);
        })
        
    }

    redirectToViewActivity(activity) {
            if (activity != null) {
            window.location.href = `/viewActivity.html?activityId=${activity.activityId}`
        }
    }



     redirectToCreateNewActivity() {
        const city = this.dataStore.get('city');
        if (city != null) {
            window.location.href = `/createNewActivity.html?cityId=${city.cityId}`;
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
            if ('caches' in window) {
                caches.open("my-cache").then(async (cache) => {
                    cache
                    .add(window.location.href)
                    .then(() => console.log("Data added to cache."))
                    .catch((error) => console.error("Error adding data to cache:", error));
                })
                caches.open("my-cache").then(async (cache) => {
                    const cachedData = await cache.match(window.location.href);
                    console.log(cachedData.url + "IS THE CACHED DATA");
                })
            } else {
                console.log("Caches not supported");
            }
            
            await clickHandler();
        });

        return button;

    }
}

    const main = async () => {
        const viewCity = new ViewCity();
        viewCity.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
