import TaLEClient from '../api/TaLEClient'
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Authenticator from '../api/authenticator';
import LoadingSpinner from '../components/loadingSpinner';
import Footer from '../components/footer';
import NavBar from '../components/navBar';

class ViewCities extends BindingClass {
    constructor() {
        super();
        this.bindClassMethods(['clientLoaded', 'mount', 'addCitiesToPage'], this);
        this.dataStore = new DataStore();
        this.dataStore.addChangeListener(this.addCitiesToPage);
        this.header = new Header(this.dataStore);
        this.navbar = new NavBar();
        this.footer = new Footer();
        this.authenticator = new Authenticator();
        this.loadingSpinner = new LoadingSpinner;
    }


    async clientLoaded() {
        this.loadingSpinner.showLoadingSpinner("Finding Fun Places...");

        const cityList = await this.client.viewCities();
        this.dataStore.set('cityList', cityList);
        
    }


    mount() {
        this.header.addHeaderToPage();
        this.navbar.addNavBarToPage();
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

}

const main = async () => {
    const viewCities = new ViewCities();
    viewCities.mount();
};

window.addEventListener('DOMContentLoaded', main);