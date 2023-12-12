import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';

class ViewCities extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCitiesToPage', 'loginOrOut'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCitiesToPage);
        this.header = new Header(this.dataStore);
        this.footer = new Footer();
        this.authenticator = new Authenticator();
        this.loadingSpinner = new LoadingSpinner;
    }


    async clientLoaded() {
        this.loadingSpinner.showLoadingSpinner("Finding Fun Places...");
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
        const cityList = await this.client.viewCities();
        this.dataStore.set('cityList', cityList);
        
    }


    mount() {
        this.header.addHeaderToPage();
        this.footer.addFooterToPage();
        this.client = new TaLEClient();
        this.clientLoaded();

    }

    async addCitiesToPage() {
        const cityList = this.dataStore.get('cityList');
        if (cityList == null) {
            return;
        }

        const citiesContainer = document.getElementById('citiesContainer');
        cityList.forEach(city => {
            const cityDiv = document.createElement('div');
            cityDiv.classList.add('cities');
            const cityName = document.createElement('h3');
            cityName.textContent = city.cityName;
            cityName.addEventListener('click', () => this.redirectToViewCity(city));
            cityDiv.appendChild(cityName);

            citiesContainer.appendChild(cityDiv);
        })
        this.loadingSpinner.hideLoadingSpinner();
    }

    redirectToViewCity(city) {
        if (city != null) {
            window.location.href = `/viewCity.html?cityId=${city.cityId}`;
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
    const viewCities = new ViewCities();
    viewCities.mount();
};

window.addEventListener('DOMContentLoaded', main);