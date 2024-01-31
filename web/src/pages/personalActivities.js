import Authenticator from '../api/authenticator';
import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';
import NavBar from '../components/navBar';
import ActivityCard from '../components/activityCard';

class PersonalActivities extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addActivitiesToPage', 'redirectToViewActivity', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.header = new Header(this.dataStore);
        this.navbar = new NavBar();
        this.footer = new Footer();
        this.activityCard = new ActivityCard();
        this.loadingSpinner = new LoadingSpinner();
        this.authenticator = new Authenticator();
        this.dataStore.addChangeListener(this.addActivitiesToPage);
        this.dataStore.addChangeListener(this.loadingSpinner.hideLoadingSpinner)
    }

    async clientLoaded() {
        this.loadingSpinner.showLoadingSpinner("Loading Your Personal Activities...");

        const activities = await this.client.viewPersonalActivities();
        this.dataStore.set('activities', activities);        
    }

    mount() {
        
        this.header.addHeaderToPage();
        this.navbar.addNavBarToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        this.clientLoaded();
    }

    addActivitiesToPage() {
        this.loadingSpinner.showLoadingSpinner();
        console.log("Loading Spinner");

        const activities = this.dataStore.get('activities');
        if (activities == null) {
            return;
        }

        const activitiesContainer = document.getElementById('activitiesContainer');

        activities.forEach(activity => {
            const activityCard = this.activityCard.CreateActivityCard(activity);
            activitiesContainer.appendChild(activityCard);
        })

        this.loadingSpinner.hideLoadingSpinner();
        console.log("End loading spinner");

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
    const personalActivities = new PersonalActivities();
    personalActivities.mount();
}

window.addEventListener('DOMContentLoaded', main);