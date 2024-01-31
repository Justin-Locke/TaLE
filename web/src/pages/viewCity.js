import TaLEClient from '../api/TaLEClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';
import NavBar from '../components/navBar';
import ActivityCard from '../components/activityCard';
import ActivityModal from '../components/activityModal';

class ViewCity extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCityToPage', 'submitNewActivity', 'redirectToViewActivity', 'addActivitiesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCityToPage);
        this.dataStore.addChangeListener(this.addActivitiesToPage);
        this.authenticator = new Authenticator();
        this.loadingSpinner = new LoadingSpinner();

        this.footer = new Footer();
        this.header = new Header(this.dataStore);
        this.navbar = new NavBar();
        this.activityCard = new ActivityCard();
        this.activityModal = new ActivityModal();
    }

    async clientLoaded() {
        
        const urlParams = new URLSearchParams(window.location.search);
        const cityId = urlParams.get('cityId');
        this.dataStore.set('cityId', cityId);

        const city = await this.client.viewCity(cityId);
        this.dataStore.set('city', city);
        
        const allActivities = await this.client.viewAllActivitiesForCity(cityId);
        this.dataStore.set('allActivities', allActivities);

        const newActivityButton = document.getElementById('createNewActivityButton');
        newActivityButton.onclick = () => this.activityModal.show();
    
    }
    

    mount() {
        this.header.addHeaderToPage();
        this.navbar.addNavBarToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        
        this.clientLoaded();
        document.getElementById('postNewActivityButton').addEventListener('click', this.submitNewActivity);
        
    }

    async submitNewActivity(evt) {
        this.loadingSpinner.showLoadingSpinner();
        const cityId = this.dataStore.get('cityId');
        evt.preventDefault();

        const errorMessageDisplay = document.getElementById('error-message');
        errorMessageDisplay.innerText = '';
        errorMessageDisplay.classList.add('hidden');

        const createButton = document.getElementById('postNewActivityButton');
        const origButtonText = createButton.innerText;
        createButton.innerText = 'Creating..';

        const activityName = document.getElementById('activityName').value.trim();
        const description = document.getElementById('description').value.trim();
        const posterExperience = document.getElementById('posterExperience').value.trim();

        const activity = await this.client.createNewActivity(cityId, activityName, description, posterExperience, (error) => {
            createButton.innerText = origButtonText;
            errorMessageDisplay.innerText = `Error: ${error.message}`;
            errorMessageDisplay.classList.remove('hidden');
            this.loadingSpinner.hideLoadingSpinner();

        });
        if (activity != null) {
            this.dataStore.set('activity', activity);
            this.redirectToViewActivity(activity);

            // Close modal after a successful activity creation
            this.activityModal.hide();
        }

    }

    addCityToPage() {
        this.loadingSpinner.showLoadingSpinner("Getting Information...");
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
            return;
        }

        activityList.forEach(activity => {
            const activityCard = this.activityCard.CreateActivityCard(activity);
            activitiesContainer.appendChild(activityCard);
        })

        this.loadingSpinner.hideLoadingSpinner();
    }

    redirectToViewActivity(activity) {
        if (activity != null) {
            window.location.href = `/viewActivity.html?activityId=${activity.activityId}`
        }
    }

    
}

    const main = async () => {
        const viewCity = new ViewCity();
        viewCity.mount();
    };

    window.addEventListener('DOMContentLoaded', main);
